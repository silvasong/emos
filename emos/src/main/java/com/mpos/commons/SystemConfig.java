package com.mpos.commons;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mpos.dto.TadminNodes;
import com.mpos.model.AddAttributevaleModel;
import com.mpos.model.CallWaiterInfo;

public class SystemConfig {
	
		
	public static String TOKEN;  
	
	public static Map<String, Integer> STORE_NODES_URL_MAP = new LinkedHashMap<String, Integer>();
	
	public static Map<String, Integer> STORE_TAKEN_MAP = new LinkedHashMap<String, Integer>();
	
	public static Map<String,Long> Admin_Nodes_Url_Map=new LinkedHashMap<String,Long>();
					
	public static Map<TadminNodes,List<TadminNodes>> Admin_Nodes_Menu_Map=new LinkedHashMap<TadminNodes,List<TadminNodes>>();
	
	public static Map<String,List<TadminNodes>> Admin_Nodes_Group_Map=new LinkedHashMap<String,List<TadminNodes>>();
	
	public static Map<String,String> Admin_Setting_Map=new Hashtable<String,String>();
	
	public static Map<String, AddAttributevaleModel> product_AttributeModel_Map=new LinkedHashMap<String,AddAttributevaleModel>();
	
	public static Map<String,CallWaiterInfo> Call_Waiter_Map = new Hashtable<String, CallWaiterInfo>();
}
