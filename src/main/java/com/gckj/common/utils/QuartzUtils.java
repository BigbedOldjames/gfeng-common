package com.omysoft.common.utils;

import java.util.Collection;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * quartz 2.X版本工具类
 * @author yy
 * @date 2016-6-20
 */
public class QuartzUtils {
	
	protected static final Logger log = LoggerFactory.getLogger(QuartzUtils.class);
	
	public static String JOB_DATA_MAP = "quartzJobDataMap";
	
	public static Scheduler scheduler;
	
	static{
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	 /**
	 * 使用默认的Scheduler 创建定时任务
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static void createScheduleJob(String jobClassName, String jobName, String jobGroup, String cronExpression, Object param) throws ClassNotFoundException {
		Class<? extends Job> jobClass = null;
		try {
			jobClass = (Class<? extends Job>) Class.forName(jobClassName);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("类名=" + jobClassName);
		}
		createScheduleJob(scheduler, jobClass, jobName, jobGroup, cronExpression, param);
	 }
		
		
	 /**
	 * 使用默认的Scheduler 创建定时任务
	 */
	public static void createScheduleJob(Class<? extends Job> jobClass, String jobName, String jobGroup, String cronExpression, Object param) {
		createScheduleJob(scheduler, jobClass, jobName, jobGroup, cronExpression, param);
	 }
	
	 /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler,  Class<? extends Job> jobClass, String jobName, String jobGroup,
                                         String cronExpression, Object param) {
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
        //放入参数，运行时的方法可以获取
        if (StringUtils.isNotNull(param)) {
        	jobDetail.getJobDataMap().put(JOB_DATA_MAP, param);
        }
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
            .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
           System.out.println("创建定时任务失败");
        }
    }
    
    /**
     * 暂停任务 
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 使用默认的Scheduler 暂停任务 
     */
    public static void pauseJob(String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 恢复任务 
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 使用默认的Scheduler 恢复任务 
     */
    public static void resumeJob(String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 删除任务 
     */
    public static void deleteJob(Scheduler scheduler, String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 使用默认的Scheduler 删除任务 
     */
    public static void deleteJob(String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 使用默认的Scheduler 立刻执行该任务 
     */
    public static void triggerJob(String jobName, String jobGroup) {
    	JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    	try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 关闭所有的Scheduler
     */
    public static void shutdownAllScheduler() {
    	try {
    		log.info("quartz关闭！");
    		Collection<Scheduler> allSchedulers = SchedulerRepository.getInstance().lookupAll();
    		for (Scheduler scheduler : allSchedulers) {
    			scheduler.shutdown(true);
    		}
		} catch (SchedulerException e) {
			log.error("quartz定时任务关闭失败！");
			e.printStackTrace();
		}
    }
    
}
 