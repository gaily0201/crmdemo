package cn.gaily.crm.web.form;

import java.io.Serializable;
/**
 * vo对象，保存jsp页面的值
 * @author Administrator
 *
 */
public class SysUserGroupForm implements Serializable {
	
	private String id;
	private String remark;   //备注
	private String name;     //部门名称
	private String principal; //部门负责人
	private String incumbent; //部门职能
	
	
	
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
