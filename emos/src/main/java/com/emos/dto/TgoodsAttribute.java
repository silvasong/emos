package com.emos.dto;

public class TgoodsAttribute implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -296980105994911590L;
	private Integer id;
	private Integer productId;
	private String attributeValue;
	private String attributePrice;
	private Object attributeValueLocale;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getAttributePrice() {
		return attributePrice;
	}
	public void setAttributePrice(String attributePrice) {
		this.attributePrice = attributePrice;
	}
	public Object getAttributeValueLocale() {
		return attributeValueLocale;
	}
	public void setAttributeValueLocale(Object attributeValueLocale) {
		this.attributeValueLocale = attributeValueLocale;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	

}
