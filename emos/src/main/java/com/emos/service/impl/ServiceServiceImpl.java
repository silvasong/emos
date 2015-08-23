package com.emos.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emos.commons.ConvertTools;
import com.emos.commons.EMailTool;
import com.emos.commons.SecurityTools;
import com.emos.dao.AdminInfoDao;
import com.emos.dao.AdminUserDao;
import com.emos.dao.ServiceDao;
import com.emos.dao.StoreDao;
import com.emos.dto.TadminInfo;
import com.emos.dto.TadminRole;
import com.emos.dto.TadminUser;
import com.emos.dto.TemaiMessage;
import com.emos.dto.Tservice;
import com.emos.dto.Tstore;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;
import com.emos.service.ServiceService;
@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceDao serviceDao;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminInfoDao adminInfoDao;
	
	
	public void save(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.save(service);
	}

	public void delete(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.delete(service);
	}

	public void delete(Integer serviceId) {
		// TODO Auto-generated method stub
		serviceDao.delete(serviceId);
	}

	public void update(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.update(service);
	}

	public PagingData loadList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = serviceDao.createCriteria();
		criteria.addOrder(Order.desc("serviceId"));
		//criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSON.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("servieName")){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return serviceDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return serviceDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	public void delete(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceDao.delete(hql, params);
	}

	public void update(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceDao.update(hql, params);
	}

	public List<Tservice> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return serviceDao.select(hql, params);
	}

	public Tservice get(Integer serviceId) {
		return serviceDao.get(serviceId);
	}

	@SuppressWarnings("unchecked")
	public List<Tservice> load() {
		Criteria criteria = serviceDao.createCriteria();
		criteria.add(Restrictions.eq("status", true));
		return criteria.list();
	}

	public Map<String,String> register(TadminUser user, Integer serviceId, String mobile,Boolean status,String filePath,String url) {
		Map<String, String> res = new HashMap<String, String>();
		Tstore store = new Tstore();
		if(serviceId==null){
			serviceId=0;
		}
		Tservice service= serviceDao.get(serviceId);
		store.setServiceId(serviceId);
		store.setPublicKey("888888");
		store.setClientPwd("888888");
		store.setStoreName(" ");
		if(service.getServiceId()>0&&service.getServicePrice()>1){
			store.setStatus(false);
		}else{
			store.setStatus(true);
		}
		store.setAutoSyncStatus(false);
		store.setServiceDate(ConvertTools.longTimeAIntDay(System.currentTimeMillis(), service.getValidDays()));
		store.setStoreCurrency("¥");
		store.setStoreLangId("2");
		store.setPrintType(1);
		storeDao.save(store);
		//tableDao.create(new Ttable("A01", 4, store.getStoreId()));
		user.setStoreId(store.getStoreId());
		user.setCreatedTime(System.currentTimeMillis());
		user.setCreatedBy("admin");
		user.setAdminId(user.getEmail());
		user.setAdminRole(new TadminRole(3));
		user.setStatus(status);
		user.setPassword(SecurityTools.MD5(user.getPassword()));
		adminUserDao.create(user);
		TadminInfo info = new TadminInfo();
		info.setAdminId(user.getAdminId());
		info.setMobile(mobile);
		adminInfoDao.create(info);
		if(service.getServiceId()>0&&service.getServicePrice()>0){
			String orderNum= "CampRay"+System.currentTimeMillis();
			
			
			res.put("price", service.getServicePrice()+"");
			res.put("orderNum", orderNum);
			res.put("subject", service.getServiceName());
		}
		res.put("email", user.getEmail());
		res.put("serviceName", service.getServiceName());
		res.put("startTime", ConvertTools.longToDateString(System.currentTimeMillis()));
		res.put("endTime",  ConvertTools.longToDateString(store.getServiceDate()));
		res.put("storeId",store.getStoreId()+"");
		res.put("storeCode", ConvertTools.bw(store.getStoreId(), 8, "S"));
		res.put("publicKey", store.getPublicKey());
		res.put("url", url);
		if(status){
			TemaiMessage message = TemaiMessage.getCreate(res);
			List<File> files = new LinkedList<File>();
			File file = new File(filePath);
			files.add(file);
			message.setFiles(files);
			EMailTool.send(message);
		}
		return res;
	}

	public Map<String, String> getInfoByEmail(String email) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		String sql = "select store.service_date,service.service_name,store.store_id,store.public_key from emos_cloud.emos_admin as admin left join emos_cloud.emos_store as store on admin.store_id = store.store_id left join emos_cloud.emos_service as service on service.service_id=store.service_id where admin.email=:email";
		Object[] res = (Object[]) adminUserDao.getBySql(sql, params);
		map.put("email", email);
		 map.put("serviceName", res[1].toString());
		 map.put("startTime", ConvertTools.longToDateString(System.currentTimeMillis()));
		 BigInteger time = (BigInteger) res[0];
		 map.put("endTime",  ConvertTools.longToDateString(time.longValue()));
		 map.put("storeCode", ConvertTools.bw((Integer)res[2], 8, "S"));
		 map.put("publicKey", (String)res[3]);
		return map;
	}

	public void deleteInfo(String adminId, Integer storeId) {
		adminUserDao.delete(adminId);
		adminInfoDao.delete(adminId);
		storeDao.delete(storeId);
	}

}
