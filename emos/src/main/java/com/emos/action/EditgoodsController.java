package com.emos.action;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emos.commons.ConvertTools;
import com.emos.commons.EmosException;
import com.emos.dto.Tcategory;
import com.emos.dto.Tlanguage;
import com.emos.dto.TlocalizedField;
import com.emos.dto.Tproduct;
import com.emos.dto.TproductAttribute;
import com.emos.dto.TproductImage;
import com.emos.model.AddProductModel;
import com.emos.model.FileMeta;
import com.emos.model.MenuModel;
import com.emos.service.CategoryAttributeService;
import com.emos.service.CategoryService;
import com.emos.service.GoodsAttributeService;
import com.emos.service.GoodsImageService;
import com.emos.service.GoodsService;
import com.emos.service.LanguageService;
import com.emos.service.LocalizedFieldService;
import com.emos.service.MenuService;
import com.emos.service.ProductAttributeService;


@Controller
@Scope("session")
public class EditgoodsController extends BaseController{
@Autowired
private GoodsService goodsService;


@Autowired
private ProductAttributeService productAttributeService;

@Autowired
private CategoryAttributeService CategoryAttributeService;

@Autowired
private MenuService menuService;

@Autowired
private CategoryService categoryService;

@Autowired
private GoodsImageService goodsImageService;

@Autowired
private LanguageService languageService;

@Autowired
private GoodsAttributeService goodsAttributeService;



@Autowired
private LocalizedFieldService localizedFieldService;


private String handleContent = "";


private LinkedHashMap<Integer,FileMeta> filesMap = new LinkedHashMap<Integer,FileMeta>();
private int imgIndex=0;	

@SuppressWarnings("unused")
@RequestMapping(value="/editgoods/{ids}",method=RequestMethod.GET)
public ModelAndView eidtgoods(@PathVariable String ids,HttpServletRequest request,Integer storeId){
	imgIndex=0;
	filesMap.clear();
	Integer id=Integer.parseInt(ids);
	JSONObject respJson = new JSONObject();
	ModelAndView mav=new ModelAndView();
	try {
		Tproduct product=goodsService.getTproductByid(id);
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		 List<TlocalizedField> productName_locale=new ArrayList<TlocalizedField>();
		 List<TlocalizedField> shortDescr_locale=new ArrayList<TlocalizedField>();
		 List<TlocalizedField> fullDescr_locale=new ArrayList<TlocalizedField>();
		 List<TlocalizedField> unitName_locale=new ArrayList<TlocalizedField>();
		 if(storeId==null||storeId==-1){
			 storeId = getSessionUser(request).getStoreId();
		 }
		 List<Tcategory> ordercategoryList=categoryService.getallCategory(1,language,storeId);
			List<Tcategory> speccategoryList=categoryService.getallCategory(0,language,storeId);
			Map<Integer, String> ordercategoryMap = new HashMap<Integer, String>();  
			Map<Integer, String> speccategoryMap = new HashMap<Integer, String>();
			for (Tcategory tcategory : ordercategoryList) {
				ordercategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
			}
			for(Tcategory tcategory : speccategoryList){
				speccategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
			}
			mav.addObject("ordercategory", ordercategoryMap);
			mav.addObject("speccategory", speccategoryMap);
		
			
		Map<Integer, String> menusMap = new HashMap<Integer, String>(); 
		List<MenuModel> menus=menuService.getNoChildrenMenus(language,getSessionUser(request).getStoreId());
	
		for (MenuModel menu : menus) {
			menusMap.put(menu.getId(), menu.getTitle());
		}
		mav.addObject("menu", menusMap);
		AddProductModel Productmodel=new AddProductModel();
		Productmodel.setFullDescr(product.getFullDescr());
		Productmodel.setMenu(product.getTmenu());
		Productmodel.setOldPrice(product.getOldPrice());
		Productmodel.setAttributeGroup(product.getTcategory());
		Productmodel.setPrice(product.getPrice());
		Productmodel.setShortDescr(product.getShortDescr());
		Productmodel.setSort(product.getSort());
		Productmodel.setSku(product.getSku());
		Productmodel.setProductName(product.getProductName());
		Productmodel.setUnitName(product.getUnitName());
		Productmodel.setMenu(product.getTmenu());
		Productmodel.setRecommend(product.isRecommend());
		Productmodel.setProductId(product.getId());
		Productmodel.setSpecid(product.getSpecid());
		Productmodel.setStoreId(product.getStoreId());
		Productmodel.setIsPut(product.getIsPut());
		productName_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "productName");
		shortDescr_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "shortDescr");
		fullDescr_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "fullDescr");
		unitName_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "unitName");
		Productmodel.setProductName_locale(productName_locale);
		Productmodel.setFullDescr_locale(fullDescr_locale);
		Productmodel.setShortDescr_locale(shortDescr_locale);
		Productmodel.setUnitName_locale(unitName_locale);
		Object string=request.getSession().getAttribute("editerrorMsg");
		if (string!=null) {
			mav.addObject("Msg", string);
			
		}
		request.getSession().setAttribute("editerrorMsg", null);
	
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		mav.addObject("lanList", languages);
	
		mav.addObject("product", Productmodel);
		mav.setViewName("goods/editgoods");
		
	} catch (EmosException be ) {	
		
	}
	
	return mav;
}


	@RequestMapping(value="editgoods/getAttributes/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String getattributes(HttpServletRequest request,@PathVariable String ids){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);
		
		JSONObject respJson = new JSONObject();
		try {
			//TproductAttribute productAttribute=goodsAttributeService.getTproductAttribute(idArr[0], idArr[1]);
			List<TproductAttribute> list=goodsAttributeService.getTproductAttribute(idArr[0]);
			respJson.put("status", true);
			respJson.put("productAttribute", list);
		}
		catch(EmosException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	
	
	@RequestMapping(value="/editgoods/editgoods",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView editGoods(HttpServletRequest request,@ModelAttribute("product") AddProductModel model,Integer storeId){
		ModelAndView mav=new ModelAndView();
		try{
			addStore(model, request, storeId);
			goodsService.updateproduct(model, filesMap, request);
			handleContent = "修改商品:"+model.getProductName()+"成功;";
			request.getSession().setAttribute("addsussess","operate.success");
			mav.setViewName("redirect:/goods");
			//return new ModelAndView("redirect:/goods");
		} catch (EmosException  e) {
			e.printStackTrace();
		/*	mav.addObject("errorMsg", e.getMessage());*/
			handleContent = "修改商品:"+model.getProductName()+"失败;";
			
			request.getSession().setAttribute("editerrorMsg", getMessage(request,e.getErrorID(),e.getMessage()));
			mav.setViewName("redirect:/editgoods/"+model.getProductId());
		}		
		
		return mav;
		}
	@RequestMapping(value="/editgoods/getImages/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String getproductImages(@PathVariable String ids,HttpServletRequest request){
		List<TproductImage> list= new ArrayList<TproductImage>();
		Integer id=Integer.parseInt(ids);
		
		JSONObject respJson = new JSONObject();
		JSONArray jsonArray=new JSONArray();
		list=goodsImageService.getByProductid(id);
		
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			FileMeta filemeta=new FileMeta();
			String string=list.get(i).getImageUrl();
			String fileName=string.split("/")[3];
			File file=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+fileName);
			String s[]=fileName.split("\\.");
			filemeta.setBytes(list.get(i).getImage());
			filemeta.setFileId(list.get(i).getId());
			filemeta.setSuffix(s[s.length-1]);
			filemeta.setUrl(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+fileName);
			jsonObj.put("id",imgIndex);
	    	jsonObj.put("fileName",fileName);
	    	jsonObj.put("fileSize", list.get(i).getImage().length/1024+"kb");
	    	jsonObj.put("url", list.get(i).getImageUrl());
	    	
	    	File filemkdir=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product");
        	
    		if (!filemkdir.isDirectory()) {
    			filemkdir.mkdirs();
    		}
	    	if(!file.exists()){
	    		 try {
						FileCopyUtils.copy(filemeta.getBytes(), file);
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
	    	}
	    	jsonArray.add(jsonObj);
	    	filesMap.put(imgIndex,filemeta);
	    
	    	imgIndex++;
		}
			respJson.put("files", jsonArray);
	        respJson.put("status", true);
	        respJson.put("info", "OK");
	        return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="/editgoods/deleteImage/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String deleteImage(@PathVariable Integer ids,HttpServletRequest request){
		
		FileMeta fileMeta = filesMap.get(ids);
		filesMap.remove(ids);
		JSONObject respJson = new JSONObject();
		if (fileMeta.getFileId()!=null) {
			//Delete old image
			File file=new File(fileMeta.getUrl());
			file.delete();
			goodsImageService.deleteImagebyid(fileMeta.getFileId());
			 respJson.put("status", true);
		     respJson.put("info", "OK");
		}else{
		String filename=fileMeta.getFileName();            	        	                
    	//Delete file        	                      
    	String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"temp"+File.separator+filename;
        File file=new File(filePath);
        file.deleteOnExit();  
        respJson.put("status", true);
        respJson.put("info", "OK");
		}                            
       
        return JSON.toJSONString(respJson);
		
	}
	@RequestMapping(value="editgoods/uploadImages",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImages(MultipartHttpServletRequest request){		
		
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
		JSONObject respJson = new JSONObject();
		JSONArray jsonArray=new JSONArray();  
		
        //Get each file		
        while(itr.hasNext()){
        	JSONObject jsonObj = new JSONObject();
            //Get next MultipartFile        	
            mpf = request.getFile(itr.next());                      
            //String fileName=this.getSessionUser(request).getAdminId()+mpf.getOriginalFilename();
            String originalName=mpf.getOriginalFilename();
            String[] strArr=originalName.split("[.]");
            String fileName=this.getSessionUser(request).getAdminId()+"_"+imgIndex+"."+strArr[strArr.length-1];
            String fileSize=mpf.getSize()/1024+" Kb";            
            FileMeta filemeta=new FileMeta();
        	filemeta.setFileName(fileName);
        	filemeta.setFileSize(mpf.getSize()/1024+" Kb");
        	filemeta.setFileType(mpf.getContentType());
        	filemeta.setSuffix(strArr[strArr.length-1]);
        	jsonObj.put("id",imgIndex);
        	jsonObj.put("fileName",mpf.getOriginalFilename());
        	jsonObj.put("fileSize",fileSize); 	
        		
        	//Save file to cache     	
            try {
            	filemeta.setBytes(mpf.getBytes());
            	String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"temp"+File.separator+fileName;
                //String fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
            	String fileUrl=request.getContextPath()+"/upload/temp/"+fileName;
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(filePath));
                filemeta.setUrl(fileUrl);                
                //filemeta.setThumbnailUrl(request.getContextPath()+"/static/upload/"+fileName); 
                jsonObj.put("url",fileUrl);
                jsonArray.add(jsonObj);
                filesMap.put(imgIndex,filemeta);
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } 
           imgIndex++;
        }
        respJson.put("files", jsonArray);
        respJson.put("status", true);
        respJson.put("info", "OK");
        return JSON.toJSONString(respJson);		
	}
}
