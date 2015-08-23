package com.emos.dto;



public class Torder implements java.io.Serializable {

	private static final long serialVersionUID = -8509466641730355616L;
	private Integer orderId;
	private int orderStatus;
	private float orderTotal;
	private float orderDiscount;
	private long createTime;
	private String creater;
	private String orderPromotion;
	private String comment;
	private int peopleNum;
	private Integer storeId;
	public Torder() {
	}

	
	public Torder(Integer orderId, int orderStatus) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
	}


	public Torder(int orderStatus, float orderTotal, float orderDiscount,
			long createTime, String creater) {
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
		this.orderDiscount = orderDiscount;
		this.createTime = createTime;
		this.creater = creater;
	}

	public Torder(int orderStatus, float orderTotal, float orderDiscount,
			long createTime, String creater, String orderPromotion,
			String comment) {
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
		this.orderDiscount = orderDiscount;
		this.createTime = createTime;
		this.creater = creater;
		this.orderPromotion = orderPromotion;
		this.comment = comment;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getOrderTotal() {
		return this.orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public float getOrderDiscount() {
		return this.orderDiscount;
	}

	public void setOrderDiscount(float orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOrderPromotion() {
		return this.orderPromotion;
	}

	public void setOrderPromotion(String orderPromotion) {
		this.orderPromotion = orderPromotion;
	}

	public String getComment() {
		return this.comment;
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

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
