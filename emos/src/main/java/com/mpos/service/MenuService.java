package com.mpos.service;

import java.util.List;

import com.mpos.dto.Tlanguage;
import com.mpos.dto.Tmenu;
import com.mpos.model.DataTableParamter;
import com.mpos.model.MenuModel;
import com.mpos.model.PagingData;

public interface MenuService {

	void saveMenu(Tmenu menu);
	void deleteMenu(Tmenu menu);
	void updateMenu(Tmenu menu);
	Tmenu getMenu(Integer menuId);
	PagingData loadMenuList(DataTableParamter rdtp);
	PagingData loadMenuList(DataTableParamter rdtp,String local);
	void deleteMenuByIds(Integer[] idArr);
	public List<Tmenu> getAllMenu(Integer storeId);
	Tmenu getParentMenu(Tmenu menu);
	List<MenuModel> getNoChildrenMenus(Integer storeId);
	List<MenuModel> loadMenu(String local);
	List<MenuModel> getNoChildrenMenus(Tlanguage language,Integer storeId);
}
