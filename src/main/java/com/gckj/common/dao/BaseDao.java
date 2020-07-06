package com.gckj.common.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.gckj.common.dto.Page;

/**
 * @Description：基础数据库操作类，其他DAO继承此类获取常用的数据库操作方法，基本不需要自己建立DAO。
 * @author：ldc
 * date：2020-06-23

 * @param <T>   模型
 *            
 */
public interface BaseDao<T> {
	
	
	public Session getCurrentSession();
	/**
	 * 刷新对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public void clear();
	
	/**
	 * 刷新对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public void flush();
	
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable merge(T o);

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void update(T o);

	/**
	 * 保存或更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 通过主键获得对象
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T getById(Class<T> c, Serializable id);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	public T getByHql(String hql);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getByHql(String hql, Map<String, Object> params);
	
	public Map getBySql(String sql, Map<String, Object> params);

	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	public List<T> find(String hql);

	/**
	 * 获得对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params);
	
	public List<Map> find(String hql, Map<String, Object> params, Class<?> resultClass);
	
	
	/**
	 * 返回N个对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param maxResults
	 *            最大返回N个对象
	 * @return List
	 */
	public List<T> findMaxResults(String hql, Map<String, Object> params, int maxResults);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, int page, int rows);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T)
	 * @return long
	 */
	public Long count(String hql);

	/**
	 * 统计数目
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T where xx = :xx)
	 * @param params
	 *            参数
	 * @return long
	 */
	public Long count(String hql, Map<String, Object> params);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params);
	
	/**
	 * 获得指定类型的结果集（容易出现参数类型不匹配错误）
	 * 数据库中浮点类型的， java的DTO使用BigDecimal类型
	 * @param sql
	 * 			sql语句
	 * @param params
	 * 			查询参数
	 * @param resultClass
	 * 			类的Class对象
	 * @return 
	 */
	public <E> List<E> findBySql(String sql, Map<String, Object> params, Class<E> resultClass);

	/**
	 * 获得分页的MAP类型结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 获得分页的指定类型结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param resultClass
	 * 			     类的Class对象
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public <E> List<E> findBySql(String sql, Map<String, Object> params, Class<E> resultClass, int page, int rows);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params);

	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 数目
	 */
	public BigInteger countBySql(String sql);

	/**
	 * 统计
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public BigInteger countBySql(String sql, Map<String, Object> params);
	
	public  Page find(Page page, String hql);
	
	public  Page find(Page page, String hql, Map<String, Object> params);
	
	public  Page findBySql(Page page, String sql);
	
	public  Page findBySql(Page page, String sql, Map<String, Object> params);
	
	public  Page findBySql(Page page, String sql, Class<?> resultClass);
	
	public  Page findBySql(Page page, String sqlString, Map<String, Object> params, Class<?> resultClass);

}
