package com.emos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.emos.dao.base.BaseDao;
import com.emos.dto.TproductAttribute;
import com.emos.dto.TproductAttributeId;
@Repository
public class ProductAttributeDao extends BaseDao<TproductAttribute>{

	@SuppressWarnings("unchecked")
	public TproductAttribute getByAttributeid(TproductAttributeId productAttributeId){
		String hql="from TproductAttribute where attribute_id=? and product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, productAttributeId.getCategoryAttribute().getAttributeId());
		query.setParameter(1, productAttributeId.getProduct().getId());
		List<TproductAttribute> list=query.list();
		TproductAttribute productAttribute=new TproductAttribute();
		if(list.size()==0){
			return null;
		}
		else {
			productAttribute=list.get(0);
			 return productAttribute;
		}
	}
	@SuppressWarnings("unchecked")
	public TproductAttribute getAttributebyid(Integer producid,Integer attributeid){
		String hql="from TproductAttribute where attribute_id=? and product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, attributeid);
		query.setParameter(1, producid);
		List<TproductAttribute> list=query.list();
		TproductAttribute productAttribute=new TproductAttribute();
		if(list.size()==0){
			return null;
		}
		else {
			productAttribute=list.get(0);
			 return productAttribute;
		}
	}
}
