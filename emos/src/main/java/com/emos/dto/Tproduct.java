package com.emos.dto;

import java.util.Set;




public class Tproduct implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String productName;
	private String shortDescr;
	private String fullDescr;
	private String unitName;
	private Float oldPrice;
	private float price;
	private Integer sku;
	private boolean recommend;
	
	private boolean status;
	private Tmenu tmenu;
	private Boolean isPut=false;
	private Tcategory tcategory;
	private Integer specid;
	private Integer sort;
	@SuppressWarnings("unused")
	private String  menuname;
	@SuppressWarnings("unused")
	private String  categoryname;
	private Integer storeId;
	
	private Set<TproductImage> images;
	
	private Set<TgoodsAttribute> attributes;
	
	private Set<Tpromotion> promotions;

	public Tproduct() {
	}



	public Tproduct(Tmenu tmenu,  Tcategory tcategory, String productName,
			String shortDescr, String fullDescr, String unitName,
			Float oldPrice, float price, Integer sku, boolean recommend,
			Integer sort,boolean status) {
		this.tmenu = tmenu;
		this.tcategory = tcategory;
		this.productName = productName;
		this.shortDescr = shortDescr;
		this.fullDescr = fullDescr;
		this.unitName = unitName;
		this.oldPrice = oldPrice;
		this.price = price;
		this.sku = sku;
		this.recommend = recommend;
		this.sort = sort;
		this.status=status;
	}
   

	
	
	
	
	


	public Integer getSpecid() {
		return specid;
	}



	public void setSpecid(Integer specid) {
		this.specid = specid;
	}



	public String getMenuname() {
		return tmenu.getTitle();
	}



	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}



	public Integer getStoreId() {
		return storeId;
	}



	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}



	public String getCategoryname() {
		if(tcategory!=null){
		return tcategory.getName();
		}else {
			return "All";
		}
	}



	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}



	public boolean isStatus() {
		return this.status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public Tmenu getTmenu() {
		return this.tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	public Tcategory getTcategory() {
		return this.tcategory;
	}

	public void setTcategory(Tcategory tcategory) {
		this.tcategory = tcategory;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortDescr() {
		return this.shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}

	public String getFullDescr() {
		return this.fullDescr;
	}

	public void setFullDescr(String fullDescr) {
		this.fullDescr = fullDescr;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Float getOldPrice() {
		return this.oldPrice;
	}

	public void setOldPrice(Float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getSku() {
		return this.sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public boolean isRecommend() {
		return this.recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Set<TproductImage> getImages() {
		return images;
	}

	public void setImages(Set<TproductImage> images) {
		this.images = images;
	}

	public Set<TgoodsAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<TgoodsAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<Tpromotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Tpromotion> promotions) {
		this.promotions = promotions;
	}
	public Boolean getIsPut() {
		return isPut;
	}
	public void setIsPut(Boolean isPut) {
		this.isPut = isPut;
	}

}
