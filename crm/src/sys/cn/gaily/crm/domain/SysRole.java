package cn.gaily.crm.domain;

import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("serial")
public class SysRole implements java.io.Serializable{
	/**
	 
	 #权限组表
	CREATE TABLE `sys_role` (
	  `id` varchar(36),                             #编号
	  `remark` TEXT,                                #备注
	  `name` VARCHAR(100)  DEFAULT NULL,             #名称
	  PRIMARY KEY (`id`)
	)
	 */
	
	private String id;
	private String remark;
	private String name;
	
	//一个角色含有多个用户
	//private Set roleUsers = new HashSet(0);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
