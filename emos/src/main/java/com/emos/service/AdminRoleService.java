package com.emos.service;

import java.util.List;

import com.emos.dto.TadminRole;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface AdminRoleService {
	
	TadminRole getAdminRoleById(int roleId);
	
	List<TadminRole> getAllAdminRoles();
	
	void createAdminRole(TadminRole adminRole);
	
	void updateAdminRole(TadminRole adminRole);
	
	void deleteAdminRole(TadminRole adminRole);
	
	void deleteAdminRoleById(int id);
	
	void deleteAdminRolesByIds(Integer[] ids);
	
	PagingData loadAdminRolesList(DataTableParamter rdtp);
		
}
