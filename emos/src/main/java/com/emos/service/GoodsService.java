package com.emos.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.emos.dto.Tproduct;
import com.emos.model.AddProductModel;
import com.emos.model.DataTableParamter;
import com.emos.model.FileMeta;
import com.emos.model.PagingData;

public interface GoodsService {
	
	void createGoods(Tproduct product);
	
	PagingData loadGoodsList(DataTableParamter dtp);
	
	public void deletegoodsByids(Integer ids[],Integer storeId);
	
	public void putGoods(String productIds,Integer storeId);
	
	public void outGoods(String productIds,Integer storeId);
	
	public void activegoodsByids(Integer ids[]);
	
	Tproduct findbyProductName(String productName);
	
	Tproduct getTproductByid(Integer id);
	
	void updateGoods(Tproduct product);
	
	List<Tproduct> loadAll();
	
	void saveTest(Integer storeId,Integer menuId,MultipartFile file,String name);
	
	 public void createproduct(AddProductModel model,LinkedHashMap<Integer,FileMeta> filesMap,HttpServletRequest request);
	 
	 public void updateproduct(AddProductModel model,LinkedHashMap<Integer,FileMeta> filesMap,HttpServletRequest request);

}
