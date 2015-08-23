package com.emos.service;

import java.util.List;

import com.emos.dto.Tcategory;
import com.emos.dto.Tlanguage;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface CategoryService {

	void createCategory(Tcategory category);
	void deleteCategory(Tcategory category);
	void updateCategory(Tcategory category);
	Tcategory getCategory(Integer categoryId);
	PagingData loadCategoryList(DataTableParamter dtp);
	void deleteCategoryByIds(Integer[] idArr);
	void cloneCategoryByIds(Integer[] ids);
	public List<Tcategory> getallCategory(Integer storeId);
	public List<Tcategory> getallCategory(Integer type,Tlanguage language,Integer storeId);
	
}
