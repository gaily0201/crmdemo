package cn.gaily.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.SysOperateLogDao;
import cn.gaily.crm.domain.SysOperateLog;

@Repository(value="sysOperateLogDao")
public class SysOperateLogDaoImpl extends CommonDaoImpl<SysOperateLog> implements SysOperateLogDao {

}
