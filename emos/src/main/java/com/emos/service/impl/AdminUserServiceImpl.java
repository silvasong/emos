/**   
 * @Title: AdminUserServiceImpl.java 
 * @Package com.uswop.service 
 *
 * @Description: User Points Management System
 * 
 * @date Sep 11, 2014 7:21:25 PM
 * @version V1.0   
 */ 
package com.emos.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emos.dao.AdminInfoDao;
import com.emos.dao.AdminUserDao;
import com.emos.dto.TadminInfo;
import com.emos.dto.TadminUser;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;
import com.emos.service.AdminUserService;


@Service
public class AdminUserServiceImpl implements AdminUserService {
	
	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminInfoDao adminInfoDao;
				

	/**
	 * (non-Javadoc)
	 * <p>Title: getAdminUserById</p> 
	 * <p>Description: </p> 
	 * @param userId
	 * @return 
	 * @see com.bps.service.AdminUserService#getAdminUserById(java.lang.String) 
	 */
	public TadminUser getAdminUserById(String userId) {
		Criteria criteria = adminUserDao.createCriteria();
		criteria.add(Restrictions.eq("status", true));
		criteria.add(Restrictions.or(Restrictions.eq("adminId", userId),Restrictions.eq("email", userId)));
		return (TadminUser) criteria.uniqueResult();	
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: createAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#createAdminUser(com.bps.dto.TadminUser) 
	 */
	public void createAdminUser(TadminUser adminUser) {		
		adminUserDao.create(adminUser);		
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: updateAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#updateAdminUser(com.bps.dto.TadminUser) 
	 */
	public void updateAdminUser(TadminUser adminUser) {
	
		adminUserDao.update(adminUser);

	}

	/**
	 * (non-Javadoc)
	 * <p>Title: deleteAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#deleteAdminUser(com.bps.dto.TadminUser) 
	 */
	public void deleteAdminUser(TadminUser adminUser) {
		adminUserDao.delete(adminUser);
	}

	public void deleteAdminUserById(int id) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAdminUserByIds(String[] ids) {
		adminUserDao.delete(ids);
	}

	public PagingData loadAdminUserList(DataTableParamter rdtp) {
		String searchJsonStr=rdtp.getsSearch();
		List<Criterion> criterionsList=new ArrayList<Criterion>();
		Criteria criteria = adminUserDao.createCriteria();
		criteria.add(Restrictions.ne("adminRole.roleId", 1));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="status"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}
					else if(key=="storeId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
					else{
						criterionsList.add(Restrictions.eq(key, jsonObj.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionsList) {
				criteria.add(criterion);
			}
			return adminUserDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return adminUserDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}
	
   public int getAdminUserAmount() {
		// TODO Auto-generated method stub
		return adminUserDao.getCount();
	}


   public void updateAdminUserPassword(TadminUser adminUser) {
	// TODO Auto-generated method stub
	adminUserDao.update(adminUser);
}

public TadminUser getTadminUsersByEmail(String email) {
	// TODO Auto-generated method stub
	return adminUserDao.findUnique("email", email);
}

@SuppressWarnings("unchecked")
public List<TadminUser> getUserByStoreId(Integer storeId) {
	// TODO Auto-generated method stub
	return adminUserDao.createCriteria().add(Restrictions.eq("storeId", storeId)).list();
}

public Boolean emailExist(String email){
	TadminUser tt = adminUserDao.findUnique("email", email);
	if(tt==null){
		return false;
	}
	return true;
}

public void updateUserStatus(String[] ids, boolean status) {
	// TODO Auto-generated method stub
	adminUserDao.updateUserStatus(ids, status);
}

public Long getRightByEmail(String email) {
	// TODO Auto-generated method stub
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("email", email);
	String sql = "select r_right.role_rights from emos.emos_admin as admin left join emos.emos_store as store on admin.store_id=store.store_id left join emos.emos_service as service on service.service_id=store.service_id left join emos.emos_admin_role_rights as r_right on r_right.role_id=service.role_id where admin.email=:email and store.status=true";
	Object object = adminUserDao.getBySql(sql, params);
	if(object==null){
		return 0L;
	}else{
		BigInteger value = (BigInteger)object;
		return value.longValue();
	}
}

public void saveStoreUser(TadminUser adminUser) {
	adminUserDao.create(adminUser);
	adminInfoDao.create(new TadminInfo(adminUser.getAdminId()));
}

public TadminUser getByCode(String code) {
	// TODO Auto-generated method stub
	return adminUserDao.findUnique("code", code);
}

public TadminUser getUserByEmail(String email) {
	// TODO Auto-generated method stub
	return null;
}

}
