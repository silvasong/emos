/**   
 * @Title: CachedDataProcessor.java 
 * @Package com.bps.core 
 *
 * @Description: User Points Management System
 * 
 * @date Nov 13, 2014 5:23:42 PM
 * @version V1.0   
 */ 
package com.emos.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.emos.commons.SystemConfig;
import com.emos.service.AdminNodesService;
import com.emos.service.StoreService;
import com.emos.service.SystemSettingService;


public class CachedDataProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
    public void onApplicationEvent(ContextRefreshedEvent event) {
		//root application context
    	if(event.getApplicationContext().getParent() == null){
    		AdminNodesService adminNodesService=(AdminNodesService) event.getApplicationContext().getBean("adminNodesService");
    		
    		SystemSettingService systemSettingService =(SystemSettingService)event.getApplicationContext().getBean("systemSettingService");
    		
    		StoreService storeService = (StoreService)event.getApplicationContext().getBean("storeService");
    		
    		if(adminNodesService!=null){
    			adminNodesService.cachedNodesData();    			
    		}
    		if(storeService !=null){
    			storeService.cacheStoreTaken();
    			for (String key : SystemConfig.STORE_TAKEN_MAP.keySet()) {
					System.out.println("key:"+key+":-------------------->value:"+SystemConfig.STORE_TAKEN_MAP.get(key));
				}
    		}
    		if(systemSettingService != null){
    			systemSettingService.cachedSystemSet();
    			for (String key : SystemConfig.Admin_Setting_Map.keySet()) {
					System.out.println("key:"+key+":-------------------->value:"+SystemConfig.Admin_Setting_Map.get(key));
				}
    		}
    	}
    	else{//projectName-servlet  context
    		
    	}
    }
}
