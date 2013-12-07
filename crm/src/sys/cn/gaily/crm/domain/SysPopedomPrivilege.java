package cn.gaily.crm.domain;

@SuppressWarnings("serial")
public class SysPopedomPrivilege implements java.io.Serializable {
	/*
	 * CREATE TABLE sys_popedom_privilege ( roleId VARCHAR(36), #权限组编号
	 * popedomModule VARCHAR(30), #模块名称 popedomPrivilege VARCHAR(30), #操作名称
	 * PRIMARY KEY(roleId,popedomModule,popedomPrivilege) )
	 */

	private SysPopedomPrivilegeId id;

	public SysPopedomPrivilegeId getId() {
		return id;
	}

	public void setId(SysPopedomPrivilegeId id) {
		this.id = id;
	}

}
