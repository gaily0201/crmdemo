package cn.gaily.crm.web.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.LinktouchSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.Linktouch;
import cn.gaily.crm.domain.SysDictionaryType;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.CompanyService;
import cn.gaily.crm.service.LinkmanService;
import cn.gaily.crm.service.LinktouchService;
import cn.gaily.crm.service.SysDictionaryTypeService;
import cn.gaily.crm.service.SysUserService;
import cn.gaily.crm.util.DataType;
import cn.gaily.crm.util.Global;
import cn.gaily.crm.util.SQLDateConverter;
import cn.gaily.crm.util.SessionUtils;
import cn.gaily.crm.web.form.LinktouchForm;

import com.opensymphony.xwork2.ModelDriven;

public class LinktouchAction extends BaseAction implements
		ModelDriven<LinktouchForm> {

	private static final long serialVersionUID = -3079419955646202217L;

	private LinktouchForm linktouchForm = new LinktouchForm();

	LinkmanService linkmanService = (LinkmanService) ServiceProvinder
			.getService("linkmanService");
	CompanyService companyService = (CompanyService) ServiceProvinder
			.getService("companyService");
	SysDictionaryTypeService sysDictionaryTypeService = (SysDictionaryTypeService) ServiceProvinder
			.getService("sysDictionaryTypeService");
	SysUserService sysUserService = (SysUserService) ServiceProvinder
			.getService("sysUserService");
	LinktouchService linktouchService = (LinktouchService) ServiceProvinder
			.getService("linktouchService");

	
	/**
	 * 从客户查询所有联系记录
	 * @return
	 */
	@Limit(module = "linktouch", privilege = "listbyComp")
	public String listbyComp(){
		String scid = request.getParameter("cid");
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if(curSysuser!=null&&StringUtils.isNotBlank(scid)){
			Integer cid = Integer.parseInt(scid);
			Company company = companyService.findCompanyById(cid);
			if(company!=null){
				List<Linktouch> linktouchs = new ArrayList<Linktouch>();
				List<Linkman> linkmans = linkmanService.findLinkmanByComp(curSysuser, company);
				if(linkmans!=null&&linkmans.size()>0){
					for(int i=0;i<linkmans.size();i++){
						List<Linktouch> linktouch = linktouchService.findlinktouchsByLinkmans(curSysuser, linkmans.get(i));
						linktouchs.addAll(linktouch);
					}
					
					for(int i=0;i<linktouchs.size();i++){
						if(linktouchs.get(i).getContent().length()>40){
							String content = linktouchs.get(i).getContent().substring(0, 39)+"...";
							linktouchs.get(i).setContent(content);
						}
					}
					request.setAttribute("linktouchs", linktouchs);
					return "list";
				}
			}
		}
		return "list";
	}
	
	/**
	 * 删除联系记录
	 * @return
	 */
	@Limit(module = "linktouch", privilege = "delete")
	public String delete(){
		String[] sids = request.getParameterValues("ids");
		SysUser curSysuser= SessionUtils.getSysUserFromSession(request);
		if(curSysuser!=null&&sids!=null&&sids.length>0){
			Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
			linktouchService.delteLinktouchsByIds(curSysuser,ids);
			return "listAction";
		}
		return null;
	}
	
	/**
	 * 更新联系记录
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module = "linktouch", privilege = "update")
	public String update() throws IllegalAccessException, InvocationTargetException {
		Linktouch linktouch = new Linktouch();
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		BeanUtils.copyProperties(linktouch, linktouchForm);

		// 处理所属人
		String suid = linktouchForm.getSysUserId();
		if (StringUtils.isNotBlank(suid)) {
			Integer uid = Integer.parseInt(suid);
			SysUser sysUser = sysUserService.findSysUserById(uid);
			linktouch.setSysUser(sysUser);
		}

		// 处理联系人
		String slid = linktouchForm.getLinkmanId();
		if (StringUtils.isNotBlank(slid)) {
			Integer lid = Integer.parseInt(slid);
			Linkman linkman = linkmanService.findLinkmanById(lid);
			linktouch.setLinkman(linkman);
		}

		// 处理业务员
		String suserid = linktouchForm.getUserNameId();
		if (StringUtils.isNotBlank(suserid)) {
			Integer userid = Integer.parseInt(suserid);
			SysUser user = sysUserService.findSysUserById(userid);
			linktouch.setUserName(user.getCnname());
		}
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			// 设置分配给所属人日期
			linktouch.setDispenseDate(linktouchForm.getCreateTime());
			linktouch.setSysUser(curSysuser);
			linktouch.setUpdater(curSysuser.getCnname());
			linktouch.setUpdateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			linktouchService.updateLinktouch(curSysuser, linktouch);
			
			return "listAction";
		}
		return null;
	}

	/*
	 * 显示编辑
	 */
	@Limit(module = "linktouch", privilege = "edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		List<Company> companys = companyService.findMyOwnCompanys(curSysuser);
		List<Linkman> linkmansSelect = new ArrayList<Linkman>();
		if (curSysuser != null && companys != null) {
			// 联系人下拉选,查出所有属于自己的联系人
			for (int i = 0; i < companys.size(); i++) {
				linkmansSelect.addAll(linkmanService.findLinkmanByComp(
						curSysuser, companys.get(i)));
			}
			request.setAttribute("linkmansSelect", linkmansSelect);
			// 联系方式下拉选
			List<SysDictionaryType> linkFashionsSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKFASHION);
			request.setAttribute("linkFashionsSelect", linkFashionsSelect);
			// 联系类别下拉选
			List<SysDictionaryType> linkTypesSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKTYPE);
			request.setAttribute("linkTypesSelect", linkTypesSelect);
			// 处理业务员下拉选
			List<SysUser> userNamesSelect = sysUserService.findAllSysUsers();
			request.setAttribute("userNamesSelect", userNamesSelect);

			String sid = request.getParameter("id");
			if (StringUtils.isNotBlank(sid)) {
				Integer id = Integer.parseInt(sid);
				Linktouch linktouch = linktouchService.findLinktouchById(id);
				BeanUtils.copyProperties(linktouchForm, linktouch);
				// 处理联系人
				if (linktouch.getLinkman() != null) {
					linktouchForm.setLinkmanId(linktouch.getLinkman().getId()
							+ "");
				}
				// 处理业务员
				if (linktouch.getUserName() != null) {
					SysUser sysUser = sysUserService
							.findSysUserByCnname(linktouch.getUserName());
					linktouchForm.setUserNameId(sysUser.getId() + "");
				}
				// 处理所属人
				if (linktouch.getSysUser() != null) {
					linktouchForm.setSysUserId(linktouch.getSysUser().getId()
							+ "");
				}
				return "edit";
			}
		}
		return null;
	}

	/**
	 * 保存联系记录
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linktouch", privilege = "save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {
		Linktouch linktouch = new Linktouch();
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		BeanUtils.copyProperties(linktouch, linktouchForm);

		// 处理所属人
		String suid = linktouchForm.getSysUserId();
		if (StringUtils.isNotBlank(suid)) {
			Integer uid = Integer.parseInt(suid);
			SysUser sysUser = sysUserService.findSysUserById(uid);
			linktouch.setSysUser(sysUser);
		}

		// 处理联系人
		String slid = linktouchForm.getLinkmanId();
		if (StringUtils.isNotBlank(slid)) {
			Integer lid = Integer.parseInt(slid);
			Linkman linkman = linkmanService.findLinkmanById(lid);
			linktouch.setLinkman(linkman);
		}

		// 处理业务员
		String suserid = linktouchForm.getUserNameId();
		if (StringUtils.isNotBlank(suserid)) {
			Integer userid = Integer.parseInt(suserid);
			SysUser user = sysUserService.findSysUserById(userid);
			linktouch.setUserName(user.getCnname());
		}
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			// 设置分配给所属人日期
			linktouch.setDispenseDate(linktouchForm.getCreateTime());
			linktouch.setSysUser(curSysuser);
			linktouchService.saveLinktouch(curSysuser, linktouch);
			return "listAction";
		}
		return null;
	}

	/**
	 * 新增联系记录
	 * 
	 * @return
	 */
	@Limit(module = "linktouch", privilege = "add")
	public String add() {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		List<Company> companys = companyService.findMyOwnCompanys(curSysuser);
		List<Linkman> linkmansSelect = new ArrayList<Linkman>();
		if (curSysuser != null && companys != null) {
			// 联系人下拉选,查出所有属于自己的联系人
			for (int i = 0; i < companys.size(); i++) {
				linkmansSelect.addAll(linkmanService.findLinkmanByComp(
						curSysuser, companys.get(i)));
			}
			request.setAttribute("linkmansSelect", linkmansSelect);
			// 联系方式下拉选
			List<SysDictionaryType> linkFashionsSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKFASHION);
			request.setAttribute("linkFashionsSelect", linkFashionsSelect);
			// 联系类别下拉选
			List<SysDictionaryType> linkTypesSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKTYPE);
			request.setAttribute("linkTypesSelect", linkTypesSelect);

			// 处理业务员下拉选
			List<SysUser> userNamesSelect = sysUserService.findAllSysUsers();
			request.setAttribute("userNamesSelect", userNamesSelect);

			// 处理创建人创建时间修改人修改时间等
			if (curSysuser != null) {
				// 处理特殊
				linktouchForm.setCreater(curSysuser.getCnname());
				linktouchForm.setUpdater(curSysuser.getCnname());
				linktouchForm.setDispensePerson(curSysuser.getCnname());
				linktouchForm.setUserNameId(curSysuser.getId() + "");
				String curDate = DateFormatUtils.format(new Date(),
						"yyyy-MM-dd HH:mm:ss");
				linktouchForm.setCreateTime(curDate);
				linktouchForm.setUpdateTime(curDate);
				return "add";
			}
		}
		return null;
	}

	/**
	 * 联系记录查看列表
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linktouch", privilege = "list")
	public String list() throws IllegalAccessException,
			InvocationTargetException {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		List<Company> companys = companyService.findMyOwnCompanys(curSysuser);
		List<Linkman> linkmansSelect = new ArrayList<Linkman>();
		if (curSysuser != null && companys != null) {
			// 联系人下拉选,查出所有属于自己的联系人
			for (int i = 0; i < companys.size(); i++) {
				linkmansSelect.addAll(linkmanService.findLinkmanByComp(
						curSysuser, companys.get(i)));
			}
			request.setAttribute("linkmansSelect", linkmansSelect);
			// 联系方式下拉选
			List<SysDictionaryType> linkFashionsSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKFASHION);
			request.setAttribute("linkFashionsSelect", linkFashionsSelect);
			// 联系类别下拉选
			List<SysDictionaryType> linkTypesSelect = sysDictionaryTypeService
					.findSysDictionaryTypeByCode(Global.LINKTYPE);
			request.setAttribute("linkTypesSelect", linkTypesSelect);
			// 处理业务员下拉选
			List<SysUser> userNamesSelect = sysUserService.findAllSysUsers();
			request.setAttribute("userNamesSelect", userNamesSelect);

			LinktouchSearch linktouchSearch = new LinktouchSearch();
			BeanUtils.copyProperties(linktouchSearch, linktouchForm);
			linktouchSearch.setLinkmanId(linktouchForm.getLinkmanId());
			linktouchSearch.setUserNameId(linktouchForm.getUserNameId());
			// 查出属于自己的联系记录
			List<Linktouch> linktouchs = linktouchService
					.findMyOwnLinktouchsByCondition(curSysuser, linktouchSearch);
			for(int i=0;i<linktouchs.size();i++){
				if(linktouchs.get(i).getContent().length()>40){
					String content = linktouchs.get(i).getContent().substring(0, 39)+"...";
					linktouchs.get(i).setContent(content);
				}
			}
			request.setAttribute("linktouchs", linktouchs);
			return "list";
		}
		return null;
	}

	@Override
	public LinktouchForm getModel() {
		// TODO Auto-generated method stub
		return linktouchForm;
	}

}
