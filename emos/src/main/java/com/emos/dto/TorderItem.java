package com.emos.dto;



public class TorderItem implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 522720481688308894L;
	private Integer id;
	private String orderId;
	private int productId;
	private int quantity;
	private float unitPrice;
	private float discount;
	private float currPrice;
	private String attributes;
	private boolean isGift;
	private String productPromotion;
    
	public TorderItem() {
	}

	public TorderItem(String orderId, int productId, int quantity,
			float unitPrice, float discount, float currPrice,
			String attributes, boolean isGift) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.currPrice = currPrice;
		this.attributes = attributes;
		this.isGift = isGift;
	}

	public TorderItem(String orderId, int productId, int quantity,
			float unitPrice, float discount, float currPrice,
			String attributes, boolean isGift, String productPromotio) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.currPrice = currPrice;
		this.attributes = attributes;
		this.isGift = isGift;
		this.productPromotion = productPromotio;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getDiscount() {
		return this.discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getCurrPrice() {
		return this.currPrice;
	}

	public void setCurrPrice(float currPrice) {
		this.currPrice = currPrice;
	}

	public String getAttributes() {
		return this.attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public boolean isIsGift() {
		return this.isGift;
	}

	public void setIsGift(boolean isGift) {
		this.isGift = isGift;
	}

	public String getProductPromotion() {
		return this.productPromotion;
	}

	public void setProductPromotion(String productPromotio) {
		this.productPromotion = productPromotio;
	}
	

}
