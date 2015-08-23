package com.emos.dto;


public class TadminRoleRights implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = 979892202536550526L;
	private int roleId;
	private long roleRights;
	private long[] rights=new long[]{};

	public TadminRoleRights() {
	}

	public TadminRoleRights(int roleId, long roleRights) {
		this.roleId = roleId;
		this.roleRights = roleRights;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public long getRoleRights() {
		return this.roleRights;
	}

	public void setRoleRights(long roleRights) {
		this.roleRights = roleRights;
	}

	public long[] getRights() {
		return rights;
	}

	public void setRights(long[] rights) {
		this.rights = rights;
	}

}
