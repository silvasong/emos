package com.emos.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.emos.commons.ConvertTools;
import com.emos.commons.EmosException;
import com.emos.dto.TadminRole;
import com.emos.dto.Tservice;
import com.emos.model.DataTableParamter;
import com.emos.model.PagingData;
import com.emos.service.AdminRoleService;
import com.emos.service.ServiceService;

/**
 * 服务管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "service")
public class ServiceController extends BaseController {
	
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private AdminRoleService adminRoleService;
	
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	/**
	 * 返回消息
	 */
	private String info ="";
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView service(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<TadminRole> roles = adminRoleService.getAllAdminRoles();
		mav.addObject("roles", roles);
		mav.setViewName("service/service");
		return mav;
	}
	
	@RequestMapping(value = "/serviceList", method = RequestMethod.GET)
	@ResponseBody
	public String serviceList(HttpServletRequest request, DataTableParamter dtp) {
		//addStoreCondition(request, dtp);
		PagingData pagingData = serviceService.loadList(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		return JSON.toJSONString(pagingData);
	}
	/**
	 * 创建服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/addService", method = RequestMethod.POST)
	@ResponseBody
	public String addService(HttpServletRequest request,Tservice service){
		Map<String, Object> res = getHashMap();
		try {
			serviceService.save(service);
			info = getMessage(request,"operate.success");
		} catch (EmosException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/editService", method = RequestMethod.POST)
	@ResponseBody
	public String editService(HttpServletRequest request,Tservice service){
		Map<String, Object> res = getHashMap();
		try {
			serviceService.update(service);
			info = getMessage(request,"operate.success");
		} catch (EmosException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 删除服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/delete/{serviceIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteService(HttpServletRequest request,@PathVariable String serviceIds){
		Map<String, Object> res = getHashMap();
		Map<String, Object> params = getHashMap();
		String deleteHql = "delete from Tservice where serviceId in(:ids)";
		try {
			String[] idStr = serviceIds.split(",");
			Integer[] ids = ConvertTools.stringArr2IntArr(idStr);
			params.put("ids", ids);
			serviceService.delete(deleteHql, params);
			info = getMessage(request,"operate.success");
		} catch (EmosException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 通过角色ID查询角色信息
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRole/{roleId}", method = RequestMethod.GET)
	@ResponseBody
	public String getRole(HttpServletRequest request,@PathVariable Integer roleId){
		Map<String, Object> res = getHashMap();
		try {
			TadminRole role = adminRoleService.getAdminRoleById(roleId);
			res.put("msg", role.getRoleName());
			info = getMessage(request,"operate.success");
		} catch (EmosException e) {
			e.printStackTrace();
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
}
