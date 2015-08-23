package com.emos.service;


import java.util.List;
import java.util.Map;

import com.emos.dto.Torder;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface OrderService {
	
	PagingData loadOrderList(DataTableParamter dtp);
	
	Torder getTorderById(int id);
	
	List<Object[]> getList(String sql,Map<String, Object> params);
	
	void update(Torder torder);
	
	void createOrder(Torder torder);
	
	void deleteOrder(Torder torder);
	
	Object get(String hql,Map<String, Object> params);
	
	Object getBySql(String sql,Map<String, Object> params);
	
	public List<Object[]> getListBySql(String sql, Map<String, Object> params);
	
	List<Torder> select(String hql,Map<String, Object> params);
}
