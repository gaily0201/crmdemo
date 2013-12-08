package cn.gaily.crm.dao;

import java.util.List;

import cn.gaily.crm.bean.ReportBean;

public interface ReportDao extends CommonDao<ReportBean> {

	/**
	 * 查询报表的实体
	 * @return
	 */
	List<ReportBean> findReportBeans();

}
