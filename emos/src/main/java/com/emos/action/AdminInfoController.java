/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.emos.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emos.commons.EmosException;
import com.emos.commons.SecurityTools;
import com.emos.commons.SystemConstants;
import com.emos.dto.TadminInfo;
import com.emos.dto.TadminUser;
import com.emos.model.ChangePasswordModel;
import com.emos.service.AdminInfoService;
import com.emos.service.AdminUserService;
@Controller
@RequestMapping(value="userprofile")
public class AdminInfoController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AdminInfoController.class);
	@Autowired
	private AdminInfoService adminInfoService;
	
    @Autowired
	private AdminUserService adminUserService;
    
    private String log_content;
    
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView user(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		TadminInfo adminInfo=new TadminInfo();
		try{
          TadminUser adminUser = (TadminUser) request.getSession().getAttribute(SystemConstants.LOGINED);
            
             mav.setViewName("login");
	        
			String adminId=adminUser.getAdminId();
			
			adminInfo = adminInfoService.getAdminInfoById(adminId);
			
			if(adminInfo == null){
				adminInfo = new TadminInfo();
				adminInfo.setAdminId(adminId);
				adminInfo.setGender(true);
				adminInfoService.createAdminInfo(adminInfo);
			}
			if(adminInfo.getGender()==null){
				adminInfo.setGender(true);
			}
			adminInfo.setEmail(adminUser.getEmail());
	        
        }catch(EmosException be){
        	
        }
		mav.addObject("adminInfo", adminInfo);
		mav.setViewName("userprofile/personal_info");
		return mav;
		
	}
	
	@RequestMapping(value="editprofile",method=RequestMethod.POST)
	@ResponseBody
	public String editAdminInfo(HttpServletRequest request,TadminInfo adminInfo,HttpSession session){
		JSONObject respJson = new JSONObject();
		
		try{
			TadminUser adminUser = (TadminUser) request.getSession().getAttribute(SystemConstants.LOGINED);
			TadminUser user = adminUserService.getTadminUsersByEmail(adminInfo.getEmail());
			 String adminId=adminUser.getAdminId();
			if(user != null && !adminUser.getAdminId().equals(user.getAdminId())){
				respJson.put("status", false);
				respJson.put("info", getMessage(request, "error.email.info"));
				return JSON.toJSONString(respJson);
			}
			if(!adminUser.getEmail().equals(adminInfo.getEmail())){
				session.removeAttribute(SystemConstants.LOGINED);
				user = adminUserService.getAdminUserById(adminId);
				user.setEmail(adminInfo.getEmail());
				user.setUpdatedBy(adminId);
				user.setUpdatedTime(System.currentTimeMillis());
				adminUserService.updateAdminUser(user);
				setSessionUser(request, user);
			}
		  
			adminInfo.setAdminId(adminId);
			adminInfoService.updateAdminInfo(adminInfo);
			log_content="success:edit profile.";
			respJson.put("info", getMessage(request, "operate.success"));
			respJson.put("status", true);
		}catch(EmosException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		
		return JSON.toJSONString(respJson);
	   }
	 
	@RequestMapping(value="changePassword",method=RequestMethod.POST)
	@ResponseBody
	public String changePassword(HttpServletRequest request,ChangePasswordModel cpMod){
		JSONObject respJson = new JSONObject();
		
		try{
			TadminUser adminUser = (TadminUser) request.getSession().getAttribute(SystemConstants.LOGINED);
			
			if(!SecurityTools.MD5(cpMod.getOldpassword()).equals(adminUser.getPassword())){
				log_content="error:old password is error.";
				
                respJson.put("status", true);    
				respJson.put("olderror",true);
				respJson.put("info", "当前密码不正确");
			}
			else{
				request.getSession().removeAttribute(SystemConstants.LOGINED);
				adminUser.setPassword(SecurityTools.MD5(cpMod.getNewpassword()));
				adminUserService.updateAdminUserPassword(adminUser);
				log_content = "success:change pasword.";
				respJson.put("info", getMessage(request,"operate.success"));
				respJson.put("status", true);
			}
			
		}catch(EmosException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		
		return JSON.toJSONString(respJson);
	   }
	
	@RequestMapping(value="chageAvatar",method=RequestMethod.POST)
	public String changeAvatar(HttpServletRequest request,@RequestParam(value = "avatar", required = false) MultipartFile file) throws IOException{
		
		InputStream inputStream = file.getInputStream();
		byte [] avatar=new byte[1048576];
		inputStream.read(avatar);
		try{
			TadminUser adminUser = (TadminUser) request.getSession().getAttribute(SystemConstants.LOGINED);
			TadminInfo adminInfo = new TadminInfo();
			String adminId=adminUser.getAdminId();
			adminInfo=adminInfoService.getAdminInfoById(adminId);
			adminInfo.setAvatar(avatar);
			
			adminInfoService.updateAdminInfoAvatar(adminInfo);
			log_content="success:change avatar.";
		}catch(EmosException be){
			
		}
		
		return "redirect:/userprofile"; 
	   }
      
	@RequestMapping(value="getAvatar",method=RequestMethod.GET)
	public void getAvatar(HttpServletRequest request,HttpServletResponse response){
		try{
			TadminUser adminUser = (TadminUser) request.getSession().getAttribute(SystemConstants.LOGINED);
			TadminInfo adminInfo = new TadminInfo();
			String adminId=adminUser.getAdminId();
			adminInfo=adminInfoService.getAdminInfoById(adminId);
			//设置页面不缓存
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			ByteArrayInputStream bin;
			if(adminInfo==null||adminInfo.getAvatar() == null){
				File file = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"images"+File.separator+"profile.jpg");
			    FileImageInputStream inputStream = new FileImageInputStream(file);
				byte [] avatar=new byte[1048576];
				inputStream.read(avatar);
				inputStream.close();
				bin = new ByteArrayInputStream(avatar);
			 }else{
				bin = new ByteArrayInputStream(adminInfo.getAvatar()); 
			 }
			 BufferedImage buffImage=ImageIO.read(bin);
			 ImageIO.write(buffImage, "JPEG", response.getOutputStream());
		}catch(EmosException b){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
