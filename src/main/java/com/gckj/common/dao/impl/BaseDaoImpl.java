package com.gckj.common.dao.impl;

import com.gckj.common.dao.BaseDao;
import com.gckj.common.dto.Page;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description：dao执行接口基类
 * @author：ldc
 * date：2020-06-23
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Autowired
    private SessionFactory sessionFactory;

/**
 * @Description：获得当前事物的session
 * @author：ldc
 * date：2020-06-23
 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	// -------------- save/update --------------

	public void clear() {
		getCurrentSession().close();
	}
	
	public void flush() {
		getCurrentSession().flush();
	}
	
	public Serializable merge(T o) {
		if (o != null) {
			return (Serializable) getCurrentSession().merge(o);
		}
		return null;
	}

//	@Transactional
	public Serializable save(T o) {
		if (o != null) {
			try {
				// 插入前执行方法
				for (Method method : o.getClass().getMethods()){
					PrePersist pp = method.getAnnotation(PrePersist.class);
					if (pp != null){
						method.invoke(o);
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return getCurrentSession().save(o);
		}
		return null;
	}

//	@Transactional
	public void update(T o) {
		if (o != null) {
			try {
				for (Method method : o.getClass().getMethods()){
					PreUpdate pu = method.getAnnotation(PreUpdate.class);
					if (pu != null){
						method.invoke(o);
						break;
					}
				}
			}  catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			getCurrentSession().update(o);
		}
	}

	public void saveOrUpdate(T o) {
		if (o != null) {
			getCurrentSession().saveOrUpdate(o);
		}

	}

	public void delete(T o) {
		if (o != null) {
			getCurrentSession().delete(o);
		}
	}

	public T getById(Class<T> c, Serializable id) {
		return (T) getCurrentSession().get(c, id);
	}

	public T getByHql(String hql) {
		return getByHql(hql, null);
	}

	public T getByHql(String hql, Map<String, Object> params) {
		List<T> l = find(hql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}
	
	public Map getBySql(String sql, Map<String, Object> params) {
		List<Map> l = findBySql(sql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return new HashMap();
	}

	public List<T> find(String hql) {
		return find(hql, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = createQuery(hql, params);
		return q.list();
	}
	
	public List<Map> find(String hql, Map<String, Object> params, Class<?> resultClass) {
		Query q = createQuery(hql, params);
		if (resultClass != null){
			if (resultClass == Map.class){
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}else if (resultClass == List.class){
				q.setResultTransformer(Transformers.TO_LIST);
			}
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = createQuery(hql, params);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, int page, int rows) {
		return find(hql, null, page, rows);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findMaxResults(String hql, Map<String, Object> params, int maxResults) {
		Query q = createQuery(hql, params);
		return q.setFirstResult(0).setMaxResults(maxResults).list();
	}

	public Long count(String hql) {
		return count(hql, null);
	}

	public Long count(String hql, Map<String, Object> params) {
		Query q = createQuery(hql, params);
		return (Long) q.uniqueResult();
	}

	public int executeHql(String hql) {
		return executeHql(hql, null);
	}

	public int executeHql(String hql, Map<String, Object> params) {
		Query q = createQuery(hql, params);
		return q.executeUpdate();
	}

	public List<Map> findBySql(String sql) {
		return findBySql(sql, null);
	}

	public List<Map> findBySql(String sql, int page, int rows) {
		return findBySql(sql, null, page, rows);
	}

	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = createSqlQuery(sql, params);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public <E> List<E> findBySql(String sql, Map<String, Object> params, Class<E> resultClass) {
		List<E> list = createSqlQuery(sql, params).setResultTransformer(Transformers.aliasToBean(resultClass)).list();
		return list;
	}

	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = createSqlQuery(sql, params);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public <E> List<E> findBySql(String sql, Map<String, Object> params, Class<E> resultClass, int page, int rows) {
		SQLQuery q = createSqlQuery(sql, params);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.aliasToBean(resultClass)).list();
	}

	// -------------- SQL execute --------------
	public int executeSql(String sql) {
		return executeSql(sql, null);
	}

	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = createSqlQuery(sql, params);
		return q.executeUpdate();
	}
	
	// -------------- SQL count --------------
	public BigInteger countBySql(String sql) {
		return countBySql(sql, null);
	}

	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = createSqlQuery(sql, params);
		return (BigInteger) q.uniqueResult();
	}
	
	// -------------- HQL Query --------------
	public  Page find(Page page, String hql){
		return find(page, hql, null);
	}
	
	@SuppressWarnings("unchecked")
	public  Page find(Page page, String hql, Map<String, Object> params){
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countQlString = "select count(*) " + removeSelect(removeOrders(hql));  
	        Query query = createQuery(countQlString, params);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setCount(Long.valueOf(list.get(0).toString()));
	        }else{
	        	page.setCount(Long.valueOf(list.size()));
	        }
			if (page.getCount() < 1) {
				return page;
			}
    	}
    	// order by
    	String ql = hql;
    	ql += page.generateOrderBy();
		Query query = createQuery(ql, params);
        if (!page.isDisabled()){
        	query.setFirstResult((page.getPageNumber() - 1) * page.getPageSize());
        	query.setMaxResults(page.getPageSize()); 
        }
        page.setList(query.list());
		return page;
    }
	 
	// -------------- SQL Query --------------
	public  Page findBySql(Page page, String sql){
    	return findBySql(page, sql, null, null);
    }
	public  Page findBySql(Page page, String sql, Map<String, Object> params){
    	return findBySql(page, sql, params, null);
    }
	public  Page findBySql(Page page, String sql, Class<?> resultClass){
    	return findBySql(page, sql, null, resultClass);
    }
    @SuppressWarnings("unchecked")
    public  Page findBySql(Page page, String sqlString, Map<String, Object> params, Class<?> resultClass){
		// get count
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countSql = "select count(*) " + removeSelect(removeOrders(sqlString));  
//	        page.setCount(Long.valueOf(createSqlQuery(countSqlString, parameter).uniqueResult().toString()));
	        Query query = createSqlQuery(countSql, params);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	//用于汇总group语句的返回条数
	        	if(params.get("isGroup")!=null){
	        		page.setCount(Long.valueOf(list.size()+""));
	        	}else{
	        		page.setCount(Long.valueOf(list.get(0).toString()));
	        	}
	        }else{
	        	page.setCount(Long.valueOf(list.size()));
	        }
			if (page.getCount() < 1) {
				return page;
			}
    	}
    	// order by
    	String sql = sqlString;
    	sql += page.generateOrderBy();
        SQLQuery query = createSqlQuery(sql, params); 
        if (!page.isDisabled()){
        	query.setFirstResult((page.getPageNumber() - 1) * page.getPageSize());
        	query.setMaxResults(page.getPageSize()); 
        }
        setResultTransformer(query, resultClass);
        page.setList(query.list());
		return page;
    }
    
 // -------------- Query Tools --------------
	 /** 
     * 去除qlString的select子句。 
     */  
    private String removeSelect(String qlString){  
        int beginPos = qlString.toLowerCase().indexOf("from");  
        return qlString.substring(beginPos);  
    }
    
    /** 
     * 去除hql的orderBy子句。 
     */  
    private String removeOrders(String qlString) {  
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(qlString);  
        StringBuffer sb = new StringBuffer();  
        while (m.find()) {  
            m.appendReplacement(sb, "");  
        }
        m.appendTail(sb);
        return sb.toString();  
    }

    public Query createQuery(String hql, Map<String, Object> params) {
		Query query = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(value instanceof Collection<?>){
                    query.setParameterList(key, (Collection<?>)value);
                }else if(value instanceof Object[]){
                    query.setParameterList(key, (Object[])value);
                }else{
                    query.setParameter(key, value);
                }
			}
		}
		return query;
	}

    public SQLQuery createSqlQuery(String hql, Map<String, Object> params) {
    	SQLQuery query = getCurrentSession().createSQLQuery(hql);
    	if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(value instanceof Collection<?>){
                    query.setParameterList(key, (Collection<?>)value);
                }else if(value instanceof Object[]){
                    query.setParameterList(key, (Object[])value);
                }else{
                    query.setParameter(key, value);
                }
			}
		}
		return query;
	}
    
    /**
	 * 设置查询结果类型
	 */
	private void setResultTransformer(SQLQuery query, Class<?> resultClass){
		if (resultClass != null){
			if (resultClass == Map.class){
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}else if (resultClass == List.class){
				query.setResultTransformer(Transformers.TO_LIST);
			}else{
				query.addEntity(resultClass);
			}
		}
	}

}
