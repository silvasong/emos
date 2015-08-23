package com.emos.model;

import java.util.List;

public class MenuProductModel {
	
	private Integer MenuId;
	
	private List<ProductModel> foods;

	

	public Integer getMenuId() {
		return MenuId;
	}

	public void setMenuId(Integer menuId) {
		MenuId = menuId;
	}

	public List<ProductModel> getFoods() {
		return foods;
	}

	public void setFoods(List<ProductModel> foods) {
		this.foods = foods;
	}
	
	

}
