package com.mpos.dto;

// Generated Oct 29, 2014 11:20:20 AM by Hibernate Tools 3.4.0.CR1

/**
 * BpsAdminNodes generated by hbm2java
 */
public class TadminNodes implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = -2186010030840281949L;
	private Integer nodeId;
	private long bitFlag;
	private String name;
	private String uri;
	private String method;
	private int pid;
	private boolean isMenu;
	private boolean status;
	private String groupName;
	private Short groupSort;
	private String descr;
	private String menuIcon;

	public TadminNodes() {
	}

	public TadminNodes(long bitFlag, String name, String uri,String method, int pid,
			boolean isMenu, boolean status) {
		this.bitFlag = bitFlag;
		this.name = name;
		this.uri = uri;
		this.method=method;
		this.pid = pid;
		this.isMenu = isMenu;
		this.status = status;
	}

	public TadminNodes(long bitFlag, String name, String uri,String method, int pid,
			boolean isMenu, boolean status, String groupName, Short groupSort,
			String descr) {
		this.bitFlag = bitFlag;
		this.name = name;
		this.uri = uri;
		this.method=method;
		this.pid = pid;
		this.isMenu = isMenu;
		this.status = status;
		this.groupName = groupName;
		this.groupSort = groupSort;
		this.descr = descr;
	}

	public boolean isMenu() {
		return isMenu;
	}

	public void setMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}	

	public long getBitFlag() {
		return bitFlag;
	}

	public void setBitFlag(long bitFlag) {
		this.bitFlag = bitFlag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public boolean isIsMenu() {
		return this.isMenu;
	}

	public void setIsMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Short getGroupSort() {
		return this.groupSort;
	}

	public void setGroupSort(Short groupSort) {
		this.groupSort = groupSort;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

}