/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tservice;


import com.mpos.service.AdminInfoService;
import com.mpos.service.AdminUserService;


import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;




@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private AdminInfoService adminInfoService;
	
	
	
	private Map<String, String>  map = new HashMap<String, String>();
	
	@RequestMapping(value="header",method=RequestMethod.GET)
	public ModelAndView header(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=getSessionUser(request);
		mav.addObject("user", tUser);
		mav.setViewName("common/header");
		return mav;
	}
	
	@RequestMapping(value="left",method=RequestMethod.GET)
	public ModelAndView left(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("menus", SystemConfig.Admin_Nodes_Menu_Map);
		mav.setViewName("common/left");
		return mav;
	}
	
	
	@RequestMapping(value="footer",method=RequestMethod.GET)
	public ModelAndView footer(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/footer");
		return mav;
	}
	
	@RequestMapping(value="noRights",method=RequestMethod.GET)
	public ModelAndView noRights(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/errpage");
		return mav;
	}
	
	@RequestMapping(value="notice",method=RequestMethod.GET)
	public ModelAndView notice(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/notice");
		return mav;
	}
	
	
	@RequestMapping(value="/checkEmail",method=RequestMethod.POST)
	@ResponseBody
	public String checkTableName(String email,HttpServletRequest request){
		return JSON.toJSONString(!adminUserService.emailExist(email));
	}
	
	
	@RequestMapping(value="getServices",method=RequestMethod.POST)
	@ResponseBody
	public String getService(HttpServletRequest request){
		Map<String, Object> res = getHashMap();
		try {
//			List<Tservice> info = new ArrayList<Tservice>();
			List<Tservice> services = serviceService.load();
//			for (Tservice tservice : services) {
//				tservice.setContent(tservice.getServiceName()+": "+tservice.getServicePrice()+"元-"+tservice.getValidDays()+"天-"+tservice.getContent());
//				tservice.setRoleId(null);
//				tservice.setServiceName(tservice.getServiceName());
//				tservice.setServicePrice(tservice.getServicePrice());
//				tservice.setValidDays(tservice.getValidDays());
//				info.add(tservice);
//			}
			res.put("status", true);
			res.put("info", services);
		} catch (Exception e) {
			// TODO: handle exception
			res.put("status", false);
			res.put("info", e.getMessage());
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,TadminUser user,Integer serviceId,String mobile){
		Map<String, Object> res = getHashMap();
		try {
			boolean status = false;
			Tservice service= serviceService.get(serviceId);
			if(service==null||service.getServicePrice()==0){
				status = true;
			}
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String logoPath = SystemConstants.STORE_SET_PATH+"examples.png";
			String filePath = realPath+logoPath;
			map.put("url", request.getRequestURL().toString().replaceFirst( request.getServletPath(), ""));
			map = serviceService.register(user, service.getServiceId(), mobile,status,filePath,map.get("url"));			
			SystemConfig.STORE_TAKEN_MAP.put(SecurityTools.MD5(map.get("storeCode")+"888888"), Integer.parseInt(map.get("storeId")));
			if(!status){
				
				res.put("data", map);
			}			
			request.getSession().setAttribute("map", map);
			res.put("isPay",!status);
			res.put("service", map.get("serviceName"));
			res.put("status", true);
			res.put("info", "注册成功");
		} catch (Exception e) {
			if(e instanceof MailSendException){
				res.put("info", "邮箱不存在");
				serviceService.deleteInfo(user.getAdminId(), user.getStoreId());
			}else{
				res.put("info", "网络异常");
			}
			e.printStackTrace();
			res.put("status", false);
		}
		return JSON.toJSONString(res);
	}
	
}
