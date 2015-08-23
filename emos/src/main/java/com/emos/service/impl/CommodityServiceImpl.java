package com.emos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emos.dao.CommodityDao;
import com.emos.dto.Tcommodity;
import com.emos.service.CommodityService;
@Service
public class CommodityServiceImpl implements CommodityService {

	@Autowired
	CommodityDao commodityDao;
	
	public Tcommodity getTproductByid(Integer id) {
		// TODO Auto-generated method stub
		return commodityDao.get(id);
	}
	
	public List<Tcommodity> getTpoductByStoreId(Integer id){
		
		return commodityDao.findBy("storeId", id);
		
	}

}
