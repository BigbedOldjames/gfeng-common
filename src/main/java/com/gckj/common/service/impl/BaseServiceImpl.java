package com.omysoft.common.service.impl;


import com.alibaba.fastjson.JSON;
import com.omysoft.common.dao.BaseDao;
import com.omysoft.common.dto.Page;
import com.omysoft.common.service.BaseService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;




/**
 * 基础业务逻辑
 * 
 * @author 叶赟
 * 
 * @param <T>
 */
@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	private BaseDao<T> commonDao;

	public void clear() {
		commonDao.clear();
	}
	
	public void flush() {
		commonDao.flush();
	}
	
	public Serializable merge(T o) {
		return commonDao.merge(o);
	}
	
	public Serializable save(T o) {
		return commonDao.save(o);
	}

	public void delete(T o) {
		commonDao.delete(o);
	}

	public void update(T o) {
		commonDao.update(o);
	}

	public void saveOrUpdate(T o) {
		commonDao.saveOrUpdate(o);
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public T getById(Serializable id) {
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return commonDao.getById(c, id);
	}

	public T getByHql(String hql) {
		return commonDao.getByHql(hql);
	}

	public T getByHql(String hql, Map<String, Object> params) {
		return commonDao.getByHql(hql, params);
	}
	
	public Map getBySql(String sql, Map<String, Object> params) {
		return commonDao.getBySql(sql, params);
	}

	public T getBySql(String sql, Map<String, Object> params, Class<?> resultClass) {
		// TODO Auto-generated method stub
		List list = findBySql(sql, params,resultClass);
		List<?> l = JSON.parseArray(JSON.toJSONString(list),resultClass);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}
	
	public List<T> find(String hql) {
		return commonDao.find(hql);
	}

	public List<T> find(String hql, Map<String, Object> params) {
		return commonDao.find(hql, params);
	}

	public List<T> find(String hql, int page, int rows) {
		return commonDao.find(hql, page, rows);
	}
	
	public List<T> findMaxResults(String hql, Map<String, Object> params, int maxResults) {
		return commonDao.findMaxResults(hql, params, maxResults);
	}

	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return commonDao.find(hql, params, page, rows);
	}

	public List<T> find(int page, int rows) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select distinct t from " + className + " t where 1=1";
		return find(hql, null, page, rows);
	}

	public Long count(String hql) {
		return commonDao.count(hql);
	}

	public Long count(String hql, Map<String, Object> params) {
		return commonDao.count(hql, params);
	}

	public Long count() {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String hql = "select count(distinct t) from " + className + " t where 1=1";
		return count(hql);
	}

	public int executeHql(String hql) {
		return commonDao.executeHql(hql);
	}

	public int executeHql(String hql, Map<String, Object> params) {
		return commonDao.executeHql(hql, params);
	}

	public List findBySql(String sql) {
		return commonDao.findBySql(sql);
	}

	public List findBySql(String sql, int page, int rows) {
		return commonDao.findBySql(sql, page, rows);
	}

	public List findBySql(String sql, Map<String, Object> params) {
		return commonDao.findBySql(sql, params);
	}

	public List findBySql(String sql, Map<String, Object> params, int page, int rows) {
		return commonDao.findBySql(sql, params, page, rows);
	}

	public int executeSql(String sql) {
		return commonDao.executeSql(sql);
	}

	public int executeSql(String sql, Map<String, Object> params) {
		return commonDao.executeSql(sql, params);
	}

	public BigInteger countBySql(String sql) {
		return commonDao.countBySql(sql);
	}

	public BigInteger countBySql(String sql, Map<String, Object> params) {
		return commonDao.countBySql(sql, params);
	}
	
	public  Page find(Page page, String hql, Map<String, Object> params){
		return commonDao.find(page, hql, params);
	}
	
	public  Page find(Page page, String hql){
		return commonDao.find(page, hql);
	}

	public  Page findBySql(Page page, String sqlString, Map<String, Object> params, Class<?> resultClass) {
		return commonDao.findBySql(page, sqlString, params, resultClass);
	}
	
	public  Page findBySql(Page page, String sqlString, Class<?> resultClass) {
		return commonDao.findBySql(page, sqlString, null, resultClass);
	}

	public <E> List<E> findBySql(String sql, Map<String, Object> params, Class<E> resultClass) {
		return commonDao.findBySql(sql, params, resultClass);
	}


}
