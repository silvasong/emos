package com.emos.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.emos.dto.Tsetting;
import com.emos.model.PagingData;

public interface SystemSettingService {
	
	Tsetting getSystemsettingById(Integer Id);
	
	void createSystemsetting(Tsetting setting);
	
	void updateSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(int id);
	
	void deleteSystemsettingByIds(Integer[] ids);
	
	public PagingData loadSystemsetting();

	public void cachedSystemSettingData() throws UnsupportedEncodingException, IOException;
	public void cachedSystemSet();
	
	public PagingData getStoreSetting();
	
	Tsetting getSystemSettingByName(String name);

}
