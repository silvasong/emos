package com.emos.dao;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.emos.dao.base.BaseDao;
import com.emos.dto.Tpromotion;

/**
 * Home object for domain model class TadminInfo.
 * @see com.bps.dto.TadminInfo
 * @author Hibernate Tools
 */
@Repository
public class PromotionDao extends BaseDao<Tpromotion> {
	
	@SuppressWarnings("unchecked")
	public List<Tpromotion> find(String hql, Map<String, Object> params) {
		Query query = currentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
}
