package com.emos.service;



import java.util.List;

import com.emos.dto.TproductImage;

public interface GoodsImageService {
	
	void CreateImages(TproductImage image);
	
	void updeteImages(TproductImage image);
	
	List<TproductImage> getByProductid(Integer id);
	
	void deleteImagebyid(Integer id);

}
