package cn.gaily.crm.web.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.components.If;

import com.opensymphony.xwork2.ModelDriven;

import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.CompanySearch;
import cn.gaily.crm.bean.SysUserSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.City;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Province;
import cn.gaily.crm.domain.SysDictionaryType;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.CityService;
import cn.gaily.crm.service.CompanyService;
import cn.gaily.crm.service.ProvinceService;
import cn.gaily.crm.service.SysDictionaryTypeService;
import cn.gaily.crm.service.SysUserGroupService;
import cn.gaily.crm.service.SysUserService;
import cn.gaily.crm.util.DataType;
import cn.gaily.crm.util.Global;
import cn.gaily.crm.util.PingyinUtils;
import cn.gaily.crm.util.SQLDateConverter;
import cn.gaily.crm.util.SessionUtils;
import cn.gaily.crm.web.form.CompanyForm;

public class CompanyAction extends BaseAction implements
		ModelDriven<CompanyForm> {
	private static final long serialVersionUID = 1L;

	private CompanyForm companyForm = new CompanyForm();

	// 获取客户的业务层
	CompanyService companyService = (CompanyService) ServiceProvinder
			.getService("companyService");

	// 获取处理下拉选的业务层
	SysDictionaryTypeService sysDictionaryTypeService = (SysDictionaryTypeService) ServiceProvinder
			.getService("sysDictionaryTypeService");

	// 获取省业务层
	ProvinceService provinceService = (ProvinceService) ServiceProvinder
			.getService("provinceService");

	// 获取城市的业务层
	CityService cityService = (CityService) ServiceProvinder
			.getService("cityService");

	// 获取部门的业务层
	SysUserGroupService sysUserGroupService = (SysUserGroupService) ServiceProvinder
			.getService("sysUserGroupService");

	// 获取用户的业务层
	SysUserService sysUserService = (SysUserService) ServiceProvinder
			.getService("sysUserService");

	/**
	 * 已过期未联系的客户
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@Limit(module = "company", privilege = "datePassedLink")
	public String datePassedLink() throws IllegalAccessException,
			InvocationTargetException, ParseException {

		CompanySearch companySearch = new CompanySearch();
		BeanUtils.copyProperties(companySearch, companyForm);
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			List<Company> allcompanyList = companyService
					.findCompanyByCondition(curSysuser, companySearch);
			// 查询共享的客户
			List<Company> sharedList = companyService
					.findSharedCompany(curSysuser);
			if (sharedList != null && sharedList.size() > 0) {
				allcompanyList.addAll(sharedList);
			}

			List<Company> companyList = new ArrayList<Company>();

			// 所有的客户保存在companyList中,出去不是今天需要联系的
			for (int i = 0; i < allcompanyList.size(); i++) {
				Company company = allcompanyList.get(i);
				if (company.getNextTouchDate() == null) {
					companyList.add(company);
				}
				if (company.getNextTouchDate() != null) {
					java.sql.Date nextTouchDate = company.getNextTouchDate();
					if (0 > nextTouchDate.compareTo(DateUtils
							.parseDateStrictly(DateFormatUtils.format(
									new Date(), "yyyy-MM-dd"),
									new String[] { "yyyy-MM-dd" }))) {
						// 已过期未联系客户
						companyList.add(company);
						request.setAttribute("outofdateCompanyNum",
								companyList.size());
					}
				}
			}
			request.setAttribute("companyList", companyList);
			return "list";
		}
		return null;
	}

	/**
	 * 今日需要联系的客户
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "company", privilege = "todayNeedsLink")
	public String todayNeedsLink() throws IllegalAccessException,
			InvocationTargetException {

		CompanySearch companySearch = new CompanySearch();
		BeanUtils.copyProperties(companySearch, companyForm);
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			List<Company> allcompanyList = companyService
					.findCompanyByCondition(curSysuser, companySearch);
			// 查询共享的客户
			List<Company> sharedList = companyService
					.findSharedCompany(curSysuser);
			if (sharedList != null && sharedList.size() > 0) {
				allcompanyList.addAll(sharedList);
			}

			List<Company> companyList = new ArrayList<Company>();

			// 所有的客户保存在companyList中,出去不是今天需要联系的
			for (int i = 0; i < allcompanyList.size(); i++) {
				Company company = allcompanyList.get(i);
				if (company.getNextTouchDate() != null) {
					java.sql.Date nextTouchDate = company.getNextTouchDate();
					if (nextTouchDate.toString().equals(
							DateFormatUtils.format(new Date(), "yyyy-MM-dd"))) {
						companyList.add(company);
						// 把今日要联系的客户数目放在request，后期可放在主界面上。
						request.setAttribute("todayNeedsLinkCompanyNum",
								companyList.size());
					}
				}
			}
			request.setAttribute("companyList", companyList);
			return "list";
		}
		return null;
	}

	/**
	 * 经手人变更
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "changeHandler")
	public String changeHandler() {
		// 获取客户ids
		String sids = request.getParameter("ids");
		// 获取当前的登录用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (StringUtils.isNotBlank(sids) && curSysuser != null) {
			// 获取新的所有者
			String[] ids = sids.split(",");
			Integer[] id = DataType.converterStringArray2IntegerArray(ids);

			String snew_owner = request.getParameter("new_owner");
			if (StringUtils.isNotBlank(snew_owner)) {
				Integer new_owner = Integer.parseInt(snew_owner.trim());
				companyService.changeHandler(curSysuser, id, new_owner);
				return "changeHandler";
			}
		}
		return null;
	}

	/**
	 * 显示经手人 变更页面
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "showChangePerson")
	public String showChangePerson() {
		// 获取系统中所有的用户
		List<SysUser> sysUserSelect = sysUserService.findAllSysUsers();
		request.setAttribute("sysUserSelect", sysUserSelect);
		return "showChangePerson";
	}

	/**
	 * 变更下次联系时间
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "updateNextTouchTime")
	public String updateNextTouchTime() {
		// 获取客户Id
		String ssids = request.getParameter("ids");
		// 获取当前的登录用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (StringUtils.isNotBlank(ssids) && curSysuser != null) {
			String[] sids = ssids.split(",");
			Integer[] ids = DataType.converterStringArray2IntegerArray(sids);

			// 获取下次联系时间
			String snext_touch_date = request.getParameter("next_touch_date");
			java.sql.Date next_touch_date = java.sql.Date
					.valueOf(snext_touch_date);

			companyService
					.updateNextTouchTime(curSysuser, ids, next_touch_date);
			return "nextTouchTime";
		}
		return null;
	}

	/**
	 * 显示设置下次联系时间界面
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "showNextTouchTime")
	public String showNextTouchTime() {
		return "showNextTouchTime";
	}

	/**
	 * 查看共享
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "showShareViewOne")
	public String showShareViewOne() {
		// 获取客户id
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);
			Company company = companyService.findCompanyById(id);
			request.setAttribute("company", company);

			// 获取该用户共享的用户信息
			List<SysUser> sysUsers = companyService.findSysUserBySharedIds(id);
			request.setAttribute("sysUsers", sysUsers);

			return "showShareViewOne";
		}
		return null;
	}

	/**
	 * 取消共享设置
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "updateshareCancelOne")
	public String updateshareCancelOne() {
		String sid = request.getParameter("id");
		// 获取当前的登录用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (StringUtils.isNotBlank(sid) && curSysuser != null) {
			Integer id = Integer.parseInt(sid);
			// 获取模块名称
			String s_module = request.getParameter("s_module");
			if (StringUtils.isNotBlank(s_module)) {
				companyService.updateShareCancelOne(curSysuser, id, s_module);
				return "updateshareCancelOne";
			}
		}
		return null;
	}

	/**
	 * 取消共享页面
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "showShareCancelOne")
	public String showShareCancelOne() {
		// 获取客户id
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);
			Company company = companyService.findCompanyById(id);
			request.setAttribute("company", company);
			return "showShareCancelOne";
		}
		return null;
	}

	/**
	 * 修改共享
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "updateShareSetOne")
	public String updateShareSetOne() {

		// 获取客户id
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);
			// 获取模块名称
			String s_module = request.getParameter("s_module");
			if (StringUtils.isNotBlank(s_module)) {
				// 获取用户id
				String[] suids = request.getParameterValues("uid");
				if (suids != null && suids.length > 0) {
					Integer[] uids = DataType
							.converterStringArray2IntegerArray(suids);

					String sharetype = request.getParameter("sharetype");
					if (StringUtils.isNotBlank(sharetype)) {
						// 获取当前的登录用户
						SysUser curSysuser = SessionUtils
								.getSysUserFromSession(request);
						if (curSysuser != null) {
							if ("add".equals(sharetype)) { // 增加共享
								companyService.addUpdateShareSetOne(curSysuser,
										s_module, id, uids);
							}
							if ("minus".equals(sharetype)) { // 减少共享
								companyService.minusUpdateShareSetOne(
										curSysuser, s_module, id, uids);
							}
						}
					}
					return "updateShareSetOne";
				}
			}
		}
		return null;
	}

	/**
	 * 打开共享页面
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "showShareSetOne")
	public String showShareSetOne() {
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);
			Company company = companyService.findCompanyById(id);
			request.setAttribute("company", company);

			// 获取部门信息
			List<SysUserGroup> sysUserGroups = sysUserGroupService
					.findAllSysGroups();
			request.setAttribute("sysUserGroups", sysUserGroups);

			List<SysUser> sysUsers = sysUserService.findAllSysUsers();
			request.setAttribute("sysUsers", sysUsers);

			return "showShareSetOne";
		}
		return null;
	}

	/**
	 * 删除客户
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "delete")
	public String delete() {
		String[] sids = request.getParameterValues("ids");
		if (sids != null && sids.length > 0) {
			Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
			// 获取当前的登录用户
			SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
			if (curSysuser != null) {
				companyService.deleteCompanyByIds(curSysuser, ids);
				return "listAction";
			}
		}
		return null;
	}

	/**
	 * 更新客户信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "company", privilege = "update")
	public String update() throws IllegalAccessException,
			InvocationTargetException {

		// 实例化po
		Company company = new Company();
		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		// vo-->po
		BeanUtils.copyProperties(company, companyForm);
		// 处理特殊
		// 处理客户所有人的id
		String userid = companyForm.getOwnerUser();
		if (StringUtils.isNotBlank(userid)) {
			SysUser sysUser = new SysUser();
			sysUser.setId(Integer.parseInt(userid));
			company.setSysUser(sysUser);
			company.setShareFlag('N');

			// 获取当前的登录用户
			SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
			if (curSysuser != null) {
				companyService.updateCompany(curSysuser, company);
				return "listAction";
			}
		}
		return null;
	}

	/**
	 * 修改客户信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "company", privilege = "edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {
		// 处理客户信息添加的下拉选
		// 处理客户等级的下拉选
		List<SysDictionaryType> gradesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);

		// 处理区域名称下拉选
		List<SysDictionaryType> regionNamesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.REGIONNAME);
		request.setAttribute("regionNamesSelect", regionNamesSelect);

		// 处理客户来源下拉选
		List<SysDictionaryType> sourcesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.SOURCE);
		request.setAttribute("sourcesSelect", sourcesSelect);

		// 处理所属行业下拉选
		List<SysDictionaryType> tradesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.TRADE);
		request.setAttribute("tradesSelect", tradesSelect);

		// 处理公司规模下拉选
		List<SysDictionaryType> scalesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.SCALE);
		request.setAttribute("scalesSelect", scalesSelect);

		// 处理客户性质下拉选
		List<SysDictionaryType> qualitysSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.QUALITY);
		request.setAttribute("qualitysSelect", qualitysSelect);

		// 处理企业性质下拉选
		List<SysDictionaryType> kindsSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.KIND);
		request.setAttribute("kindsSelect", kindsSelect);
		
		// 处理经营范围下拉选
		List<SysDictionaryType> dealinsSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.DEALIN);
		request.setAttribute("dealinsSelect", dealinsSelect);
		// 获取所有的省的信息
		List<Province> provincesSelect = provinceService.findAllProvinces();
		request.setAttribute("provincesSelect", provincesSelect);

		// 获取客户id
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			// 获得客户的id，通过id获得客户信息
			Integer id = Integer.parseInt(sid);
			Company company = companyService.findCompanyById(id);
			// 将客户信息放到栈顶位置
			BeanUtils.copyProperties(companyForm, company);

			// 特殊处理 城市
			String pname = company.getProvince();
			// 通过省的名称获得省的id
			Province province = provinceService.findProvinceByName(pname);
			if (province != null) {
				// 通过省的id查询该省对应的城市信息
				List<City> citiesSelect = cityService.findCitiesByPid(province
						.getId());
				request.setAttribute("citiesSelect", citiesSelect);
			}
			// 处理所属人id
			if (company.getSysUser() != null) {
				companyForm.setOwnerUser(company.getSysUser().getId() + "");
			}
			request.setAttribute("company", company);
			return "edit";
		}
		return null;
	}

	/**
	 * 显示客户拜访页面
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "company", privilege = "list")
	public String list() throws IllegalAccessException,
			InvocationTargetException {

		// 处理查询的下拉选
		// 客户等级 客户来源 客户性质
		// 处理客户等级下拉选
		List<SysDictionaryType> gradesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);
		// 处理客户来源下拉选
		List<SysDictionaryType> sourcesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.SOURCE);
		request.setAttribute("sourcesSelect", sourcesSelect);
		// 处理客户性质下拉选
		List<SysDictionaryType> qualitySelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.QUALITY);
		request.setAttribute("qualitySelect", qualitySelect);
		// 处理经营范围下拉选
		List<SysDictionaryType> dealinsSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.DEALIN);
		request.setAttribute("dealinsSelect", dealinsSelect);

		CompanySearch companySearch = new CompanySearch();
		BeanUtils.copyProperties(companySearch, companyForm);

		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			List<Company> companyList = companyService.findCompanyByCondition(
					curSysuser, companySearch);
			// 查询共享的客户
			List<Company> sharedList = companyService
					.findSharedCompany(curSysuser);
			if (sharedList != null && sharedList.size() > 0) {
				companyList.addAll(sharedList);
			}
			request.setAttribute("companyList", companyList);
			return "list";
		}
		return null;
	}

	/**
	 * 保存客户
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "company", privilege = "save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {
		// 实例化po
		Company company = new Company();
		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		// vo-->po
		BeanUtils.copyProperties(company, companyForm);
		// 处理特殊
		// 处理客户所有人的id
		String userid = companyForm.getOwnerUser();
		if (StringUtils.isNotBlank(userid)) {
			SysUser sysUser = new SysUser();
			sysUser.setId(Integer.parseInt(userid));
			company.setSysUser(sysUser);

			// 设置分配给所属人日期
			company.setDispenseDate(companyForm.getCreateTime());
			company.setShareFlag('N');

			// 保存
			// 获取当前的登录用户
			SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
			if (curSysuser != null) {
				companyService.saveCompany(curSysuser, company);
				return "listAction";
			}
		}
		return null;
	}

	/**
	 * 显示客户添加页面
	 * 
	 * @return
	 */
	@Limit(module = "company", privilege = "add")
	public String add() {
		// 处理客户的编码
		String code = companyService.getCompanyCodeByTabName("c_company");
		request.setAttribute("code", code);

		// 处理客户信息添加的下拉选
		// 处理客户等级的下拉选
		List<SysDictionaryType> gradesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);

		// 处理区域名称下拉选
		List<SysDictionaryType> regionNamesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.REGIONNAME);
		request.setAttribute("regionNamesSelect", regionNamesSelect);

		// 处理客户来源下拉选
		List<SysDictionaryType> sourcesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.SOURCE);
		request.setAttribute("sourcesSelect", sourcesSelect);

		// 处理所属行业下拉选
		List<SysDictionaryType> tradesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.TRADE);
		request.setAttribute("tradesSelect", tradesSelect);

		// 处理公司规模下拉选
		List<SysDictionaryType> scalesSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.SCALE);
		request.setAttribute("scalesSelect", scalesSelect);

		// 处理客户性质下拉选
		List<SysDictionaryType> qualitysSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.QUALITY);
		request.setAttribute("qualitysSelect", qualitysSelect);

		// 处理企业性质下拉选
		List<SysDictionaryType> kindsSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.KIND);
		request.setAttribute("kindsSelect", kindsSelect);
		// 处理经营范围下拉选
		List<SysDictionaryType> dealinsSelect = sysDictionaryTypeService
				.findSysDictionaryTypeByCode(Global.DEALIN);
		request.setAttribute("dealinsSelect", dealinsSelect);
		// 获取所有的省的信息
		List<Province> provincesSelect = provinceService.findAllProvinces();
		request.setAttribute("provincesSelect", provincesSelect);

		// 获取当前登陆用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			// 处理创建人 修改人 所属人 所属人id 创建日期 修改日期
			companyForm.setCreater(curSysuser.getCnname());
			companyForm.setUpdater(curSysuser.getCnname());
			companyForm.setDispensePerson(curSysuser.getCnname());
			companyForm.setOwnerUser(curSysuser.getId() + "");
			String curDate = DateFormatUtils.format(new Date(),
					"yyyy-MM-dd HH:mm:ss");
			companyForm.setCreateTime(curDate);
			companyForm.setUpdateTime(curDate);

			return "add";
		}
		return null;
	}

	/**
	 * 显示省下对应的城市
	 * 
	 * @return
	 * @throws IOException
	 */
	@Limit(module = "company", privilege = "showCity")
	public String showCity() throws IOException {
		// 获取省的名称
		String name = request.getParameter("name");
		if (StringUtils.isNotBlank(name)) {
			// 通过省的名称获得省的id
			Province province = provinceService.findProvinceByName(name);
			if (province != null) {
				// 通过省的id查询该省对应的城市信息
				List<City> citiesSelect = cityService.findCitiesByPid(province
						.getId());
				// request.setAttribute("citiesSelect", citiesSelect);

				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "id", "pycode", "pid",
						"postcode", "areacode" });

				JSONArray jsonArray = JSONArray
						.fromObject(citiesSelect, config);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jsonArray.toString());
			}
		}
		return null;
	}

	/**
	 * 客户名称生成拼音码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String pinyin() throws IOException {
		String name = request.getParameter("name");
		if (StringUtils.isNotBlank(name)) {
			String pin = PingyinUtils.converterToFirstSpell(name);
			response.getWriter().print(pin);
		}
		return null;
	}

	@Override
	public CompanyForm getModel() {
		return companyForm;
	}
}
