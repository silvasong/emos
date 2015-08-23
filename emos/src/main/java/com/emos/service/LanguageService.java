package com.emos.service;

import java.util.List;

import com.emos.dto.Tlanguage;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface LanguageService {

	
	Tlanguage getLanguageById(Integer Id);
	
	void createLanguage(Tlanguage setting);
	
	void updateLanguage(Tlanguage setting);
	
	void deleteLanguage(Tlanguage setting);
	
	void deleteLanguageByIds(Integer[] ids);
	
	void activeLanguageByids(Integer ids[]);
	
	public PagingData loadLanguageList(DataTableParamter rdtp);
	
	List<Tlanguage> loadAllTlanguage();
	
	Tlanguage get(String locale);
	
	Tlanguage getLanguageBylocal(String local);
	
	List<Tlanguage> getLangListByStoreId(Integer storeId);
}
