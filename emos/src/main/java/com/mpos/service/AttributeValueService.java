package com.mpos.service;

import java.util.List;

import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tlanguage;

public interface AttributeValueService {

	void createAttributeValue(TattributeValue attributeValue);
	void deleteAttributeValue(TattributeValue attributeValue);
	void updateAttributeValue(TattributeValue attributeValue);
	TattributeValue getAttributeValue(Integer valueId);
	List<TattributeValue> loadAttributeValuesByAttrId(Integer attrId);
	void deleteAttributeValueByIds(Integer[] idArr);
	public List<TattributeValue> getAllAttributeValue();
	List<TattributeValue> getattributeValuesbyattributeid(Integer id,Tlanguage language);
	
}
