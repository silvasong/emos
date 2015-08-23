package com.emos.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.emos.commons.BaiduPushTool;
import com.emos.commons.ConvertTools;
import com.emos.commons.EmosException;
import com.emos.commons.SecurityTools;
import com.emos.commons.SystemConfig;
import com.emos.commons.SystemConstants;
import com.emos.commons.BaiduPushTool.Notification;
import com.emos.dto.ImageModel;
import com.emos.dto.Tlanguage;
import com.emos.dto.Tservice;
import com.emos.dto.Tstore;
import com.emos.service.AdminUserService;
import com.emos.service.GoodsService;
import com.emos.service.LanguageService;
import com.emos.service.ServiceService;
import com.emos.service.StoreService;

@Controller
@RequestMapping(value="storeSetting")
public class StoreContrller extends BaseController {
	
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private LanguageService languageService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private GoodsService goodsService;
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	/**
	 * 返回消息
	 */
	private String info ="";
	/**
	 * 操作内容
	 */
	private String handleContent = "";
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getStoreSetting(HttpServletRequest request){
		Integer storeId = getSessionStoreId(request);
		ModelAndView mav = new ModelAndView();
		try {
			putInfo(request, storeId, mav);
			mav.addObject("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("store/storesetting");
		return mav;
	}

	private void putInfo(HttpServletRequest request, Integer storeId,
			ModelAndView mav) throws IOException {
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		String hql = "select new Tstore(storeId,storeName) from Tstore";
		List<Tstore> stores = storeService.select(hql, null);
		Tstore store = getInfo(request, storeId);
		mav.addObject("store", store);
		mav.addObject("langs", languages);
		mav.addObject("role", getSessionUser(request).getAdminRole().getRoleId());
		mav.addObject("stores",stores);
		mav.addObject("store_code",ConvertTools.bw(storeId, 8, "S"));
		mav.addObject("langIds", store.getStoreLangId());
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public ModelAndView getInfo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		Map<String, Object> params = getHashMap();
		String hql = "select new Tstore(serviceId,serviceDate) from Tstore where storeId=:storeId";
		params.put("storeId", getSessionStoreId(request));
		Tstore store = storeService.selectOne(hql, params);
		Tservice service = serviceService.get(store.getServiceId());
		mav.addObject("endDate", ConvertTools.longToDateString(store.getServiceDate()));
		mav.addObject("service", service);
		mav.addObject("startDate", ConvertTools.longToDateString(getSessionUser(request).getCreatedTime()));
		mav.addObject("admin", getSessionUser(request).getEmail());
		mav.setViewName("service/serviceinfo");
		return mav;
	}
	
	@RequestMapping(value="search/{storeId}",method=RequestMethod.GET)
	public ModelAndView getStoreSetting(HttpServletRequest request,@PathVariable Integer storeId){
		if(storeId == null||storeId==-1){
			storeId = getSessionStoreId(request);
		}
		ModelAndView mav = new ModelAndView();
		try {
				putInfo(request, storeId, mav);
				mav.addObject("status", 0);
				mav.setViewName("store/storesetting");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	private Tstore getInfo(HttpServletRequest request, Integer storeId) throws IOException {
		Tstore store = storeService.get(storeId);
		String logoPath = getImagePath(store.getStoreLogo(), storeId, request, "logo");
		String backgroundPath = getImagePath(store.getStoreBackground(), storeId, request, "background");
		store.setStoreLogo(null);
		store.setStoreBackground(null);
		store.setLogoPath(logoPath);
		store.setBackgroundPath(backgroundPath);
		return store;
	}
	/**
	 * 上传或者跟新logo
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadLogo",method=RequestMethod.POST)
	@ResponseBody
	public String uploadLogo(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file,Integer storeId){
		//更新参数
		//Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		//获取店铺ID
		if(storeId==null||storeId==-1){
			storeId = getSessionUser(request).getStoreId();
		}
		//Integer storeId = getSessionUser(request).getStoreId();
		//修改HQL
		//String updateLogoHql = "update Tstore set storeLogo=:storeLogo where storeId=:storeId";
		//上传文件后缀名
		String logoSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		logoSuffix = ".jpg";
		//上传pathName
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String logoPath = SystemConstants.STORE_SET_PATH+"logo"+"_"+storeId+logoSuffix;
		File  logoFile=null;
		try {
			ImageModel model = new ImageModel();
			model.setType(ImageModel.LOGO);
			model.setStoreId(storeId);
			model.setImage(file.getBytes());
			storeService.updateImage(model);
			/*params.put("storeLogo", file.getBytes());
			params.put("storeId", storeId);
			storeService.updateImage(updateLogoHql, params);*/
			logoFile = new File(realPath+logoPath);
			if(logoFile.exists()){
				logoFile.delete();
			}
			logoFile.createNewFile();
			FileUtils.copyInputStreamToFile(file.getInputStream(), logoFile);
			res.put("path", logoPath);
			handleContent = "上传logo成功;";
			info = getMessage(request,"operate.success");
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			info = e.getMessage();
			handleContent = "上传logo失败;";
			
		}		
		
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/test",method=RequestMethod.POST)
	@ResponseBody
	public String test(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file){
		Map<String, Object> res = getHashMap();
		Integer storeId = getSessionStoreId(request);//2.6.8.9.10.11
		try {
			goodsService.saveTest(storeId, 2, file,"特色菜");
			goodsService.saveTest(storeId, 6, file,"凉菜");
			goodsService.saveTest(storeId, 8, file,"川菜");
			goodsService.saveTest(storeId, 9, file,"粤菜");
			goodsService.saveTest(storeId, 10, file,"东北菜");
			goodsService.saveTest(storeId, 11, file,"湘菜");
			goodsService.saveTest(storeId, 12, file,"台湾菜");
		} catch (Exception e) {
			status = false;
			info= e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 
	 * @param image
	 * @param logoPath
	 * @param storeId
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	private String getImagePath(byte[] image,Integer storeId,HttpServletRequest request,String name) throws IOException{
		InputStream is = null;
		String logoPath = SystemConstants.STORE_SET_PATH+name+"_"+storeId+"."+"jpg";
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if(image!=null){
			File	logo = new File(realPath+logoPath);
			if(!logo.exists()){
				logo.createNewFile();
				is = new ByteArrayInputStream(image);
				FileUtils.copyInputStreamToFile(is, logo);
			}
			logoPath = SystemConstants.STORE_UP_PATH+name+"_"+storeId+"."+"jpg";
		}else{
			logoPath = SystemConstants.STORE_UP_PATH+"store"+"_"+name+"."+"jpg";
		}
		return logoPath.substring(logoPath.indexOf("/")+1);
	}
	
	/**
	 * 上传或者跟新background
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadBackground",method=RequestMethod.POST)
	@ResponseBody
	public String uploadBackground(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file,Integer storeId){
		//更新参数
		//Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		System.out.println("------------------------------------->"+storeId);
		System.out.println(storeId);
		//获取店铺ID
		if(storeId==null||storeId==-1){
			storeId = getSessionUser(request).getStoreId();
		}
		//修改HQL
		//String updateBackHql = "update Tstore set storeBackground=:storeBackground where storeId=:storeId";
		//上传文件后缀名
		String logoSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		logoSuffix = ".jpg";
		//上传pathName
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String backPath = SystemConstants.STORE_SET_PATH+"background"+"_"+storeId+logoSuffix;
		//realPath += realPath+SystemConstants.STORE_SET_PATH+"background_"+storeId+"."+logoSuffix;
		File  logoFile=null;
		try {
			ImageModel model = new ImageModel();
			model.setType(ImageModel.BACK);
			model.setStoreId(storeId);
			model.setImage(file.getBytes());
			storeService.updateImage(model);
			//params.put("storeBackground", file.getBytes());
			//params.put("storeId", storeId);
			//storeService.update(updateBackHql, params);
			logoFile = new File(realPath+backPath);
			if(logoFile.exists()){
				logoFile.delete();
			}
			logoFile.createNewFile();
			FileUtils.copyInputStreamToFile(file.getInputStream(), logoFile);
			res.put("path", backPath);
			handleContent = "上传background成功;";
			info = getMessage(request,"operate.success");
		} catch (Exception e) {
			status = false;
			info = e.getMessage();
			handleContent = "上传background失败;";
			
		}		
		
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 修改key
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/changeKey",method=RequestMethod.POST)
	@ResponseBody
	public String changePublicKey(HttpServletRequest request,String value,@RequestParam(value="name") Integer storeId){
		//更新参数
		Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		if(storeId==null||storeId==-1){
			storeId = getSessionUser(request).getStoreId();
		}
		//修改HQL
		String updatePublicKeyHql = "update Tstore set publicKey=:publicKey where storeId=:storeId";
		try {
			 params.put("storeId", storeId);
			params.put("publicKey", value);
			storeService.update(updatePublicKeyHql, params);
			String k = "";
			for(String key:SystemConfig.STORE_TAKEN_MAP.keySet()){
				Integer val = SystemConfig.STORE_TAKEN_MAP.get(key);
				if(val==storeId){
					k = key;
					break;
				}
			}
			if(!k.isEmpty()){
				SystemConfig.STORE_TAKEN_MAP.remove(k);
			}
			Notification notification = new Notification(10002,value);
			BaiduPushTool.pushMsgToTag(notification, getSessionUser(request).getStoreId()+"", BaiduPushTool.IOS_TYPE);
			SystemConfig.STORE_TAKEN_MAP.put(SecurityTools.MD5(ConvertTools.bw(storeId, 8, "S")+value), storeId);
			handleContent = "修改公钥成功;";
			info = getMessage(request,"operate.success");
			res.put("msg", value);
		} catch (Exception be) {
			be.printStackTrace();
			//info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
			handleContent = "修改公钥失败;"+be.getMessage();
			
		}		
		
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 修改客户语言配置
	 * @return
	 */
	@RequestMapping(value="/changeLangSet/{storeLangIds}",method=RequestMethod.GET)
	@ResponseBody
	public String changeLangSet(HttpServletRequest request,@PathVariable String storeLangIds,Integer storeId){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		if(storeId==null||storeId==-1){
			storeId = getSessionStoreId(request);
		}
		params.put("storeId", storeId);
		if(storeLangIds.equals(",")){
			storeLangIds="";
		}else{
			storeLangIds = storeLangIds.substring(storeLangIds.indexOf(",")+1);
		}
		params.put("storeLangId", storeLangIds);
		String updateLangHql = "update Tstore set storeLangId=:storeLangId where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			handleContent = "客户端语言配置成功;";
			info = getMessage(request,"operate.success");
		} catch (EmosException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
			handleContent = "客户端语言配置失败;"+be.getMessage();
			
		}		
		
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改店铺名称
	 * @return
	 */
	@RequestMapping(value="changeStoreName",method=RequestMethod.POST)
	@ResponseBody
	public String changeStoreName(HttpServletRequest request,String value,@RequestParam(value="name") Integer storeId){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		if(storeId==null||storeId==-1){
			storeId = getSessionStoreId(request);
		}
		params.put("storeId", storeId);
		params.put("storeName", value);
		String updateLangHql = "update Tstore set storeName=:storeName where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
			res.put("msg", value);
			handleContent = "修改店铺名称成功;";
		} catch (EmosException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
			handleContent = "修改店铺名称失败;"+be.getMessage();
			
		}	
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改店铺货币符号
	 * @return
	 */
	@RequestMapping(value="/changeStoreCurrency",method=RequestMethod.POST)
	@ResponseBody
	public String changeStoreCurrency(HttpServletRequest request,String value,@RequestParam(value="name") Integer storeId){
		Map<String, Object> res = getHashMap();
		//更新参数storeCurrency
		Map<String, Object> params = getHashMap();
		if(storeId==null||storeId==-1){
			storeId = getSessionStoreId(request);
		}
		params.put("storeId", storeId);
		params.put("storeCurrency", value);
		String updateLangHql = "update Tstore set storeCurrency=:storeCurrency where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
			res.put("msg",value);
			handleContent = "修改店铺货币符号成功;";
		} catch (EmosException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
			handleContent = "修改店铺货币符号失败;"+be.getMessage();
			
		}	
		
		res.put("status",status);
		res.put("info",info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改店铺打印类型
	 * @return
	 */
	@RequestMapping(value="/changeStorePrint",method=RequestMethod.POST)
	@ResponseBody
	public String changeStorePrint(HttpServletRequest request,Integer printType,Integer storeId){
		Map<String, Object> res = getHashMap();
		//更新参数storeCurrency
		Map<String, Object> params = getHashMap();
		if(storeId==null||storeId==-1){
			storeId = getSessionStoreId(request);
		}
		params.put("storeId", storeId);
		params.put("printType", printType);
		String updateLangHql = "update Tstore set printType=:printType where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
			res.put("msg",printType);
			handleContent = "修改店铺打印类型成功;";
		} catch (EmosException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
			handleContent = "修改店铺打印类型失败;"+be.getMessage();
			
		}	
		
		res.put("status",status);
		res.put("info",info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改订阅服务
	 * @return
	 */
	@RequestMapping(value="/changeService",method=RequestMethod.POST)
	@ResponseBody
	public String changeService(HttpServletRequest request,Integer serviceId){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		Integer storeId = getSessionStoreId(request);
		params.put("storeId", storeId);
		//查询以前的服务日期
		String queryStoreHql = "select newTstore(serviceId,serviceDate) from Tstore where storeId=:storeId";
		//更新新的订阅服务
		String updateServiceHql = "update Tstore set serviceId=:serviceId,serviceDate=:serviceDate where storeId=:storeId";
		try {
			Tstore store = storeService.selectOne(queryStoreHql, params);
			Long serviceDate = store.getServiceDate();
			if(serviceDate==null){
				serviceDate= System.currentTimeMillis();
			}
			Tservice service = serviceService.get(serviceId);
			Integer validDays = service.getValidDays();
			
			
			params.put("serviceId", serviceId);
			params.put("serviceDate", ConvertTools.longTimeAIntDay(serviceDate, validDays));
			storeService.update(updateServiceHql, params);
			
			info = getMessage(request,"operate.success");
		} catch (EmosException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		return JSON.toJSONString(res);
	}
	
}
