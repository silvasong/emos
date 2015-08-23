package com.emos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.emos.dao.base.BaseDao;
import com.emos.dto.TproductImage;
@Repository
public class GoodsImageDao extends BaseDao<TproductImage> {

	@SuppressWarnings("unchecked")
	public List<TproductImage> getByproductid(Integer id){
		String hql="from TproductImage where product_id=?";
		Query   query= currentSession().createQuery(hql);
		query.setParameter(0, id);
		List<TproductImage> productImages=query.list();
		return productImages;
	}
}
