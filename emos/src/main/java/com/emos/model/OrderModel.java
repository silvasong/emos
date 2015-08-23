package com.emos.model;


public class OrderModel {

	private Integer orderId;
	
	private String orderStatus;
	
	private float orderTotal;
	
	private float orderDiscount;
	
	private String createTime;
	
	private String creater;
	
	private String orderPromotion;
	
	private String comment;
	
	private int peopleNum;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public float getOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(float orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOrderPromotion() {
		return orderPromotion;
	}

	public void setOrderPromotion(String orderPromotion) {
		this.orderPromotion = orderPromotion;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

}
