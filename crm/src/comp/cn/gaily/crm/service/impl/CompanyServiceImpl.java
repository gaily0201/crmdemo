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
import cn.gaily.crm.bean.CompanySearch;
import cn.gaily.crm.dao.CompanyDao;
import cn.gaily.crm.dao.SysCodeRuleDao;
import cn.gaily.crm.dao.SysOperateLogDao;
import cn.gaily.crm.dao.SysUserDao;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.SysCodeRule;
import cn.gaily.crm.domain.SysOperateLog;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.CompanyService;
import cn.gaily.crm.util.DataType;

@Transactional(readOnly = true)
@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService {

	@Resource(name = "sysCodeRuleDao")
	private SysCodeRuleDao sysCodeRuleDao;

	@Resource(name = "companyDao")
	private CompanyDao companyDao;

	@Resource(name = "sysUserDao")
	private SysUserDao sysUserDao;

	@Resource(name = "sysOperateLogDao")
	private SysOperateLogDao sysOperateLogDao;

	@Override
	@Transactional(readOnly = false)
	public String getCompanyCodeByTabName(String tabName) {
		// 获取代码规则，查询sys_code_rule
		String whereHql = " and o.tabName=?";
		Object[] params = { tabName };
		List<SysCodeRule> list = sysCodeRuleDao
				.findObjectsByConditionWithNoPage(whereHql, params);
		if (list == null || list.size() != 1) {
			throw new RuntimeException("不能生成客户的编码！");
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
			// 生成客户编码
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
				// 生成客户编码
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
				// 生成客户编码
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
	public void saveCompany(SysUser curSysuser, Company company) {
		if (curSysuser != null && company != null) {
			companyDao.save(company);

			// 处理日志
			SysOperateLog log = new SysOperateLog();
			log.setUserName(curSysuser.getName());
			log.setCnname(curSysuser.getCnname());
			log.setActionType("添加");
			String actionContent = "添加一个客户信息[ID:" + company.getId() + ",客户名称:"
					+ company.getName() + ",客户编码:" + company.getCode() + "]";
			log.setActionContent(actionContent);
			log.setActionDate(DateFormatUtils.format(new java.util.Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sysOperateLogDao.save(log);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<Company> findCompanyByCondition(SysUser curSysuser,
			CompanySearch companySearch) {
		if (companySearch == null) {
			throw new RuntimeException("传递给业务层部门查询条件的对象为空");
		}
		if (curSysuser != null && companySearch != null) {
			String whereHql = "";
			List paramList = new ArrayList();

			// 只能查自己的客户
			if (curSysuser.getId() != null) {
				whereHql += " and o.sysUser.id=?";
				paramList.add(curSysuser.getId());
			}

			if (StringUtils.isNotBlank(companySearch.getCode())) {
				whereHql += " and o.code like ?";
				paramList.add("%" + companySearch.getCode().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getName())) {
				whereHql += " and o.name like ?";
				paramList.add("%" + companySearch.getName().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getPycode())) {
				whereHql += " and o.pycode like ?";
				paramList.add("%" + companySearch.getPycode().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getTel1())) {
				whereHql += " and o.tel1 like ?";
				paramList.add("%" + companySearch.getTel1().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getGrade())) {
				whereHql += " and o.grade like ?";
				paramList.add("%" + companySearch.getGrade().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getSource())) {
				whereHql += " and o.source like ?";
				paramList.add("%" + companySearch.getSource().trim() + "%");
			}
			if (StringUtils.isNotBlank(companySearch.getQuality())) {
				whereHql += " and o.quality like ?";
				paramList.add("%" + companySearch.getQuality().trim() + "%");
			}
			Object[] params = paramList.toArray();
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.id", "asc");

			return companyDao.findObjectsByConditionWithNoPage(whereHql,
					params, orderby);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Company findCompanyById(Integer id) {
		return companyDao.findObjectById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateCompany(SysUser curSysuser, Company company) {
		if (curSysuser != null && company != null) {
			companyDao.update(company);

			// 处理日志
			SysOperateLog log = new SysOperateLog();
			log.setUserName(curSysuser.getName());
			log.setCnname(curSysuser.getCnname());
			log.setActionType("更新");
			String actionContent = "更新一个客户信息[ID:" + company.getId() + ",客户名称:"
					+ company.getName() + ",客户编码:" + company.getCode() + "]";
			log.setActionContent(actionContent);
			log.setActionDate(DateFormatUtils.format(new java.util.Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sysOperateLogDao.save(log);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteCompanyByIds(SysUser curSysuser, Integer[] ids) {
		if (curSysuser != null && ids != null && ids.length > 0) {
			
			// 处理日志
			for(int i=0;i<ids.length;i++){
				Company company = companyDao.findObjectById(ids[i]);
				SysOperateLog log = new SysOperateLog();
				log.setUserName(curSysuser.getName());
				log.setCnname(curSysuser.getCnname());
				log.setActionType("删除");
				String actionContent = "删除客户信息[ID:" + company.getId() + ",客户名称:"
						+ company.getName() + ",客户编码:" + company.getCode() + "]";
				log.setActionContent(actionContent);
				log.setActionDate(DateFormatUtils.format(new java.util.Date(),
						"yyyy-MM-dd HH:mm:ss"));
				sysOperateLogDao.save(log);
			}
			//删除客户
			companyDao.deleteByIds((java.io.Serializable[]) ids);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void addUpdateShareSetOne(SysUser curSysuser,String s_module, Integer id, Integer[] uids) {
		if (StringUtils.isNotBlank(s_module) && id != null && uids != null
				&& uids.length > 0) {
			if ("c_company".equals(s_module)) {// 模块名称为客户模块
				Company company = companyDao.findObjectById(id);
				if (company != null&&curSysuser!=null) {
					// 处理用户#1#2#
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < uids.length; i++) {
						sb.append(uids[i] + "#");
					}
					if ('N' == company.getShareFlag()) {
						company.setShareFlag('Y');
						company.setShareIds("#" + sb.toString());
						companyDao.update(company);
					} else if ('Y' == company.getShareFlag()) {
						company.setShareFlag('Y');
						company.setShareIds(company.getShareIds()
								+ sb.toString());
						companyDao.update(company);
					}
					
					//处理增加共享日志
					SysOperateLog log = new SysOperateLog();
					log.setUserName(curSysuser.getName());
					log.setCnname(curSysuser.getCnname());
					log.setActionType("增加共享");
					StringBuffer usernames = new StringBuffer();
					for(int i=0;i<uids.length;i++){
						SysUser sysUser = sysUserDao.findObjectById(uids[i]);
						usernames.append(sysUser.getCnname()+",");
					}
					String actionContent = "增加共享客户给"+usernames.toString()+
							"共享客户信息[ID:" + company.getId() + ",客户名称:"
							+ company.getName() + ",客户编码:" + company.getCode() +"]";
					log.setActionContent(actionContent);
					log.setActionDate(DateFormatUtils.format(new java.util.Date(),
							"yyyy-MM-dd HH:mm:ss"));
					sysOperateLogDao.save(log);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void minusUpdateShareSetOne(SysUser curSysuser,String s_module, Integer id,
			Integer[] uids) {
		if (StringUtils.isNotBlank(s_module) && id != null && uids != null
				&& uids.length > 0) {
			if ("c_company".equals(s_module)) {// 模块名称为客户模块
				Company company = companyDao.findObjectById(id);
				if (company != null&&curSysuser!=null) {
					// N不处理
					if ('Y' == company.getShareFlag()) {
						String shareIds = company.getShareIds();

						for (int i = 0; i < uids.length; i++) {
							String uid = "#" + uids[i] + "#";
							while (true) {
								if (shareIds != null && shareIds.contains(uid)) {
									shareIds = shareIds.replaceAll(uid, "#");
								} else {
									break;
								}
							}
						}
						if ("#".equals(shareIds)) {
							company.setShareFlag('N');
							company.setShareIds(null);
							companyDao.update(company);
						} else {
							company.setShareIds(shareIds);
							company.setShareIds(shareIds);
							companyDao.update(company);
						}
					}
					
					//处理减少共享日志
					SysOperateLog log = new SysOperateLog();
					log.setUserName(curSysuser.getName());
					log.setCnname(curSysuser.getCnname());
					log.setActionType("减少共享客户");
					StringBuffer usernames = new StringBuffer();
					for(int i=0;i<uids.length;i++){
						SysUser sysUser = sysUserDao.findObjectById(uids[i]);
						usernames.append(sysUser.getCnname()+",");
					}
					usernames.deleteCharAt(usernames.length()-1);
					
					String actionContent = "对"+usernames.toString()+"减少共享客户"+
							"减少共享客户信息[ID:" + company.getId() + ",客户名称:"
							+ company.getName() + ",客户编码:" + company.getCode() + 
							" 给"+usernames.toString()+"]";
					log.setActionContent(actionContent);
					log.setActionDate(DateFormatUtils.format(new java.util.Date(),
							"yyyy-MM-dd HH:mm:ss"));
					sysOperateLogDao.save(log);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateShareCancelOne(SysUser curSysuser,Integer id, String s_module) {
		if (id != null && StringUtils.isNotBlank(s_module)) {
			Company company = companyDao.findObjectById(id);
			if (company != null&&curSysuser!=null) {
				company.setShareFlag('N');
				company.setShareIds(null);
				companyDao.update(company);
				
				// 处理日志
				SysOperateLog log = new SysOperateLog();
				log.setUserName(curSysuser.getName());
				log.setCnname(curSysuser.getCnname());
				log.setActionType("取消共享");
				String actionContent = "取消共享客户，客户信息[ID:" + company.getId() + ",客户名称:"
						+ company.getName() + ",客户编码:" + company.getCode() + "]";
				log.setActionContent(actionContent);
				log.setActionDate(DateFormatUtils.format(new java.util.Date(),
						"yyyy-MM-dd HH:mm:ss"));
				sysOperateLogDao.save(log);
				
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysUser> findSysUserBySharedIds(Integer id) {
		if (id != null) {
			Company company = companyDao.findObjectById(id);
			if (company != null) {
				String shareIds = company.getShareIds();
				if (StringUtils.isNotBlank(shareIds)) {
					String[] sids = shareIds.split("#");
					Integer[] ids = DataType
							.converterStringArray2IntegerArray(sids);

					if (ids != null && ids.length > 0) {
						StringBuffer whereHql = new StringBuffer();
						whereHql.append(" and o.id in(");
						List<Integer> params = new ArrayList<Integer>();
						for (int i = 1; i < ids.length; i++) {
							whereHql.append("?,");
							params.add(ids[i]);
						}
						whereHql.deleteCharAt(whereHql.length() - 1);
						whereHql.append(")");
						return sysUserDao.findObjectsByConditionWithNoPage(
								whereHql.toString(), params.toArray());
					}
				}
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> findSharedCompany(SysUser curSysuser) {
		String id = curSysuser.getId() + "";
		if (id != null) {
			List<Company> companys = new ArrayList<Company>();
			String whereHql = " and o.shareFlag=?";
			List params = new ArrayList();
			params.add('Y');
			List<Company> list = companyDao.findObjectsByConditionWithNoPage(
					whereHql, params.toArray());
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Company company = list.get(i);
					String shareIds = company.getShareIds();
					if (StringUtils.isNotBlank(shareIds)
							&& shareIds.contains("#" + id + "#")) {
						companys.add(company);
					}
				}
				return companys;
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateNextTouchTime(SysUser curSysuser,Integer[] ids, java.sql.Date next_touch_date) {
		if (ids != null && ids.length > 0 && next_touch_date != null && curSysuser!=null) {
			for (int i = 0; i < ids.length; i++) {
				Company company = companyDao.findObjectById(ids[i]);
				if (company != null) {
					company.setNextTouchDate(next_touch_date);
					companyDao.update(company);
					
					//处理日志
					SysOperateLog log = new SysOperateLog();
					log.setUserName(curSysuser.getName());
					log.setCnname(curSysuser.getCnname());
					log.setActionType("变更下次联系时间");
					String actionContent = "变更客户[ID:" + company.getId() + ",客户名称:"
							+ company.getName() + ",客户编码:" + company.getCode() + "]"+
							"下次联系时间为："+next_touch_date.toString();
					log.setActionContent(actionContent);
					log.setActionDate(DateFormatUtils.format(new java.util.Date(),
							"yyyy-MM-dd HH:mm:ss"));
					sysOperateLogDao.save(log);
				}
			}
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void changeHandler(SysUser curSysuser, Integer[] id, Integer new_owner) {
		if (id != null && id.length > 0 && new_owner != null) {
			// 查询客户的信息
			SysUser sysUser = sysUserDao.findObjectById(new_owner);
			for (int i = 0; i < id.length; i++) {
				Company company = companyDao.findObjectById(id[i]);
				if (company != null && sysUser != null && curSysuser!=null) {
					company.setSysUser(sysUser);
					company.setDispensePerson(sysUser.getCnname());
					company.setDispenseDate(DateFormatUtils.format(new Date(),
							"yyyy-MM-dd HH:mm:ss"));
					companyDao.update(company);
					
					
					//处理日志
					SysOperateLog log = new SysOperateLog();
					log.setUserName(curSysuser.getName());
					log.setCnname(curSysuser.getCnname());
					log.setActionType("变更经手人");
					String actionContent = "将客户[ID:" + company.getId() + ",客户名称:"
							+ company.getName() + ",客户编码:" + company.getCode() + "]"+
							"经手人变更为："+sysUser.getCnname();
					log.setActionContent(actionContent);
					log.setActionDate(DateFormatUtils.format(new java.util.Date(),
							"yyyy-MM-dd HH:mm:ss"));
					sysOperateLogDao.save(log);
					
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> findMyOwnCompanys(SysUser curSysuser) {
		String whereHql = " and o.sysUser.id = ?";
		Object[] paramList ={curSysuser.getId()};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		List<Company> myOwnComp = companyDao.findObjectsByConditionWithNoPageCache(whereHql, paramList, orderby);
		return myOwnComp;
	}
}
