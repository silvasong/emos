package com.emos.model;

import java.util.List;

public class MenuProductModel {
	
	private Integer MenuId;
	
	private String menuName;
	
	private Integer sort;
	
	private List<ProductModel> foods;

	

	public Integer getMenuId() {
		return MenuId;
	}

	public void setMenuId(Integer menuId) {
		MenuId = menuId;
	}
    
	
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<ProductModel> getFoods() {
		return foods;
	}

	public void setFoods(List<ProductModel> foods) {
		this.foods = foods;
	}
	
	

}
