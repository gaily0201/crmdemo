package cn.gaily.crm.web.action;

import cn.gaily.crm.annotation.Limit;

public class LinktouchAction extends BaseAction {

	
	/**
	 * 新增联系记录
	 * @return
	 */
	@Limit(module="linktouch",privilege="add")
	public String add(){
		return "add";
	}
	
	/**
	 * 联系记录查看列表
	 * @return
	 */
	@Limit(module="linktouch",privilege="list")
	public String list(){
		return "list";
	}
}
