package com.emos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.emos.dao.GoodsAttributeDao;
import com.emos.dto.TgoodsAttribute;
import com.emos.dto.TproductAttribute;
import com.emos.service.GoodsAttributeService;

public class GoodsAttributeServiceImpl implements GoodsAttributeService{
	
	@Autowired
	private GoodsAttributeDao goodsAttributeDao;

	public void createProductAttribute(TgoodsAttribute goodsAttribute) {
		goodsAttributeDao.create(goodsAttribute);
	}

	public TproductAttribute getTproductAttribute(Integer productid,Integer AttributeId) {
		
		return goodsAttributeDao.getAttribute(productid, AttributeId);
	}

	public List<TproductAttribute> getTproductAttribute(Integer productid) {
		
		return goodsAttributeDao.getAttributebyproductid(productid);
	}	

}
