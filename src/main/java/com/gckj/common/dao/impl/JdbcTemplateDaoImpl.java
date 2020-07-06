package com.gckj.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.gckj.common.dao.BaseDao;
import com.gckj.common.dao.JdbcTemplateDao;



public class JdbcTemplateDaoImpl<T> implements JdbcTemplateDao<T>{
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	protected BaseDao<T> baseDao;
	
}
