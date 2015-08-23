package com.emos.dao;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.emos.dao.base.BaseDao;
import com.emos.dto.ImageModel;
import com.emos.dto.Tstore;
@Repository
public class StoreDao extends BaseDao<Tstore>{
	
	public void updateImage(ImageModel model){
		Integer type = model.getType();
		String hql = "";
		if(ImageModel.BACK==type){
			hql = "update Tstore set storeBackground=:image where storeId=:storeId";
		}else if(ImageModel.LOGO==type){
			hql = "update Tstore set storeLogo=:image where storeId=:storeId";
		}
		Query query = currentSession().createQuery(hql);
		query.setBinary("image", model.getImage());
		query.setInteger("storeId", model.getStoreId());
		query.executeUpdate();
	}

}
