package com.emos.service;

import java.util.List;

import com.emos.dto.TcategoryAttribute;
import com.emos.dto.Tlanguage;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface CategoryAttributeService {

	void createCategoryAttribute(TcategoryAttribute attribute);
	void deleteCategoryAttribute(TcategoryAttribute attribute);
	void updateCategoryAttribute(TcategoryAttribute attribute);
	TcategoryAttribute getCategoryAttribute(Integer attributeId);
	public List<TcategoryAttribute> getCategoryAttributeByCategoryid(Integer id,Tlanguage language);
	public List<TcategoryAttribute> getCategoryAttributeByCategoryid(Integer id);
	PagingData loadAttributeList(String id, DataTableParamter dtp);
	void deleteAttributeByIds(Integer[] idArr);
}
