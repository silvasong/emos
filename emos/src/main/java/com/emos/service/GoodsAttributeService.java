package com.emos.service;

import java.util.List;

import com.emos.dto.TgoodsAttribute;
import com.emos.dto.TproductAttribute;

public interface GoodsAttributeService {
	public void createProductAttribute(TgoodsAttribute goodsAttribute);
	
	public TproductAttribute getTproductAttribute(Integer productid,Integer AttributeId);
	
	public List<TproductAttribute> getTproductAttribute(Integer productid);
		
	
}
