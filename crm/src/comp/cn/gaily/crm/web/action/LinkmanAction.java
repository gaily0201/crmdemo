package cn.gaily.crm.web.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import com.opensymphony.xwork2.ModelDriven;
import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.CompanySearch;
import cn.gaily.crm.bean.LinkmanSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.CompanyService;
import cn.gaily.crm.service.LinkmanService;
import cn.gaily.crm.service.SysUserService;
import cn.gaily.crm.util.DataType;
import cn.gaily.crm.util.PingyinUtils;
import cn.gaily.crm.util.SQLDateConverter;
import cn.gaily.crm.util.SessionUtils;
import cn.gaily.crm.web.form.LinkmanForm;

public class LinkmanAction extends BaseAction implements
		ModelDriven<LinkmanForm> {

	private static final long serialVersionUID = 1L;
	private LinkmanForm linkmanForm = new LinkmanForm();

	private LinkmanService linkmanService = (LinkmanService) ServiceProvinder
			.getService("linkmanService");
	private CompanyService companyService = (CompanyService) ServiceProvinder
			.getService("companyService");
	private SysUserService sysUserService = (SysUserService) ServiceProvinder
			.getService("sysUserService");
	
	
	/**
	 * 按照公司来查询客户
	 * @return
	 */
	@Limit(module="linkman",privilege="listbyComp")
	public String listbyComp(){
		String scid = request.getParameter("cid");
		if(StringUtils.isNotBlank(scid)){
			Integer cid = Integer.parseInt(scid);
			List<Linkman> linkmans = linkmanService.findLinkmanByCompId(cid);
			request.setAttribute("linkmans", linkmans);
			return "listbyComp";
		}
		return null;
	}
	
	/**
	 * 删除联系人
	 * @return
	 */
	@Limit(module="linkman",privilege="delete")
	public String delete(){
		SysUser cursysUser = SessionUtils.getSysUserFromSession(request);
		String[] sids = request.getParameterValues("ids");
		if(sids!=null&&sids.length>0&&cursysUser!=null){
			Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
			linkmanService.deleteLinkmansByIds(cursysUser,ids);
			return "listAction";
		}
		return null;
	}
	
	/**
	 * 更新联系人信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linkman", privilege = "update")
	public String update() throws IllegalAccessException,
			InvocationTargetException {
		// 实例化po
		Linkman linkman = new Linkman();
		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		// vo-->po
		BeanUtils.copyProperties(linkman, linkmanForm);
		// 获取当前的登录用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if (curSysuser != null) {
			// 处理特殊
			// 1.更新更新人，更新日期
			linkman.setUpdater(curSysuser.getCnname());
			linkman.setUpdateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 处理联系人所有人的id,所属公司的id
			String suid = linkmanForm.getSysUserId();
			if(StringUtils.isNotBlank(suid)){
				SysUser sysUser = sysUserService.findSysUserById(Integer.parseInt(suid));
				linkman.setSysUser(sysUser);
			}
			String compid = linkmanForm.getCompanyId();
			if (StringUtils.isNotBlank(compid)) {
				Company company = companyService.findCompanyById(Integer.parseInt(compid));
				linkman.setCompany(company);
				linkmanService.updateLinkman(curSysuser, linkman);
				return "listAction";
			}
		}
		return null;
	}

	/**
	 * 编辑联系人信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linkman", privilege = "edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);

		// 处理联系人中的所属公司下拉选
		List<Company> companySelect = companyService
				.findMyOwnCompanys(curSysuser);
		request.setAttribute("companySelect", companySelect);
		// 获取联系人id
		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);
			Linkman linkman = linkmanService.findLinkmanById(id);
			BeanUtils.copyProperties(linkmanForm, linkman);
			//处理所属人
			if(linkman.getSysUser()!=null){
				linkmanForm.setSysUserId(linkman.getSysUser().getId()+"");
			}
			// 处理所属公司
			if (linkman.getCompany() != null) {
				linkmanForm.setCompanyId(linkman.getCompany().getId() + "");
			}
			request.setAttribute("linkman", linkman);
			return "edit";
		}
		return null;
	}

	/**
	 * 显示联系人页面
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linkman", privilege = "list")
	public String list() throws IllegalAccessException,
			InvocationTargetException {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);

		// 处理联系人中的所属公司下拉选
		List<Company> companySelect = companyService
				.findMyOwnCompanys(curSysuser);
		request.setAttribute("companySelect", companySelect);

		LinkmanSearch linkmanSearch = new LinkmanSearch();
		BeanUtils.copyProperties(linkmanSearch, linkmanForm);
		if (curSysuser != null) {
			List<Linkman> linkmans = linkmanService.findLinkmansByCondition(curSysuser, linkmanSearch);
			request.setAttribute("linkmans", linkmans);
			return "list";
		}
		return null;
	}

	/**
	 * 保存联系人信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "linkman", privilege = "save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {
		// 实例化po
		Linkman linkman = new Linkman();
		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		// vo-->po
		BeanUtils.copyProperties(linkman, linkmanForm);

		// 处理特殊
		// 处理联系人所有人的id,所属公司的id
		String compid = linkmanForm.getCompanyId();
		if (StringUtils.isNotBlank(compid)) {
			Company company = new Company();
			company.setId(Integer.parseInt(compid));
			linkman.setCompany(company);
		}

		String userid = linkmanForm.getSysUserId();
		if (StringUtils.isNotBlank(userid)) {
			SysUser sysUser = new SysUser();
			sysUser.setId(Integer.parseInt(userid));
			linkman.setSysUser(sysUser);

			// 设置分配给所属人日期
			linkman.setDispenseDate(linkmanForm.getCreateTime());
			linkman.setShareFlag('N');
			
			// 保存
			// 获取当前的登录用户
			SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
			if (curSysuser != null) {
				linkmanService.saveLinkman(curSysuser, linkman);
				return "listAction";
			}
		}
		return null;
	}

	/**
	 * 显示联系人添加页面
	 * 
	 * @return
	 */
	@Limit(module = "linkman", privilege = "add")
	public String add() {
		// 获取当前用户
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		// 处理联系人的编码
		String code = linkmanService.getLinkmanCodeByTabName("c_linkman");
		request.setAttribute("code", code);

		// 处理联系人中的所属公司下拉选
		List<Company> companySelect = companyService
				.findMyOwnCompanys(curSysuser);
		request.setAttribute("companySelect", companySelect);

		if (curSysuser != null) {
			// 处理特殊
			linkmanForm.setCreater(curSysuser.getCnname());
			linkmanForm.setUpdater(curSysuser.getCnname());
			linkmanForm.setDispensePerson(curSysuser.getCnname());
			linkmanForm.setSysUserId(curSysuser.getId() + "");
			String curDate = DateFormatUtils.format(new Date(),
					"yyyy-MM-dd HH:mm:ss");
			linkmanForm.setCreateTime(curDate);
			linkmanForm.setUpdateTime(curDate);
			return "add";
		}
		return null;
	}

	/**
	 * 联系人姓名生成拼音码
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
	public LinkmanForm getModel() {
		return linkmanForm;
	}

}
