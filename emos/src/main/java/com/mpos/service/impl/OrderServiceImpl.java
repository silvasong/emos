package com.mpos.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.dao.OrderDao;
import com.mpos.dto.Torder;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.OrderService;
public class OrderServiceImpl implements OrderService {
	
    @Autowired
	private OrderDao orderDao;
    
	public PagingData loadOrderList(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		String searchJsonStr = dtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="creater"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getString(key)));
					}else if(key=="orderStatus"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}else if(key=="orderId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}else if(key=="storeId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
				}
			}
			
			if(jsonObj.getString("startTime") != null && !jsonObj.getString("startTime").isEmpty() 
				    && jsonObj.getString("endTime") != null && !jsonObj.getString("endTime").isEmpty()){
			   try {
				   criterionsList.add(Restrictions.between("createTime", ConvertTools.dateString2Long(jsonObj.getString("startTime"),"yyyy-MM-dd"), ConvertTools.dateString2Long(jsonObj.getString("endTime"),"yyyy-MM-dd")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if(jsonObj.getString("startTime") != null && !jsonObj.getString("startTime").isEmpty()){
				try {
					   criterionsList.add(Restrictions.ge("createTime", ConvertTools.dateString2Long(jsonObj.getString("startTime"),"yyyy-MM-dd")));
					} catch (ParseException e) {						
						e.printStackTrace();
					}
			}
			else if(jsonObj.getString("endTime") != null && !jsonObj.getString("endTime").isEmpty()){
				try {
					   criterionsList.add(Restrictions.le("createTime", ConvertTools.dateString2Long(jsonObj.getString("endTime"),"yyyy-MM-dd")));
					} catch (ParseException e) {						
						e.printStackTrace();
					}
			}
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return orderDao.findPage("orderId",false,criterions,dtp.iDisplayStart, dtp.iDisplayLength);
		}
		return orderDao.findPage("orderId",false,Restrictions.eq("orderStatus", 0),dtp.getiDisplayStart(), dtp.getiDisplayLength());
	}

	public Torder getTorderById(int id) {
		// TODO Auto-generated method stub
		return orderDao.get(id);
	}

	public void update(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.update(torder);
	}

	public void createOrder(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.create(torder);
	}

	public void deleteOrder(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.delete(torder);
	}

	public List<Torder> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.select(hql, params);
	}

	public Object get(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.getObject(hql, params);
	}

	public List<Object[]> getList(String sql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.getList(sql, params);
	}

	public Object getBySql(String sql,Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.getBySql(sql, params);
	}
	
	public List<Object[]> getListBySql(String sql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.getListBySql(sql, params);
	}
}
