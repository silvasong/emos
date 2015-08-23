package com.emos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emos.commons.SystemConstants;
import com.emos.dao.AttributeValueDao;
import com.emos.dao.CategoryAttributeDao;
import com.emos.dao.LocalizedFieldDao;
import com.emos.dto.TattributeValue;
import com.emos.dto.TcategoryAttribute;
import com.emos.dto.Tlanguage;
import com.emos.dto.TlocalizedField;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;
import com.emos.service.CategoryAttributeService;
@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService {
	@Autowired
	CategoryAttributeDao attributeDao;
	@Autowired
	LocalizedFieldDao localizedFieldDao;
	@Autowired
	AttributeValueDao attributeValueDao;
	
	public void createCategoryAttribute(TcategoryAttribute attribute) {
		
		attributeDao.create(attribute);
		String[] values=attribute.getValues().trim().split(",");
		List<TattributeValue> attributeValues=new ArrayList<TattributeValue>();
		for (int i = 0; i < values.length; i++) {
			TattributeValue attributeValue=new TattributeValue();
			attributeValue.setValue(values[i]);
			attributeValue.setSort(i);
			attributeValue.setAttributeId(attribute.getAttributeId());
			attributeValueDao.create(attributeValue);
			attributeValues.add(attributeValue);
		}		
		
		
		//Save the localized field for the attribute title
		List<TlocalizedField> titleLocaleList=attribute.getTitle_locale();			
		for (TlocalizedField localizedField : titleLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setEntityId(attribute.getAttributeId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATE_ATTRIBUTE);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_TITLE);
				localizedFieldDao.saveOrUpdate(localizedField);
			}				
		}
		//Save the localized field for the attribute values
		List<TlocalizedField> valuesLocaleList=attribute.getValues_locale();
		for (TlocalizedField localizedField : valuesLocaleList) {
			String localeStr=localizedField.getLocaleValue();
			if(localeStr!=null&&!localeStr.isEmpty()){
				String[] valueArr=localeStr.split(",");
				for (int i = 0; i < valueArr.length; i++) {
					TattributeValue attributeValue=attributeValues.get(i);
					TlocalizedField valueLocalizedField=new TlocalizedField();
					valueLocalizedField.setLanguage(localizedField.getLanguage());
					valueLocalizedField.setEntityId(attributeValue.getValueId());
					valueLocalizedField.setLocaleValue(valueArr[i]);
					valueLocalizedField.setTableName(SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE);
					valueLocalizedField.setTableField(SystemConstants.TABLE_FIELD_VALUE);
					localizedFieldDao.create(valueLocalizedField);
				}
				
			}
		}
	}
	
	public void deleteCategoryAttribute(TcategoryAttribute attribute) {
		// TODO Auto-generated method stub
		attributeDao.delete(attribute);
	}

	
	public void updateCategoryAttribute(TcategoryAttribute attribute) {
		attributeDao.update(attribute);
		List<TattributeValue> oldValuesList=attributeValueDao.findBy("attributeId", attribute.getAttributeId(), "sort", true);		
		List<TattributeValue> attributeValues=new ArrayList<TattributeValue>();
		String[] values=attribute.getValues().trim().split(",");
		int i=0;
		for (String value : values) {
			TattributeValue attributeValue=new TattributeValue();
			if(i<oldValuesList.size()){				
				attributeValue=oldValuesList.get(i);
			}
			attributeValue.setValue(value);
			attributeValue.setSort(i);
			attributeValue.setAttributeId(attribute.getAttributeId());
			attributeValueDao.saveOrUpdate(attributeValue);
			attributeValues.add(attributeValue);
			i++;
		}
		
		//Delete the records for the attribute value			
		for (; i < oldValuesList.size(); i++) {
			attributeValueDao.delete(oldValuesList.get(i));		
		}		
		
		
		
		//Save the localized field for the attribute title 
		List<TlocalizedField> titleLocaleList=attribute.getTitle_locale();
		localizedFieldDao.deleteAllByEntityId(attribute.getAttributeId());
		for (TlocalizedField localizedField : titleLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setLocaleId(null);
				localizedField.setEntityId(attribute.getAttributeId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATE_ATTRIBUTE);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_TITLE);
				localizedFieldDao.save(localizedField);
			}				
		}
		
		for (TattributeValue value : attributeValues) {
			localizedFieldDao.deleteAllByEntityId(value.getValueId());
		}
		
				//Save the localized field for the attribute values
				List<TlocalizedField> valuesLocaleList=attribute.getValues_locale();
				for (TlocalizedField localizedField : valuesLocaleList) {
					String localeStr=localizedField.getLocaleValue();
					if(localeStr!=null&&!localeStr.isEmpty()){
						String[] valueArr=localeStr.split(",");
						for (int j = 0; j < attributeValues.size(); j++) {
							TattributeValue attributeValue=attributeValues.get(j);
							TlocalizedField valueLocalizedField=new TlocalizedField();
							valueLocalizedField.setLanguage(localizedField.getLanguage());
							valueLocalizedField.setEntityId(attributeValue.getValueId());
							valueLocalizedField.setLocaleValue(valueArr[j]);
							valueLocalizedField.setTableName(SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE);
							valueLocalizedField.setTableField(SystemConstants.TABLE_FIELD_VALUE);
							localizedFieldDao.create(valueLocalizedField);
						}
						
					}
				}
		
		//Save or delete the localized field for the attribute values
		/*List<TlocalizedField> valuesLocaleList=attribute.getValues_locale();
		for (TlocalizedField localizedField : valuesLocaleList) {
			List<TlocalizedField> oldLocaleList=localizedFieldDao.findBy(new String[]{"language","tableName","tableField"},
					new Object[]{localizedField.getLanguage(),SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE,SystemConstants.TABLE_FIELD_VALUE});
			
			String localeStr=localizedField.getLocaleValue().trim();			
			String[] valueArr=localeStr.split(",");
			int j=0;
			for (String value : valueArr) {
				TlocalizedField valueLocalizedField=new TlocalizedField();
				if(oldLocaleList!=null&&j<oldLocaleList.size()){
					valueLocalizedField=oldLocaleList.get(j);
				}
				valueLocalizedField.setLocaleValue(value);
				localizedFieldDao.saveOrUpdate(valueLocalizedField);
				j++;
			}
			for (; j < oldLocaleList.size(); j++) {
				localizedFieldDao.delete(oldLocaleList.get(j));
			}
						
		}*/
		
	}

	
	public TcategoryAttribute getCategoryAttribute(Integer attributeId) {
		
		return attributeDao.get(attributeId);
	}
	
	public PagingData loadAttributeList(String id, DataTableParamter rdtp) {
		
		Criteria criteria = attributeDao.createCriteria();
		Criterion criterion = Restrictions.eq("categoryId.categoryId", Integer.valueOf(id));
		criteria.add(criterion);
		criteria.addOrder(Order.asc("sort"));
		return attributeDao.findPage(criteria, rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	
	public void deleteAttributeByIds(Integer[] idArr) {			
		for (Integer attrId : idArr) {
			List<TattributeValue> attributeValuesList=attributeValueDao.findBy("attributeId", attrId);
			List<TlocalizedField> titleLocaleFieldList=localizedFieldDao.findBy(new String[]{"entityId","tableName","tableField"},
					new Object[]{attrId,SystemConstants.TABLE_NAME_CATE_ATTRIBUTE,SystemConstants.TABLE_FIELD_TITLE});				
			localizedFieldDao.deleteAll(titleLocaleFieldList);
			
			for (TattributeValue attributeValue : attributeValuesList) {
				Integer valueId=attributeValue.getValueId();
				attributeValueDao.delete(attributeValue);
				
				List<TlocalizedField> valueLocaleFieldList=localizedFieldDao.findBy(new String[]{"entityId","tableName","tableField"},
						new Object[]{valueId,SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE,SystemConstants.TABLE_FIELD_VALUE});				
				localizedFieldDao.deleteAll(valueLocaleFieldList);
				
			}									
		}
		attributeDao.deleteAll(idArr);
	}


	@SuppressWarnings("unchecked")
	public List<TcategoryAttribute> getCategoryAttributeByCategoryid(Integer id) {
		Criteria criteria=attributeDao.createCriteria();
		return criteria.add(Restrictions.eq("categoryId.categoryId", id))				
				.addOrder(Order.asc("sort")).list();
		
	}

	@SuppressWarnings("unchecked")
	public List<TcategoryAttribute> getCategoryAttributeByCategoryid(
			Integer id, Tlanguage language) {
		Criteria criteria=attributeDao.createCriteria();
		List<TcategoryAttribute>  list=criteria.add(Restrictions.eq("categoryId.categoryId", id))				
				.addOrder(Order.asc("sort")).list();
		if (list!=null&&list.size()>0) {
			
			for (TcategoryAttribute categoryAttribute : list) {
				Map<String, Object> ssMap=new HashMap<String, Object>();
				ssMap.put("language", language);
				ssMap.put("entityId", categoryAttribute.getAttributeId());
				ssMap.put("tableName","TcategoryAttribute");
				ssMap.put("tableField","title");
				
				List<TlocalizedField> localizedFields= localizedFieldDao.find("from TlocalizedField where language=:language and entityId=:entityId and tableName=:tableName and tableField=:tableField", ssMap);
				if (localizedFields.size()>0) {
					TlocalizedField localizedField=localizedFields.get(0);
					if(!localizedField.getLocaleValue().isEmpty()&&localizedField.getLocaleValue()!=null){
						categoryAttribute.setTitle(localizedField.getLocaleValue());
				}
				}	
			}
	}
	return list;
	}



}
