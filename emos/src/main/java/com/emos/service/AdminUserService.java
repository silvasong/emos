package com.emos.service;

import java.util.List;

import com.emos.dto.TadminUser;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface AdminUserService {
	
	TadminUser getAdminUserById(String userId);
	
	public Boolean emailExist(String email);
	
	void createAdminUser(TadminUser adminUser);
	
	void saveStoreUser(TadminUser adminUser);
	
	void updateAdminUser(TadminUser adminUser);
	
	void updateAdminUserPassword(TadminUser adminUser);
	
	void deleteAdminUser(TadminUser adminUser);
	
	void deleteAdminUserById(int id);
	
	void deleteAdminUserByIds(String[] ids);
	
	public PagingData loadAdminUserList(DataTableParamter rdtp);
	
	public int getAdminUserAmount();
	
	public TadminUser getTadminUsersByEmail(String email);
	
	public TadminUser getUserByEmail(String email);
	
	public TadminUser getByCode(String code);
	
	List<TadminUser> getUserByStoreId(Integer storeId);
	
	public Long getRightByEmail(String email);
	
	public void updateUserStatus(String[] ids,boolean status);
		
}
