package cn.gaily.crm.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.SysRoleDao;
import cn.gaily.crm.domain.SysRole;

@Repository(value="sysRoleDao")
public class SysRoleDaoImpl extends CommonDaoImpl<SysRole> implements SysRoleDao {

}
