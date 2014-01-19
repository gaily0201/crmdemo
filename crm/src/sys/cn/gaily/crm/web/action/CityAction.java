package cn.gaily.crm.web.action;

import cn.gaily.crm.annotation.Limit;

public class CityAction extends BaseAction {

	/**
	 * 列出省份
	 * @return
	 */
//	@Limit(module="city",privilege="list")
	public String list(){
		
		return "list";
	}
}
