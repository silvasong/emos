package com.emos.service;

import com.emos.dto.TorderItem;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;

public interface OrderItemService {
	
	PagingData loadPagingDataByOrderId(DataTableParamter dtp);
	
	void createOrderItem(TorderItem orderItem);
	
	String get(Integer orderItemId);
	
}
