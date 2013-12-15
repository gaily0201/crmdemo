package cn.gaily.crm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.LinktouchSearch;
import cn.gaily.crm.dao.LinktouchDao;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.Linktouch;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.LinktouchService;
import cn.gaily.crm.util.SQLDateConverter;

@Service(value = "linktouchService")
@Transactional(readOnly = true)
public class LinktouchServiceImpl implements LinktouchService {

	@Resource(name = "linktouchDao")
	private LinktouchDao linktouchDao;

	@Override
	@Transactional(readOnly = false)
	public void saveLinktouch(SysUser curSysuser, Linktouch linktouch) {
		if (curSysuser != null && linktouch != null) {
			linktouchDao.save(linktouch);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Linktouch> findMyOwnLinktouchsByCondition(SysUser curSysuser,
			LinktouchSearch linktouchSearch) {
		if (curSysuser != null) {
			StringBuffer whereHql = new StringBuffer("");
			List paramList = new ArrayList();
			whereHql.append(" and o.sysUser.id=?");
			paramList.add(curSysuser.getId());

			if (StringUtils.isNotBlank(linktouchSearch.getLinkmanId())) {
				whereHql.append(" and o.linkman.name=?");
				paramList.add(linktouchSearch.getLinkmanId().trim());
			}
			if (StringUtils.isNotBlank(linktouchSearch.getLinkFashion())) {
				whereHql.append(" and o.linkFashion=?");
				paramList.add(linktouchSearch.getLinkFashion().trim());
			}
			if (StringUtils.isNotBlank(linktouchSearch.getLinkType())) {
				whereHql.append(" and o.linkType=?");
				paramList.add(linktouchSearch.getLinkType().trim());
			}
			if (StringUtils.isNotBlank(linktouchSearch.getUserNameId())) {
				whereHql.append(" and o.userName like ?");
				paramList.add(linktouchSearch.getUserNameId().trim());
			}
			if (StringUtils.isNotBlank(linktouchSearch.getBeginDate())) {
				whereHql.append(" and o.linkTime >?");
				paramList.add(java.sql.Date.valueOf(linktouchSearch
						.getBeginDate()));
			}
			if (StringUtils.isNotBlank(linktouchSearch.getEndDate())) {
				whereHql.append(" and o.linkTime<?");
				paramList.add(java.sql.Date.valueOf(linktouchSearch
						.getEndDate()));
			}

			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.id", "asc");
			return linktouchDao.findObjectsByConditionWithNoPageCache(
					whereHql.toString(), paramList.toArray(), orderby);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Linktouch findLinktouchById(Integer id) {
		if (id != null) {
			return linktouchDao.findObjectById(id);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateLinktouch(SysUser curSysuser, Linktouch linktouch) {
		if (curSysuser != null && linktouch != null) {
			linktouchDao.update(linktouch);
			// 处理日志

		}
	}

	@Override
	@Transactional(readOnly = false)
	public List<Linktouch> findlinktouchsByLinkmans(SysUser curSysuser,
			Linkman linkman) {
		if (curSysuser != null && linkman != null) {
			
			List<Linktouch> linktouchs = new ArrayList<Linktouch>();
			StringBuffer whereHql = new StringBuffer("");
			List paramList = new ArrayList();
			whereHql.append(" and o.linkman.id=?");
			paramList.add(linkman.getId());
			linktouchs.addAll(linktouchDao.findObjectsByConditionWithNoPage(
					whereHql.toString(), paramList.toArray(), null));
			return linktouchs;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void delteLinktouchsByIds(SysUser curSysuser, Integer[] ids) {
		if (curSysuser != null && ids != null && ids.length > 0) {
			linktouchDao.deleteByIds((java.io.Serializable[]) ids);
		}

	}
}
