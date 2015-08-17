package com.mpos.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.dao.GoodsAttributeDao;
import com.mpos.dao.GoodsDao;
import com.mpos.dao.GoodsImageDao;
import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductImage;
import com.mpos.model.AddProductModel;
import com.mpos.model.DataTableParamter;
import com.mpos.model.FileMeta;
import com.mpos.model.PagingData;
import com.mpos.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private GoodsAttributeDao goodsAttributeDao;
	@Autowired
	private LocalizedFieldDao localizedFieldDao;
	@Autowired
	private GoodsImageDao goodsImageDao;
	

	public PagingData loadGoodsList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		if (searchJsonStr != null && !searchJsonStr.isEmpty()) {
			List<Criterion> criterionsList = new ArrayList<Criterion>();
			criterionsList.add(Restrictions.eq("status", true));
			JSONObject jsonObj = (JSONObject) JSON.parse(searchJsonStr);
			Set<String> keys = jsonObj.keySet();
			for (String key : keys) {
				String val = jsonObj.getString(key);
				if (val != null && !val.isEmpty()) {
					if (key == "isPut") {
						criterionsList.add(Restrictions.eq(key,
								jsonObj.getBoolean(key)));

					} else if (key == "tcategory.categoryId") {
						criterionsList.add(Restrictions.eq(key,
								jsonObj.getInteger(key)));
					} else if (key == "tmenu.menuId") {
						criterionsList.add(Restrictions.eq(key,
								jsonObj.getInteger(key)));
					} else if (key == "status") {
						criterionsList.add(Restrictions.eq(key,
								jsonObj.getBoolean(key)));
					} else if (key == "storeId") {
						criterionsList.add(Restrictions.eq(key,
								jsonObj.getInteger(key)));
					} else {
						/*
						 * if (jsonObj.get(key).toString().isEmpty()) {
						 * criterionsList.add(Restrictions.like(key,
						 * jsonObj.get(key))); }else {
						 */
						criterionsList.add(Restrictions.like(key,
								(String) jsonObj.get(key), MatchMode.ANYWHERE));
						/* } */
					}
				}
			}
			Criterion[] criterions = new Criterion[criterionsList.size()];
			int i = 0;
			for (Criterion criterion : criterionsList) {
				criterions[i] = criterion;
				i++;
			}
			return goodsDao.findPage("id", false, criterions,
					rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return goodsDao.findPage("id", false, Restrictions.eq("status", true),
				rdtp.iDisplayStart, rdtp.iDisplayLength);

	}

	public void deletegoodsByids(Integer[] ids, Integer storeId) {
		
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				Tproduct goods = getTproductByid(id);
				goods.setStatus(false);
				goodsDao.update(goods);
				Boolean isexist = true;
				

			}
		}
	}

	public Tproduct getTproductByid(Integer id) {

		return goodsDao.get(id);
	}

	public void activegoodsByids(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				Tproduct goods = getTproductByid(id);
				goods.setStatus(true);
				goodsDao.update(goods);
			}
		}

	}

	public void createGoods(Tproduct product) {
		goodsDao.create(product);

	}

	public Tproduct findbyProductName(String productName) {

		return goodsDao.findprodctbyname(productName);
	}

	public void updateGoods(Tproduct product) {

		goodsDao.update(product);
	}

	public List<Tproduct> loadAll() {
		// TODO Auto-generated method stub
		return goodsDao.LoadAll();
	}

	public void createproduct(AddProductModel model,
			LinkedHashMap<Integer, FileMeta> filesMap,
			HttpServletRequest request) {

		Tproduct product = new Tproduct();
		product.setProductName(model.getProductName());
		product.setShortDescr(model.getShortDescr());
		product.setFullDescr(model.getFullDescr());
		product.setPrice(model.getPrice());
		product.setOldPrice(model.getOldPrice());
		product.setUnitName(model.getUnitName());
		product.setRecommend(model.isRecommend());
		product.setStoreId(model.getStoreId());
		product.setIsPut(model.getIsPut());
		if (model.getSku() != null) {
			product.setSku(model.getSku());
		}
		if (model.getSort() != null) {
			product.setSort(model.getSort());
		}
		product.setStatus(true);
		product.setTmenu(model.getMenu());
		if (model.getAttributeGroup().getCategoryId() != 0) {
			product.setTcategory(model.getAttributeGroup());
		}
		if (model.getSpecid() != 0) {
			product.setSpecid(model.getSpecid());
		}
		goodsDao.create(product);
		List<TgoodsAttribute> productAttributesList = model.getAttributes();
		for (TgoodsAttribute goodsAttribute : productAttributesList) {
			goodsAttribute.setProductId(product.getId());
			goodsAttributeDao.create(goodsAttribute);
		}
		// Set the product fields language model
		List<TlocalizedField> productNameLocaleList = model
				.getProductName_locale();
		List<TlocalizedField> shortDescrLocaleList = model
				.getShortDescr_locale();
		List<TlocalizedField> fullDescrLocaleList = model.getFullDescr_locale();
		List<TlocalizedField> unitNameLocaleList = model.getUnitName_locale();
		// Save product language information
		for (TlocalizedField localizedField : productNameLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("productName");
				localizedFieldDao.save(localizedField);
			}
		}
		for (TlocalizedField localizedField : shortDescrLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("shortDescr");
				localizedFieldDao.save(localizedField);
			}
		}
		for (TlocalizedField localizedField : fullDescrLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("fullDescr");
				localizedFieldDao.save(localizedField);
			}
		}
		for (TlocalizedField localizedField : unitNameLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("unitName");
				localizedFieldDao.save(localizedField);
			}
		}
		// set images
		Set<Integer> keys = filesMap.keySet();
		int i = 0;
		for (Integer key : keys) {
			FileMeta fileMeta = filesMap.get(key);
			TproductImage productImage = new TproductImage();
			productImage.setProduct(product);
			productImage.setImage(fileMeta.getBytes());
			productImage.setImageSuffix(fileMeta.getSuffix());
			String filename = product.getStoreId() + "_" + product.getId()
					+ "_" + i + "." + fileMeta.getSuffix();
			String filePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator
					+ "upload"
					+ File.separator
					+ "product"
					+ File.separator + filename;
			// String
			// fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
			String fileUrl = "/upload/product/" + filename;
			File filemkdir = new File(request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator + "upload" + File.separator + "product");

			if (!filemkdir.isDirectory()) {
				filemkdir.mkdirs();
			}
			try {
				FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream(
						filePath));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			productImage.setImageUrl(fileUrl);
			i++;
			goodsImageDao.create(productImage);
		}
		filesMap.clear();
		// add productReleaseService
		

	}

	public void updateproduct(AddProductModel model,
			LinkedHashMap<Integer, FileMeta> filesMap,
			HttpServletRequest request) {
		Tproduct product = new Tproduct();
		product.setProductName(model.getProductName());
		product.setShortDescr(model.getShortDescr());
		product.setFullDescr(model.getFullDescr());
		product.setPrice(model.getPrice());
		product.setOldPrice(model.getOldPrice());
		product.setUnitName(model.getUnitName());
		product.setRecommend(model.isRecommend());
		product.setSku(model.getSku());
		product.setSort(model.getSort());
		product.setStatus(true);
		product.setTmenu(model.getMenu());
		product.setIsPut(model.getIsPut());
		product.setId(model.getProductId());
		product.setStoreId(model.getStoreId());
		if (model.getAttributeGroup().getCategoryId() != 0) {
			product.setTcategory(model.getAttributeGroup());
		}
		if (model.getSpecid() != 0) {
			product.setSpecid(model.getSpecid());
		}
		goodsDao.update(product);
		List<TgoodsAttribute> productAttributesList = model.getAttributes();
		goodsAttributeDao.DeleteAttributebyproductid(model.getProductId());
		for (TgoodsAttribute goodsAttribute : productAttributesList) {
			goodsAttribute.setProductId(product.getId());
			goodsAttributeDao.create(goodsAttribute);
		}

		// local language
		List<TlocalizedField> productNameLocaleList = model
				.getProductName_locale();
		List<TlocalizedField> shortDescrLocaleList = model
				.getShortDescr_locale();
		List<TlocalizedField> fullDescrLocaleList = model.getFullDescr_locale();
		List<TlocalizedField> unitNameLocaleList = model.getUnitName_locale();
		for (TlocalizedField localizedField : productNameLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("productName");
				localizedFieldDao.saveOrUpdate(localizedField);
			}
		}
		for (TlocalizedField localizedField : shortDescrLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("shortDescr");
				localizedFieldDao.saveOrUpdate(localizedField);
			}
		}
		for (TlocalizedField localizedField : fullDescrLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("fullDescr");
				localizedFieldDao.saveOrUpdate(localizedField);
			}
		}
		for (TlocalizedField localizedField : unitNameLocaleList) {
			if (localizedField.getLocaleValue() != null
					&& !localizedField.getLocaleValue().isEmpty()) {
				localizedField.setEntityId(product.getId());
				localizedField.setTableName("Tproduct");
				localizedField.setTableField("unitName");
				localizedFieldDao.saveOrUpdate(localizedField);
			}
		}
		// set images
		Set<Integer> keys = filesMap.keySet();
		int i = 0;
		for (Integer key : keys) {
			FileMeta fileMeta = filesMap.get(key);
			TproductImage productImage = new TproductImage();
			if (fileMeta.getFileId() != null) {
				productImage.setId(fileMeta.getFileId());
			}
			File files = new File(fileMeta.getUrl());
			if (files.exists()) {
				files.delete();
			}
			productImage.setProduct(product);
			productImage.setImage(fileMeta.getBytes());
			productImage.setImageSuffix(fileMeta.getSuffix());
			String filename = product.getStoreId() + "_" + product.getId()
					+ "_" + i + "." + fileMeta.getSuffix();
			String filePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator
					+ "upload"
					+ File.separator
					+ "product"
					+ File.separator + filename;
			// String
			// fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
			String fileUrl = "/upload/product/" + filename;
			File file = new File(request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator + "upload" + File.separator + "product");

			if (!file.isDirectory()) {
				file.mkdirs();
			}
			try {
				FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream(
						filePath));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			productImage.setImageUrl(fileUrl);
			i++;
			// goodsImageDao.create(productImage);
			goodsImageDao.saveOrUpdate(productImage);
		}
		filesMap.clear();
		
	}


	/*private void publish(Integer storeId, String productIds, boolean ok) {
		Integer verId = productReleaseDao.getMaxId("id", storeId);
		TproductRelease productrelease;
		if (verId != null && verId != 0) {
			productrelease = productReleaseDao.get(verId);
			if (productrelease != null && !productrelease.isIsPublic()) {
				String ids = productrelease.getProducts();
				// String products[]=ids.split(",");
				if (ok) {
					String[] proIds = productIds.split(",");
					for (int j = 0; j < proIds.length; j++) {
						String id = proIds[j];
						if (!ids.contains(id)) {
							ids += "," + id;
						}
					}
				} else {
					String[] idstr = ids.split(",");
					List<String> istr = new LinkedList<String>();
					List<String> reids = new LinkedList<String>();
					for (String string : idstr) {
						istr.add(string);
					}
					for (String id : istr) {
						if (productIds.contains(id)) {
							reids.add(id);
						}
					}
					for (String id : reids) {
						istr.remove(id);
					}
					ids = istr.toString().substring(1, istr.toString().length()-1);
				}
				productrelease.setProducts(ids);
				productReleaseDao.update(productrelease);
			} else {
				TproductRelease newproductrelease = new TproductRelease();
				newproductrelease.setProducts(productIds);
				newproductrelease.setIsPublic(false);
				newproductrelease.setStoreId(storeId);
				productReleaseDao.create(newproductrelease);
			}
		} else {
			TproductRelease productrelease1 = new TproductRelease();
			productrelease1.setProducts(productIds);
			productrelease1.setIsPublic(false);
			productrelease1.setStoreId(storeId);
			productReleaseDao.create(productrelease1);
		}
	}*/

	public void putGoods(String productIds, Integer storeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer[] ids = ConvertTools.stringArr2IntArr(productIds.split(","));
		params.put("storeId", storeId);
		params.put("ids", ids);
		params.put("isPut", true);
		String hql = "update Tproduct set isPut=:isPut where storeId=:storeId and id in(:ids)";
		goodsDao.update(hql, params);
		
	}

	public void outGoods(String productIds, Integer storeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer[] ids = ConvertTools.stringArr2IntArr(productIds.split(","));
		params.put("storeId", storeId);
		params.put("ids", ids);
		params.put("isPut", false);
		String hql = "update Tproduct set isPut=:isPut where storeId=:storeId and id in(:ids)";
		goodsDao.update(hql, params);
		
	}
	
	public static void main(String[] args) {
		List<String> ids = new ArrayList<String>();
		List<String> reids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		ids.add("3");
		for (String id : ids) {
			if("1,5,7".contains(id)){
				reids.add(id);
			}
		}
		for (String string : reids) {
			ids.remove(string);
		}
		System.out.println(ids.toString().substring(1, ids.toString().length()-1));
	}

	public void saveTest(Integer storeId, Integer menuId,MultipartFile file,String name) {
		List<Tproduct> products = new ArrayList<Tproduct>();
		String ids = "";
			for (int i = 0; i < 40; i++) {
				Tproduct pro = new Tproduct();
				pro.setIsPut(true);
				pro.setOldPrice((float)(15+i));
				pro.setPrice(13+i);
				pro.setProductName(name+i);
				pro.setShortDescr(name+"������"+i);
				pro.setSku(10);
				pro.setSort(i);
				pro.setUnitName("��");
				pro.setTmenu(new Tmenu(menuId));
				pro.setStatus(true);
				pro.setStoreId(storeId);
				pro.setRecommend(false);
				products.add(pro);
			}
			
			for (Tproduct product : products) {
				goodsDao.create(product);
			}
			List<TproductImage> images = new ArrayList<TproductImage>();
			for (Tproduct product : products) {
				ids +=product.getId()+",";
				TproductImage image = new TproductImage();
				String fileName = storeId + "_" + product.getId()+ "_" + 0 + ".jpg";
				image.setProduct(product);
				try {
					image.setImage(file.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				image.setImageSuffix("jpg");
				image.setImageUrl("/upload/product/"+fileName);
				images.add(image);
			}
			for (TproductImage image : images) {
				goodsImageDao.create(image);
			}
			
	}

}
