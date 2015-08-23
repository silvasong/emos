package com.emos.service;

import com.emos.dto.TadminInfo;

public interface AdminInfoService{
	TadminInfo getAdminInfoById(String adminId);
	void updateAdminInfo(TadminInfo adminInfo);
	void updateAdminInfoAvatar(TadminInfo adminInfo);
	void createAdminInfo(TadminInfo adminInfo);
	void delete(String adminId);
}
