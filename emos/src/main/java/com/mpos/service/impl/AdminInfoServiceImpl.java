package com.mpos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.AdminInfoDao;
import com.mpos.dto.TadminInfo;
import com.mpos.service.AdminInfoService;

@Service
public class AdminInfoServiceImpl implements AdminInfoService{
	@Autowired
	private AdminInfoDao adminInfoDao;

	public TadminInfo getAdminInfoById(String adminId) {
		// TODO Auto-generated method stub
		return adminInfoDao.get(adminId);
	}

	public void updateAdminInfoAvatar(TadminInfo adminInfo) {
		
		// TODO Auto-generated method stub
		
		//adminInfoDao.updateInfoAvatar(adminInfo);
		 adminInfoDao.update(adminInfo);
	}

	public void updateAdminInfo(TadminInfo adminInfo) {
		// TODO Auto-generated method stub
		adminInfoDao.updateInfo(adminInfo);
	}

	public void createAdminInfo(TadminInfo adminInfo) {
		// TODO Auto-generated method stub
		adminInfoDao.create(adminInfo);
	}

	public void delete(String adminId) {
		// TODO Auto-generated method stub
		
	}
    
	
	

}
