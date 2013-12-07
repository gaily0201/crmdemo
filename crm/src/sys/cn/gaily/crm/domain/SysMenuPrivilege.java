package cn.gaily.crm.domain;

import java.io.Serializable;

public class SysMenuPrivilege implements Serializable {

	private static final long serialVersionUID = 1L;

	private SysMenuPrivilegeId id;

	public SysMenuPrivilegeId getId() {
		return id;
	}
	public void setId(SysMenuPrivilegeId id) {
		this.id = id;
	}
}
