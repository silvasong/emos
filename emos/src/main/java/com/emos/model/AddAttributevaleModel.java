package com.emos.model;

public class AddAttributevaleModel {
	
	private String title;
	private String content;
//	private String price;
	private Integer attributeId;
	private Integer type;
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/*public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	*/

}
