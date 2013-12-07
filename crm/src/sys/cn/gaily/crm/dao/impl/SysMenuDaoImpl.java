package cn.gaily.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.SysMenuDao;
import cn.gaily.crm.dao.SysPopedomDao;
import cn.gaily.crm.domain.SysMenu;
import cn.gaily.crm.domain.SysPopedom;

@Repository(value="sysMenuDao")
public class SysMenuDaoImpl extends CommonDaoImpl<SysMenu> implements SysMenuDao {

}
