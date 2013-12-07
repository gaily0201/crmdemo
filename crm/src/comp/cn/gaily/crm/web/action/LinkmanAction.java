package cn.gaily.crm.web.action;

public class LinkmanAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 显示联系人页面
	 * @return
	 */
	public String list(){
		return "list";
	}
	
	/**
	 * 显示联系人添加页面
	 * @return
	 */
	public String add(){
		
		return "add";
	}
}
