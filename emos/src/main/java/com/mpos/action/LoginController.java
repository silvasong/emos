/**   
* @Title: LoginController.java 
* @Package com.uswop.action 
*
* @Description: TODO
* 
* @date Sep 10, 2014 3:06:32 PM
* @version V1.0   
*/ 
package com.mpos.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mpos.commons.EMailTool;

import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;

import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.service.AdminUserService;



@Controller
public class LoginController extends BaseController {	
	@Autowired
	private AdminUserService adminUserService;		
	
  
	/** 
	 * <p>Open the login page</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */ 
    private String log_content;
    
    @RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=new TadminUser();		
		mav.addObject("user", tUser);
		mav.setViewName("login");
		return mav;
	}
		
	
	/** 
	 * <p>User Login</p>
	 * @Title: login 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,TadminUser user){
		
		
		TadminUser tUser=(TadminUser)adminUserService.getAdminUserById(user.getAdminId());
		ModelAndView mav=new ModelAndView();
		Long time = (Long) request.getSession().getAttribute(SystemConstants.LOGIN_STATUS);
		if(time != null && System.currentTimeMillis()-time<60000*Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.LOGIN_ERROR_LOCK))){
			    mav.addObject(ERROR_MSG_KEY,getMessage(request,"login.failed.locked",Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.LOGIN_ERROR_LOCK))));
				if(tUser != null){
				  mav.addObject("user", tUser);
				}else{
					mav.addObject("user", new TadminUser());
				}
				
				log_content=SystemConstants.LOG_FAILURE+":userid locked.";
		}else if(tUser==null){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}else if(!tUser.getStatus() && !"admin".equals(tUser.getAdminId())){
			mav.addObject(ERROR_MSG_KEY, "账号未激活");
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}else if(!tUser.getAdminRole().getStatus() && !"admin".equals(tUser.getAdminId())){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}
		else if(!SecurityTools.MD5(user.getPassword()).equalsIgnoreCase(tUser.getPassword())){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", tUser);
			saveLoginErrorTims(request);
	
			log_content=SystemConstants.LOG_FAILURE+":password error.";
		}else{
			String toUrl=(String)request.getSession().getAttribute(LOGIN_TO_URL);
			request.getSession().removeAttribute(LOGIN_TO_URL);
			request.getSession().removeAttribute(SystemConstants.LOGIN_ERROR);
			request.getSession().removeAttribute(SystemConstants.LOGIN_STATUS);
			if(StringUtils.isEmpty(toUrl)&&tUser.getAdminRole().getRoleId()==1){
				setSessionUser(request, tUser);
				toUrl="/home";
			}else if (StringUtils.isEmpty(toUrl)&&tUser.getAdminRole().getRoleId()!=1) {
				Long right = adminUserService.getRightByEmail(tUser.getEmail());
				setSessionUser(request, tUser,right);
				toUrl="home/storeHome";	
			}			
			mav.setViewName("redirect:"+toUrl);
			log_content=SystemConstants.LOG_SUCCESS+":login success.";
		}
		
		return mav;
	}
	
	@RequestMapping(value="/common/ResetPassword",method=RequestMethod.POST)
	public ModelAndView resetPassword(HttpServletRequest request ,String email){
		ModelAndView mav = new ModelAndView();
		
		String url = request.getRequestURL().toString().replaceFirst( request.getServletPath(), "");
		TadminUser adminUser = adminUserService.getTadminUsersByEmail(email.toLowerCase());
		if(adminUser == null){
			 adminUser = new TadminUser();
			 mav.addObject(ERROR_MSG_KEY,"邮箱不存在");
		}else{
			
			String random = UUID.randomUUID().toString().trim().replace("-","").substring(0,6);
			String code = SecurityTools.MD5(email+random);
			adminUser.setCode(code);
			adminUser.setUpdatedBy(adminUser.getAdminId());
			adminUser.setUpdatedTime(System.currentTimeMillis());
			adminUser.setStatus(false);
			adminUserService.updateAdminUserPassword(adminUser);
			Map<String,String> map = new HashMap<String, String>();
			map.put("email", email);
			map.put("code", code);
			map.put("url", url);
			TemaiMessage message = TemaiMessage.getRest(map);
			EMailTool.send(message);
			mav.addObject("msg", getMessage(request,"reset.pwd.success"));
		
		}
		mav.addObject("user",adminUser);
		mav.setViewName("login");
		log_content="success:resert password success.";
		return mav;
	}
	public static void main(String[] args) {
		String email = "34444";
		String random="xxxx";
		String html = "<a href='http://192.168.10.103:8080/mpos/common/reset?email="+email+"&code="+random+"'>点击链接重置密码,此链接有效时间2分钟</a>";
		System.out.println(html);
	}
	@RequestMapping(value="/common/reset",method=RequestMethod.GET)
	public ModelAndView reset(HttpServletRequest request ,String code){
		ModelAndView mav = new ModelAndView();
		String msg  = "";
		TadminUser adminUser = new TadminUser();
		
		try {
			if((code!=null&&!code.isEmpty())){
				adminUser = adminUserService.getByCode(code);
				if(adminUser!=null){
					long now = System.currentTimeMillis();
					long system = Long.valueOf(SystemConfig.Admin_Setting_Map.get(SystemConstants.RESET_PWD_VALID_TIME));
					if((now-adminUser.getUpdatedTime())<=system){
						if(adminUser.getCode().equals(code)){
							mav.addObject("user", adminUser);
							mav.setViewName("reset");
							return mav;
						}else{
							msg="校验失败";
						}
					}else{
						msg="链接失效";
					}
				}else{
					msg="邮箱不存在";
				}
			}else{
				msg="非法链接";
			}
			mav.addObject("user", adminUser);
			mav.addObject(ERROR_MSG_KEY,msg);
			mav.setViewName("login");
		} catch (MposException e) {
			
		}
		return mav;
	}
	
	@RequestMapping(value="/common/change",method=RequestMethod.POST)
	public ModelAndView change(HttpServletRequest request ,TadminUser user){
		ModelAndView mav = new ModelAndView();
		try {
			
			TadminUser adminUser = adminUserService.getTadminUsersByEmail(user.getEmail());
			if(adminUser!=null&&adminUser.getCode().equals(user.getCode())){
				adminUser.setPassword(SecurityTools.MD5(user.getPassword()));
				adminUser.setUpdatedTime(System.currentTimeMillis());
				adminUser.setStatus(true);
				adminUserService.updateAdminUser(adminUser);
				mav.addObject("msg","重置成功，请重新登录");
			}else{
				adminUser = new TadminUser();
				mav.addObject(ERROR_MSG_KEY,"邮箱不存在或者非法操作");
			}
			mav.addObject("user", adminUser);
			mav.setViewName("login");
		} catch (MposException e) {
		}
		return mav;
	}
	
	/** 
	 * <p></p>
	 * @Title: logout 
	 * @param session
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="/logout")
	public String logout(HttpSession session,HttpServletRequest request){
		
		if((TadminUser)session.getAttribute(SystemConstants.LOGINED)!=null){
		
		log_content="success:login out.";
		
		session.removeAttribute(SystemConstants.LOGINED);
		session.invalidate();
		}
		return "forward:login";
	}
}
