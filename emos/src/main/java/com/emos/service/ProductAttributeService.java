package com.emos.service;

import com.emos.dto.TproductAttribute;
import com.emos.dto.TproductAttributeId;
import com.emos.model.AddAttributevaleModel;

public interface ProductAttributeService {
	public void cachedSystemSettingData(AddAttributevaleModel model);
	public void cachedSystemclearData(AddAttributevaleModel model);
	void createProductAttribute(TproductAttribute productAttribute);
	TproductAttribute getAttributes(TproductAttributeId productAttributeId);
	void updattProductAttribute(TproductAttribute productAttribute);
	void createOrProductAttribute(TproductAttribute productAttribute);
	 public TproductAttribute getAttributeByproductidAndattributeid(Integer productid,Integer attributeid);
}
