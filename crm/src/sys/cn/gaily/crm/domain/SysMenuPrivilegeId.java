package cn.gaily.crm.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SysMenuPrivilegeId implements Serializable {

	private String roleId;
	private String menuModule;
	private String menuPrivilege;

	public String getRoleId() {
		return roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((menuModule == null) ? 0 : menuModule.hashCode());
		result = prime * result
				+ ((menuPrivilege == null) ? 0 : menuPrivilege.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysMenuPrivilegeId other = (SysMenuPrivilegeId) obj;
		if (menuModule == null) {
			if (other.menuModule != null)
				return false;
		} else if (!menuModule.equals(other.menuModule))
			return false;
		if (menuPrivilege == null) {
			if (other.menuPrivilege != null)
				return false;
		} else if (!menuPrivilege.equals(other.menuPrivilege))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuModule() {
		return menuModule;
	}

	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}

	public String getMenuPrivilege() {
		return menuPrivilege;
	}

	public void setMenuPrivilege(String menuPrivilege) {
		this.menuPrivilege = menuPrivilege;
	}

}
