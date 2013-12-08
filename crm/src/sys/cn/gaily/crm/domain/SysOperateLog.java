package cn.gaily.crm.domain;

public class SysOperateLog {

	
	private Integer id;
	private String userName;
	private String cnname;
	private String actionType;
	private String actionContent;
	private String actionDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionContent() {
		return actionContent;
	}
	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	
}
