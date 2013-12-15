package cn.gaily.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.LinkmanSearch;
import cn.gaily.crm.dao.LinkmanDao;
import cn.gaily.crm.dao.SysCodeRuleDao;
import cn.gaily.crm.dao.SysOperateLogDao;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.SysCodeRule;
import cn.gaily.crm.domain.SysOperateLog;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.LinkmanService;
import cn.gaily.crm.util.DataType;

@Service(value = "linkmanService")
@Transactional(readOnly = true)
public class LinkmanServiceImpl implements LinkmanService {

	@Resource(name = "linkmanDao")
	private LinkmanDao linkmanDao;

	@Resource(name = "sysCodeRuleDao")
	private SysCodeRuleDao sysCodeRuleDao;

	@Resource(name = "sysOperateLogDao")
	private SysOperateLogDao sysOperateLogDao;

	@Override
	@Transactional(readOnly = false)
	public String getLinkmanCodeByTabName(String tabName) {
		// 获取代码规则，查询sys_code_rule
		String whereHql = " and o.tabName=?";
		Object[] params = { tabName };
		List<SysCodeRule> list = sysCodeRuleDao
				.findObjectsByConditionWithNoPage(whereHql, params);
		if (list == null || list.size() != 1) {
			throw new RuntimeException("不能生成联系人的编码！");
		}
		SysCodeRule sysCodeRule = list.get(0);

		// 获取 是否被修改过或 新添加的 字段的值
		// 如果 是否被修改过或新添加的=='Y'
		if (sysCodeRule.getAvailable().equals("Y")) {
			// 获取 流水位
			Integer glideBit = sysCodeRule.getGlideBit();
			// 生成第一个流水号
			String firstGlideNumber = DataType.geneFirstGlideNumber(glideBit);
			// 计算 下一个流水号
			String nextGlideNumber = DataType
					.geneNextGlideNumber(firstGlideNumber);
			// 获取系统当前日期
			String curDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
			// 生成联系人编码
			String code = sysCodeRule.getAreaPrefix()
					+ "-"
					+ DateFormatUtils.format(new Date(),
							sysCodeRule.getAreaTime()) + "-" + firstGlideNumber;
			// 修改编码规则表
			sysCodeRule.setNextseq(nextGlideNumber);
			sysCodeRule.setCurDate(curDate);
			sysCodeRule.setAvailable("N");
			sysCodeRule.setCurrentCode(code);
			sysCodeRuleDao.update(sysCodeRule);
			return code;
		} else {// 是否被修改过或新添加的==“N”
				// 获取代码规则表中的当前日期字段的值
			String curDate = sysCodeRule.getCurDate();
			// 获取系统的当前日期
			String sysCurDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
			if (curDate.equals(sysCurDate)) {
				// 获取下一个序列号
				String nextseq = sysCodeRule.getNextseq();
				// 计算新的流水号
				String nextGlideNumber = DataType.geneNextGlideNumber(nextseq);
				// 生成联系人编码
				String code = sysCodeRule.getAreaPrefix()
						+ "-"
						+ DateFormatUtils.format(new Date(),
								sysCodeRule.getAreaTime()) + "-" + nextseq;
				// 修改代码规则
				sysCodeRule.setNextseq(nextGlideNumber);
				sysCodeRule.setCurrentCode(code);
				sysCodeRuleDao.update(sysCodeRule);

				return code;
			} else { // 代码规则表中的当前日期字段的值！=系统的当前日期
						// 获取流水号
				Integer glideBit = sysCodeRule.getGlideBit();
				// 生成第一个流水号
				String firstGlideNumber = DataType
						.geneFirstGlideNumber(glideBit);
				// 计算下一个流水号
				String nextGlideNumber = DataType
						.geneNextGlideNumber(firstGlideNumber);
				// 生成联系人编码
				String code = sysCodeRule.getAreaPrefix()
						+ "-"
						+ DateFormatUtils.format(new Date(),
								sysCodeRule.getAreaTime()) + "-"
						+ firstGlideNumber;
				// 修改编码规则表
				sysCodeRule.setNextseq(nextGlideNumber);
				sysCodeRule.setCurDate(sysCurDate);
				sysCodeRule.setAvailable("N");
				sysCodeRule.setCurrentCode(code);
				sysCodeRuleDao.update(sysCodeRule);
				return code;
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveLinkman(SysUser curSysuser, Linkman linkman) {
		if (curSysuser != null && linkman != null) {
			linkmanDao.save(linkman);

			// 处理日志
			// 处理日志
			SysOperateLog log = new SysOperateLog();
			log.setUserName(curSysuser.getName());
			log.setCnname(curSysuser.getCnname());
			log.setActionType("添加");
			String actionContent = "添加一个联系人信息[ID:" + linkman.getId()
					+ ",联系人名称:" + linkman.getName() + ",联系人编码:"
					+ linkman.getCode() + "]";
			log.setActionContent(actionContent);
			log.setActionDate(DateFormatUtils.format(new java.util.Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sysOperateLogDao.save(log);
		}
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<Linkman> findLinkmansByCondition(SysUser curSysuser,
			LinkmanSearch linkmanSearch) {
		if (curSysuser == null) {
			throw new RuntimeException("传递给业务层联系人查询条件的对象为空");
		}
		if (curSysuser != null) {
			String whereHql = "";
			List paramList = new ArrayList();

			// 只能查自己的客户
			if (curSysuser.getId() != null) {
				whereHql += " and o.sysUser.id=?";
				paramList.add(curSysuser.getId());
			}
			if (StringUtils.isNotBlank(linkmanSearch.getName())) {
				whereHql += " and o.name like ?";
				paramList.add("%" + linkmanSearch.getName().trim() + "%");
			}
			if (StringUtils.isNotBlank(linkmanSearch.getPycode())) {
				whereHql += " and o.pycode like ?";
				paramList.add("%" + linkmanSearch.getPycode().trim() + "%");
			}
			if (StringUtils.isNotBlank(linkmanSearch.getCompanyId())) {
				whereHql += " and o.company.id=?";
				paramList.add(Integer.parseInt(linkmanSearch.getCompanyId()));
			}
			if (StringUtils.isNotBlank(linkmanSearch.getDepartment())) {
				whereHql += " and o.department like ?";
				paramList.add("%" + linkmanSearch.getDepartment().trim() + "%");
			}
			if (StringUtils.isNotBlank(linkmanSearch.getDuty())) {
				whereHql += " and o.duty like ?";
				paramList.add("%" + linkmanSearch.getDuty().trim() + "%");
			}
			if (StringUtils.isNotBlank(linkmanSearch.getSex())) {
				whereHql += " and o.sex=?";
				paramList.add(linkmanSearch.getSex().trim());
			}
			Object[] params = paramList.toArray();
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.id", "asc");
			List<Linkman> linkmans = linkmanDao
					.findObjectsByConditionWithNoPage(whereHql, params, orderby);
			return linkmans;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Linkman findLinkmanById(Integer id) {
		return linkmanDao.findObjectById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateLinkman(SysUser curSysuser, Linkman linkman) {
		if (curSysuser != null && linkman != null) {
			linkmanDao.update(linkman);
			// 处理日志

		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteLinkmansByIds(SysUser cursysUser, Integer[] ids) {
		if (cursysUser != null && ids != null && ids.length > 0) {
			linkmanDao.deleteByIds((java.io.Serializable[]) ids);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Linkman> findLinkmanByComp(SysUser curSysuser, Company company) {
		if (company != null && curSysuser != null) {
			String whereHql = "";
			List paramList = new ArrayList();
			//客户所属人不属于当前用户，但该客户下的某些联系人被共享给了当前用户，同样进行查询
			if (!company.getSysUser().getId().equals(curSysuser.getId())) {
				
			}
			//客户的所属人属于当前用户，才进行查询
			if (company.getSysUser().getId().equals(curSysuser.getId())) {
				whereHql = " and o.company.id =?";
				paramList.add(company.getId());
				LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
				orderby.put("o.id", "asc");
				return linkmanDao.findObjectsByConditionWithNoPageCache(
						whereHql, paramList.toArray(), orderby);
			}
		}
		return null;
	}
}
