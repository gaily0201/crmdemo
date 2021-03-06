package cn.gaily.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.type.DateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.SysUserSearch;
import cn.gaily.crm.dao.SysOperateLogDao;
import cn.gaily.crm.dao.SysUserDao;
import cn.gaily.crm.domain.SysOperateLog;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.SysUserService;
import cn.gaily.crm.util.DataType;
import freemarker.template.utility.StringUtil;

@Service(value = "sysUserService")
@Transactional(readOnly = true)
public class SysUserServiceImpl implements SysUserService {

	@Resource(name = "sysUserDao")
	private SysUserDao sysUserDao;

	@Resource(name = "sysOperateLogDao")
	private SysOperateLogDao sysOperateLogDao;

	@Override
	@Transactional(readOnly = false)
	public SysUser findSysUserByNameAndPassword(String name, String password) {

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
			String whereHql = " and o.name = ? and o.password = ?";
			Object[] params = { name, password };

			// 调用Dao查询
			List<SysUser> list = sysUserDao.findObjectsByConditionWithNoPage(
					whereHql, params);
			String actionContent = "";
			if (list != null && list.size() == 1) {
				SysUser curSysuser = list.get(0);
				
				// 处理日志
				SysOperateLog log = new SysOperateLog();
				log.setUserName(curSysuser.getName());
				log.setCnname(curSysuser.getCnname());
				if (list.get(0).getStatus().equals("Y")) {
					log.setActionType("登录系统");
					actionContent = curSysuser.getCnname()
							+ "于 "
							+ DateFormatUtils.format(new Date(),
									"yyyy-MM-dd HH:mm:ss") + " 登录系统";
				} else if (list.get(0).getStatus().equals("N")) {
					actionContent = curSysuser.getCnname()
							+ "于"
							+ DateFormatUtils.format(new Date(),
									"yyyy-MM-dd HH:mm:ss") + " 试图登录系统，但被系统阻止！";
				}
				log.setActionContent(actionContent);
				log.setActionDate(DateFormatUtils.format(new java.util.Date(),
						"yyyy-MM-dd HH:mm:ss"));
				sysOperateLogDao.save(log);
				
				return curSysuser; //返回用户
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		sysUserDao.save(sysUser);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysUser> findSysUsersByCondition(SysUserSearch sysUserSearch) {
		// TODO Auto-generated method stub
		if (sysUserSearch == null) {
			throw new RuntimeException("传递给业务层部门查询条件的对象为空");
		}

		String whereHql = "";
		List paramList = new ArrayList();

		if (StringUtils.isNotBlank(sysUserSearch.getName())) {
			whereHql += " and o.name like ?";
			paramList.add("%" + sysUserSearch.getName().trim() + "%");
		}
		if (StringUtils.isNotBlank(sysUserSearch.getCnname())) {
			whereHql += " and o.cnname like ?";
			paramList.add("%" + sysUserSearch.getCnname().trim() + "%");
		}
		if (StringUtils.isNotBlank(sysUserSearch.getStatus())) {
			whereHql += " and o.status = ?";
			paramList.add(sysUserSearch.getStatus().trim());
		}
		if (sysUserSearch.getGroupId() != null
				&& sysUserSearch.getGroupId() != 0) {
			whereHql += " and  o.sysUserGroup.id = ?";
			paramList.add(sysUserSearch.getGroupId());
		}

		Object[] params = paramList.toArray();

		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");

		return sysUserDao.findObjectsByConditionWithNoPage(whereHql, params,
				orderby);

	}

	@Override
	@Transactional(readOnly = false)
	public void deleteSysUsersByIds(Integer... ids) {
		// TODO Auto-generated method stub
		List<Integer> listids = new ArrayList<Integer>();
		for(int m=0;m<ids.length;m++){
			listids.add(ids[m]);
		} //不删除超级管理员
		if(listids.contains(Integer.parseInt("1"))){
			listids.remove(0);
		}
		Integer[] lids = new Integer[listids.size()];
		for(int i=0;i<lids.length;i++){
			lids[i]=listids.get(i);
		}
		
		sysUserDao.deleteByIds((java.io.Serializable[]) lids);
	}

	@Override
	@Transactional(readOnly = true)
	public SysUser findSysUserById(Integer id) {
		// TODO Auto-generated method stub
		return sysUserDao.findObjectById(id);

	}

	@Override
	@Transactional(readOnly = false)
	public void enableSysUsersByIds(Integer[] ids) {
		// TODO Auto-generated method stub
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				SysUser sysUser = sysUserDao.findObjectById(id);
				sysUser.setStatus("Y");
				sysUserDao.update(sysUser);
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void disableSysUsersByIds(Integer[] ids) {
		// TODO Auto-generated method stub
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				SysUser sysUser = sysUserDao.findObjectById(id);
				sysUser.setStatus("N");
				sysUserDao.update(sysUser);
			}
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void updateSysUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		sysUserDao.update(sysUser);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysUser> findAllSysUsers() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		return sysUserDao.findObjectsByConditionWithNoPageCache(null, null,
				orderby);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateSysUsersPassword(SysUser curSysuser, SysUser sysUser) {
		// TODO Auto-generated method stub
		if (sysUser != null && curSysuser != null) {
			sysUserDao.update(sysUser);

			// 处理日志
			SysOperateLog log = new SysOperateLog();
			log.setUserName(curSysuser.getName());
			log.setCnname(curSysuser.getCnname());
			log.setActionType("修改密码");
			String actionContent = "修改用户[" + sysUser.getCnname() + "]的密码";
			log.setActionContent(actionContent);
			log.setActionDate(DateFormatUtils.format(new java.util.Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sysOperateLogDao.save(log);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public SysUser findSysUserByCnname(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			String whereHql = "";
			List paramList = new ArrayList();
			whereHql = " and o.cnname=?";
			paramList.add(userName);
			List<SysUser> sysUser = sysUserDao
					.findObjectsByConditionWithNoPage(whereHql,
							paramList.toArray());
			if (sysUser.size() == 1) {
				return sysUser.get(0);
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysUser> findSysUserInOrNotInGroup(Integer groupId, String flag) {
		if(groupId!=null&&StringUtils.isNotBlank(flag)){
			StringBuffer whereHql  = new StringBuffer("");
			if("Y".equals(flag)){
				whereHql.append(" and o.sysUserGroup.id=?");
			}else if("N".equals(flag)){
				whereHql.append(" and o.sysUserGroup.id<>?");
			}
			List params = new ArrayList();
			params.add(groupId);
			return sysUserDao.findObjectsByConditionWithNoPage(whereHql.toString(), params.toArray(), null);
		}
		return null;
	}
}
