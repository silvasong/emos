package com.emos.dto;


public class TproductAttributeId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tproduct product;
	
	private TcategoryAttribute categoryAttribute;

	public TproductAttributeId() {
	}

	public TproductAttributeId(Tproduct product, TcategoryAttribute categoryAttribute) {
		this.product = product;
		this.categoryAttribute = categoryAttribute;
	}



	public Tproduct getProduct() {
		return this.product;
	}

	public void setProduct(Tproduct product) {
		this.product = product;
	}

	public TcategoryAttribute getCategoryAttribute() {
		return this.categoryAttribute;
	}

	public void setCategoryAttribute(TcategoryAttribute categoryAttribute) {
		this.categoryAttribute = categoryAttribute;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TproductAttributeId))
			return false;
		TproductAttributeId castOther = (TproductAttributeId) other;

		return (this.getCategoryAttribute() == getCategoryAttribute())
				&& (this.getProduct() == castOther.getProduct());
	}

	
	@Override
	public int hashCode() {
		int result = 17;

		//result = 37 * result + this.getProduct();
		//result = 37 * result + this.getProductId();
		return result;
	}
	
}
