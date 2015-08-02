package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tlanguage;
@Repository
public class LanguageDao extends BaseDao<Tlanguage>{
	public Tlanguage getlanguagebylocal(String local){
		String hql="from Tlanguage where local=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, local);
		@SuppressWarnings("unchecked")
		List<Tlanguage> language= query.list();
		if(language!=null){
			return language.get(0);
		}
		else {
			 return null;
		}
	}
}
