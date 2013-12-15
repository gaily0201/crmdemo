package cn.gaily.crm.domain;

import java.sql.Date;

public class Linktouch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Linkman linkman;
	private SysUser sysUser;
	private Date linkTime;
	private Date nextTouchDate;
	private String linkFashion;
	private String linkType;
	private String content;
	private String nextTouchAim;
	private String userName;
	private String remark;
	private String creater;
	private String createTime;
	private String updater;
	private String updateTime;
	private String dispensePerson;
	private String dispenseDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Linkman getLinkman() {
		return linkman;
	}
	public void setLinkman(Linkman linkman) {
		this.linkman = linkman;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public Date getLinkTime() {
		return linkTime;
	}
	public void setLinkTime(Date linkTime) {
		this.linkTime = linkTime;
	}
	public Date getNextTouchDate() {
		return nextTouchDate;
	}
	public void setNextTouchDate(Date nextTouchDate) {
		this.nextTouchDate = nextTouchDate;
	}
	public String getLinkFashion() {
		return linkFashion;
	}
	public void setLinkFashion(String linkFashion) {
		this.linkFashion = linkFashion;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNextTouchAim() {
		return nextTouchAim;
	}
	public void setNextTouchAim(String nextTouchAim) {
		this.nextTouchAim = nextTouchAim;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDispensePerson() {
		return dispensePerson;
	}
	public void setDispensePerson(String dispensePerson) {
		this.dispensePerson = dispensePerson;
	}
	public String getDispenseDate() {
		return dispenseDate;
	}
	public void setDispenseDate(String dispenseDate) {
		this.dispenseDate = dispenseDate;
	}
}