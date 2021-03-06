package com.emos.service;

import java.util.List;
import java.util.Map;

import com.emos.dto.TadminUser;
import com.emos.dto.Tservice;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface ServiceService {
	public void save(Tservice service);
	public void delete(Tservice service);
	public void delete(Integer serviceId);
	public Tservice get(Integer serviceId);
	public void update(Tservice service);
	public List<Tservice> load();
	public PagingData loadList(DataTableParamter rdtp);
	
	public Map<String, String> register(TadminUser user,Integer serviceId,String mobile,Boolean status,String filePath,String url);
	
	public Map<String, String> getInfoByEmail(String email);
	public void deleteInfo(String adminId,Integer storeId);
	public void delete(String hql,Map<String, Object> params);
	public void update(String hql,Map<String, Object> params);
	public List<Tservice> select(String hql,Map<String, Object> params);
}
