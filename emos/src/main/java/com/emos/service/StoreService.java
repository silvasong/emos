package com.emos.service;

import java.util.List;
import java.util.Map;

import com.emos.dto.ImageModel;
import com.emos.dto.Tstore;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface StoreService {
		public void save(Tstore store);
		public void delete(Tstore store);
		public void delete(Integer storeId);
		public void update(Tstore store);
		public Tstore get(Integer storeId);
		public PagingData loadList(DataTableParamter rdtp);
		public PagingData load(DataTableParamter rdtp);
		public List<Tstore> loadAll();
		public List<Tstore> loadStoreNameAndId();
		
		public void deleteByStoreId(Integer storeId,String adminId);
		
		public void updateImage(String hql, Map<String, Object> params);
		public void updateImage(ImageModel model);
		public void delete(String hql,Map<String, Object> params);
		public void update(String hql,Map<String, Object> params);
		public List<Tstore> select(String hql,Map<String, Object> params);
		public List<Object[]> getBySql(String sql,Map<String, Object> params);
		public Tstore selectOne(String hql,Map<String, Object> params);
		public Object getObject(String hql,Map<String, Object> params);
		public void cacheStoreTaken();		
}
