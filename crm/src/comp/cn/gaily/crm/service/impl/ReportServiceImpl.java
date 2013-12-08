package cn.gaily.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.ReportBean;
import cn.gaily.crm.dao.ReportDao;
import cn.gaily.crm.service.ReportService;

@Transactional(readOnly=true)
@Service(value="reportService")
public class ReportServiceImpl implements ReportService{

	@Resource(name="reportDao")
	private ReportDao reportDao;
	
	@Override
	public List<ReportBean> findReportBeans() {
		List<ReportBean> list = reportDao.findReportBeans();
		return list;
	}

}
