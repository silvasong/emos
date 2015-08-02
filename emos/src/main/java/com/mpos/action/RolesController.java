/**   
 * @Title: RolesController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminRoleRights;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.AdminRoleRightsService;
import com.mpos.service.AdminRoleService;

/**
 * @ClassName: RolesController
 * @Description: TODO
 * @author Phills Li
 * @date Sep 2, 2014 7:25:22 PM
 * 
 */
@Controller
@RequestMapping(value="roles")
public class RolesController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(RolesController.class);

	@Resource
	private AdminRoleService adminRoleService;
	@Resource
	private AdminRoleRightsService adminRoleRightsService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView roles(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.addObject("rightsList", SystemConfig.Admin_Nodes_Group_Map);		
		mav.setViewName("roles/roles");
		return mav;
	}
		
	@RequestMapping(value="rolesList",method=RequestMethod.GET)
	@ResponseBody
	public String rolesList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=adminRoleService.loadAdminRolesList(dtp);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
	}
	
	/**
	 * <p>Description: </p>
	 * @Title: addRole 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@ResponseBody
	public String addRole(HttpServletRequest request,TadminRole adminRole){		
		JSONObject respJson = new JSONObject();
		try{
			adminRole.setStatus(true);
			adminRoleService.createAdminRole(adminRole);
			respJson.put("info", getMessage(request,"operate.success"));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="editRole",method=RequestMethod.POST)
	@ResponseBody
	public String updateRole(HttpServletRequest request,TadminRole adminRole){		

		JSONObject respJson = new JSONObject();
		try{
			if(adminRole.getRoleId()==1){
				respJson.put("status", false);
				respJson.put("info", getMessage(request, "error.edit.role"));
			}
			else{
				adminRoleService.updateAdminRole(adminRole);
				respJson.put("status", true);
				respJson.put("info", getMessage(request,"operate.success"));
			}
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value="editRoleRights",method=RequestMethod.POST)
	@ResponseBody
	public String updateRoleRights(HttpServletRequest request,TadminRoleRights adminRoleRights){
		JSONObject respJson = new JSONObject();
		try{
			long bitVal=0;
			long[] rights=adminRoleRights.getRights();
			for (int i = 0; i < rights.length; i++) {
				bitVal=bitVal|rights[i];
			}
			adminRoleRights.setRoleRights(bitVal);
			adminRoleRightsService.saveAdminRoleRights(adminRoleRights);
			setSessionRights(request,adminRoleRights.getRoleRights());
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	

	@RequestMapping(value="roles/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteRoles(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{			
			if(Arrays.binarySearch(idArr, new Integer(1))>=0){
				respJson.put("status", false);
				respJson.put("info", getMessage(request, "error.delete.role"));
			}
			else{
				adminRoleService.deleteAdminRolesByIds(idArr);
				respJson.put("status", true);
				respJson.put("info", getMessage(request,"operate.success"));
			}
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
}
