package com.emos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.emos.commons.SystemConfig;
import com.emos.dao.ProductAttributeDao;
import com.emos.dto.TproductAttribute;
import com.emos.dto.TproductAttributeId;
import com.emos.model.AddAttributevaleModel;
import com.emos.service.ProductAttributeService;

public class ProductAttributeServiceImpl implements ProductAttributeService{

	
	@Autowired
	private ProductAttributeDao productAttributeDao;

	public void cachedSystemSettingData(AddAttributevaleModel model) {
		// TODO Auto-generated method stub
		SystemConfig.product_AttributeModel_Map.put(model.getTitle(), model);
	}

	

	public void createProductAttribute(TproductAttribute productAttribute) {
		// TODO Auto-generated method stub
		productAttributeDao.create(productAttribute);
	}



	public TproductAttribute getAttributes(TproductAttributeId productAttributeId) {
		
		return productAttributeDao.getByAttributeid(productAttributeId);
	}

	public TproductAttribute getAttributeByproductidAndattributeid(Integer productid,Integer attributeid){
		return productAttributeDao.getAttributebyid(productid,attributeid);
	}

	public void updattProductAttribute(TproductAttribute productAttribute) {
		//productAttributeDao.update(productAttribute);
		productAttributeDao.saveOrUpdate(productAttribute);
		
	}



	public void cachedSystemclearData(AddAttributevaleModel model) {
		SystemConfig.product_AttributeModel_Map.remove(model.getTitle());
		
	}



	public void createOrProductAttribute(TproductAttribute productAttribute) {
		
		TproductAttribute tproductAttribute=productAttributeDao.getByAttributeid(productAttribute.getId());
		if(tproductAttribute!=null){
			String content=tproductAttribute.getContent();
			String updatecontent=content+","+productAttribute.getContent();
			tproductAttribute.setContent(updatecontent);
			productAttributeDao.update(tproductAttribute);
		}else {
			productAttributeDao.create(productAttribute);
		}
	}

}
