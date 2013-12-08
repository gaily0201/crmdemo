package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.bean.ReportBean;

public interface ReportService {

	/**
	 * 获取所有的报表需要数据的bean
	 * @return
	 */
	List<ReportBean> findReportBeans();

}
