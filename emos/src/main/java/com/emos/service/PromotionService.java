package com.emos.service;

import java.util.List;

import com.emos.dto.Tpromotion;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface PromotionService {
	
	PagingData loadPromotionList(DataTableParamter dtp);
	
	void updatePromtion(Tpromotion tPromotion);
    
	Tpromotion getPromtionById(int id);
	
	List<Tpromotion> selectPromotion(Integer bindType);
	
	void createPromtion(Tpromotion tPromotion);
}
