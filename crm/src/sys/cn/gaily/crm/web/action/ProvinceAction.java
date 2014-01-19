package cn.gaily.crm.web.action;

import cn.gaily.crm.annotation.Limit;

public class ProvinceAction extends BaseAction {

	/**
	 * 列出省份信息
	 * @return
	 */
//	@Limit(module="province",privilege="list")
	public String list(){
		return "list";
	}
}
