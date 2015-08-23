/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.emos.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emos.commons.ConvertTools;
import com.emos.commons.EmosException;
import com.emos.service.AdminUserService;
import com.emos.service.OrderService;



@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {
	@Autowired
	private OrderService orderService;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private AdminUserService adminUserService;		
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request){
		
		ModelAndView mav=new ModelAndView();
        if(getSessionUser(request).getAdminRole().getRoleId()!=1){
        	mav.setViewName("redirect:/home/storeHome");
		}else{
		String sql = "select count(*),role.role_name from emos.emos_admin as admin left join emos.emos_admin_role as role on admin.role_id=role.role_id group by role.role_name";
		List<Object[]> qres = orderService.getList(sql, null);
		mav.addObject("userRole", qres);
		
		String storeSql= "select count(*),store.status from emos.emos_store as store group by store.status";
		List<Object[]> store =  orderService.getListBySql(storeSql,null);
		loadData(store);
		mav.addObject("store", store);
		
		String serviceSql = "select count(*),service.status from emos.emos_service as service group by service.status";
		List<Object[]> service = orderService.getListBySql(serviceSql,null);
		loadData(service);
		mav.addObject("service", service);
		
		Map<String, Object> params = getHashMap();
		params.put("startTime", ConvertTools.getFirstDay());
		params.put("endTime", ConvertTools.getLastDay());
		mav.setViewName("home/home");
		}
		return mav;
	}
	private void loadData(List<Object[]> data) {
		if(data.size()==1){
			Object[] o = data.get(0);
			boolean status = (Boolean) o[1] ;
			Object[] n = new Object[2];
			if(status ){
				n[0] = 0;
				n[1] = false;
			}else{
				n[0] = 0;
				n[1] = true;
			}
			data.add(n);
		}
		if(data.size()==0){
			Object[] one = new Object[2];
			one[0] = 0;
			one[1]=true;
			Object[] two = new Object[2];
			two[0] = 0;
			two[1]=false;
			data.add(one);
			data.add(two);
		}
	}
	@RequestMapping(value="storeHome",method=RequestMethod.GET)
	public ModelAndView storeHome(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();	
		
		if(getSessionUser(request).getAdminRole().getRoleId()==4||getSessionUser(request).getAdminRole().getRoleId()==1){
			mav.setViewName("redirect:/home");
		}else{
			Map<String, Object> params = getHashMap();
			params.put("startTime", ConvertTools.getFirstDay());
			params.put("endTime", ConvertTools.getLastDay());
			params.put("storeId", getSessionStoreId(request));
			String query_order = "select count(orderId),sum(orderTotal) from Torder where storeId=:storeId and createTime between :startTime and :endTime";
			Object o = orderService.get(query_order, params);
			Object[] res = (Object[])o;
			mav.addObject("orderCount", res[0]);
			if(res[1]==null||res[1]==""){
				res[1]=0;
			}
			mav.addObject("orderMount", res[1]);
			params.clear();
			params.put("storeId", getSessionStoreId(request));
			String query_product="select count(product.product_id),menu.title from emos_product as product right join emos_menu as menu on menu.menu_id = product.menu_id where product.store_id = :storeId group by menu.title";
			List<Object[]> qres = orderService.getList(query_product, params);
			mav.addObject("productList", qres);
			mav.setViewName("home/store_home");
		}
		return mav;
	}

	@RequestMapping(value="/getAmount",method=RequestMethod.GET)
	@ResponseBody
	public String systemUserAmount(HttpServletRequest request,@RequestParam(value="id",required=true) int id){
		JSONObject resp = new JSONObject();
		int amount=0;
		try{
			if(id==0){
		     	amount=adminUserService.getAdminUserAmount();
			}else if(id==1){
				
			}else{
				
			}
			resp.put("amount", amount);
			resp.put("status", true);
		}catch(EmosException b){
			resp.put("status", false);
			resp.put("info",b.getMessage());
		}
		
		return JSON.toJSONString(resp);
	
	}
	
	@RequestMapping(value="/getCpuStatus",method=RequestMethod.GET)
	@ResponseBody
	public String getSystemCpuStatus(HttpServletRequest request) throws IOException{
		loadLibiray(request);
		JSONObject respJson = new JSONObject();
		Sigar sigar = new Sigar();
		try{
			CpuPerc perc = sigar.getCpuPerc();
			long cpu=Math.round(perc.getCombined()*100);
			
			respJson.put("cpu", cpu);
		}catch(EmosException b){
			
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/getMemStatus",method=RequestMethod.GET)
	@ResponseBody
	public String getSystemMemStatus(HttpServletRequest request) throws IOException{
		loadLibiray(request);
		JSONObject respJson = new JSONObject();
		Sigar sigar = new Sigar();
		try{
			Mem mem;
			mem = sigar.getMem();
			long m=Math.round(mem.getUsedPercent());
			
			respJson.put("m", m);
		}catch(EmosException b){
			
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JSON.toJSONString(respJson);
	}
	
	
    public static void loadLibiray(HttpServletRequest request){
    	String path=request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"lib"+File.separator;
    	if(System.getProperty("os.name").startsWith("Windows")){
    		if(System.getProperty("os.arch").endsWith("64")){
    			System.load(path+"sigar-amd64-winnt.dll");
    		}else{
    			System.load(path+"sigar-x86-winnt.dll");
    		}
        }
    }	
}
