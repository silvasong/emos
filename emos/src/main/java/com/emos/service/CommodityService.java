package com.emos.service;

import java.util.List;

import com.emos.dto.Tcommodity;

public interface CommodityService {
	
	Tcommodity getTproductByid(Integer id);
	
	public List<Tcommodity> getTpoductByStoreId(Integer id);
}
