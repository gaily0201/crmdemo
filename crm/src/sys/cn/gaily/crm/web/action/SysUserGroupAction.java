package cn.gaily.crm.web.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.SysUserGroupSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysUserGroupService;
import cn.gaily.crm.util.DataType;
import cn.gaily.crm.web.form.SysUserGroupForm;
import com.opensymphony.xwork2.ModelDriven;

public class SysUserGroupAction extends BaseAction implements ModelDriven<SysUserGroupForm> {
	
	private static final long serialVersionUID = 1L;
	
	protected SysUserGroupForm sysUserGroupForm = new SysUserGroupForm();
	
	private SysUserGroupService sysUserGroupService = (SysUserGroupService) ServiceProvinder
			.getService("sysUserGroupService");

	/**
	 * 保存部门信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Limit(module="group",privilege="save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {

		// 实例化po对象
		SysUserGroup sysUserGroup = new SysUserGroup();

		// 赋值vo对象到po中
		BeanUtils.copyProperties(sysUserGroup, sysUserGroupForm);

		// 获取业务层对象(本项目struts2和spring分离的)
		/*
		 * SysUserGroupService sysUserGroupService = (SysUserGroupService)
		 * ServiceProvinder.getService("sysUserGroupService");
		 */
		// 调用业务层保存
		sysUserGroupService.saveUserGroup(sysUserGroup);
		return "listAction";
	}

	/**
	 * 显示部门信息查询页面
	 * 
	 * @return
	 */
	@Limit(module="group",privilege="list")
	public String list() {
		// 实例化封装查询条件的javabean
		SysUserGroupSearch sysUserGroupSearch = new SysUserGroupSearch();
		
		sysUserGroupSearch.setName(sysUserGroupForm.getName());

		List<SysUserGroup> sysUserGroups = sysUserGroupService.findSysUserGroups(sysUserGroupSearch);

		request.setAttribute("sysUserGroups", sysUserGroups);

		return "list";
	}

	/**
	 * 显示部门添加页面
	 * 
	 * @return
	 */
	@Limit(module="group",privilege="add")
	public String add() {
		return "add";
	}

	/**
	 * 显示部门信息修改页面
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module="group",privilege="edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {

		// 获取部门id
		String sid = sysUserGroupForm.getId();
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);

			// 调用业务层方法，通过部门id查询部门信息
			SysUserGroup sysUserGroup = sysUserGroupService
					.findSysUserGroupById(id);

			// 处理部门编辑页面显示要编辑的信息，放在湛顶
			BeanUtils.copyProperties(sysUserGroupForm, sysUserGroup);

			return "edit";
		}

		return "listAction";
	}

	/**
	 * 修改部门信息
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module="group",privilege="update")
	public String update() throws IllegalAccessException, InvocationTargetException {
		// 实例化po对象
		SysUserGroup sysUserGroup = new SysUserGroup();

		// 赋值vo对象到po中
		BeanUtils.copyProperties(sysUserGroup, sysUserGroupForm);

		// 调用业务层保存
		sysUserGroupService.updateUserGroup(sysUserGroup);
		return "listAction";
	}

	/**
	 * 删除部门信息
	 * @return
	 */
	@Limit(module="group",privilege="delete")
	public String delete(){
		
		//获取部门的id
		String[] sids = request.getParameterValues("ids");
		Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
		if(ids!=null){
			sysUserGroupService.deleteSysUserGroupsByIds(ids);
		}
		return "listAction";
	}
	
	@Override
	public SysUserGroupForm getModel() {
		// TODO Auto-generated method stub
		return sysUserGroupForm;
	}
}
