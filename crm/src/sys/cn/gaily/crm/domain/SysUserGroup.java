package cn.gaily.crm.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * po对象，这里的值和数据库对应
 * @author Administrator
 *
 */
public class SysUserGroup {
/*
 #部门信息表
CREATE TABLE `sys_user_group` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,               #编号
  `remark` TEXT,                                          #备注
  `name` VARCHAR(100) DEFAULT NULL,                       #部门名称
  `principal` VARCHAR(50)  DEFAULT NULL,                  #部门负责人
  `incumbent` VARCHAR(200)  DEFAULT NULL,                 #部门职能
  PRIMARY KEY (`id`)
)
 */
	
	private Integer id;
	private String remark;
	private String name;
	private String principal;
	private String incumbent;
	
	//一个部门包含对个用户，不做配置
	//private Set user = new HashSet(0);
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getIncumbent() {
		return incumbent;
	}
	public void setIncumbent(String incumbent) {
		this.incumbent = incumbent;
	}
	
	
	
}
