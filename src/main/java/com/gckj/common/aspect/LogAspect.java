package com.gckj.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description： 日志切面
 * @author：ldc
 * date：2020-06-23
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

//    @Pointcut(value = "execution(* com.omysoft.*.controller..*.*(..))")
//    public void pointCutMethod() {
//    }
//
//    @Around("pointCutMethod()")
//    public Object around(ProceedingJoinPoint joinPoint) {
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//
//        String uri = request.getRequestURI();
//
//        String method = request.getMethod();
//
//        Object[] args = joinPoint.getArgs();
//
//        /*if (method == null || !("POST").equalsIgnoreCase(method)) {
//            return new Result(ResultStatus.FAILED.getCode(), "所有请求都必须是POST请求", false);
//        }
//        if (args[0] == null) {
//            return new Result(ResultStatus.FAILED.getCode(), "参数不能为空", false);
//        }*/
//     /*   Method[] methods = args[0].getClass().getMethods();
//
//        for (Method method1 : methods) {
//            if(method.equals("public ")){
//
//            }
//        }*/
//
//   /*     if (args[0] == null || MapBuilder.objectToMap(args[0], true).get("memberId") == null) {
//            return new Result(ResultStatus.FAILED.getCode(), "参数不能为空,且参数必须包含memberId", false);
//        }*/
//
//        log.info("***************  请求接口:" + uri + ",请求方式:" + method + ",请求参数:" + Arrays.toString(args) + "  ***************");
//
//        Long startTime = System.currentTimeMillis();
//
//        Object rtn;
//        try {
//            rtn = joinPoint.proceed();
//            Long endTime = System.currentTimeMillis();
//            log.info("***************  请求接口:" + uri + ",请求方式:" + method + ",请求用时:" + (endTime - startTime) / 1000.0 + "s,返回数据:" + (rtn == null ? "" : JSON.toJSONString(rtn)) + "  ***************");
//            return rtn;
//        } catch (Throwable e) {
//            e.printStackTrace();
//            log.error("***************  请求接口:" + uri + ",请求方式:" + method + ",出现异常:" + e.getMessage());
//            return new Result(ResultStatus.FAILED.getCode(), "出现异常", true, e.getMessage());
//        }
//    }
}
