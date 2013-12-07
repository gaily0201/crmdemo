package cn.gaily.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.SysCodeRuleDao;
import cn.gaily.crm.domain.SysCodeRule;

@Repository(value="sysCodeRuleDao")
public class SysCodeRuleDaoImpl extends CommonDaoImpl<SysCodeRule> implements
		SysCodeRuleDao {

}
