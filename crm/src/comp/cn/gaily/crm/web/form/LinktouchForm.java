package cn.gaily.crm.web.form;

import java.sql.Date;

import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.SysUser;

public class LinktouchForm implements java.io.Serializable {

	// Fields

	private Integer id;
	private String linkmanId;
	private String sysUserId;
	private String linkTime;
	private String nextTouchDate;
	private String linkFashion;
	private String linkType;
	private String content;
	private String nextTouchAim;
	private String userNameId;
	private String remark;
	private String creater;
	private String createTime;
	private String updater;
	private String updateTime;
	private String dispensePerson;
	private String dispenseDate;
	
	private String beginDate;
	private String endDate;
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLinkmanId() {
		return linkmanId;
	}
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkTime() {
		return linkTime;
	}
	public void setLinkTime(String linkTime) {
		this.linkTime = linkTime;
	}
	public String getNextTouchDate() {
		return nextTouchDate;
	}
	public void setNextTouchDate(String nextTouchDate) {
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
	
	public String getUserNameId() {
		return userNameId;
	}
	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
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