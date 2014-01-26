package cn.gaily.crm.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import cn.gaily.crm.bean.ReportBean;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.service.ReportService;
import cn.gaily.crm.util.JFreeChartUtils;

public class ReportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ReportService reportService = (ReportService) ServiceProvinder.getService("reportService");
	
	/**
	 * 客户分类分析
	 * @return
	 * @throws IOException 
	 */
	public String khflfx() throws IOException{
		List<ReportBean> reportBeans = reportService.findReportBeans();
		request.setAttribute("reportBeans", reportBeans);
		
		//计算合计
		Long sum = 0L;
		if(reportBeans!=null&&reportBeans.size()>0){
			for(ReportBean r: reportBeans){
				sum=sum+r.getCount();
			}
		}
		request.setAttribute("sum", sum);
		
		//获取servletContext对象
		ServletContext sc= ServletActionContext.getServletContext();
		//生成图片
		JFreeChartUtils.generalBarJpeg(reportBeans,request, sc);
		return "khflfx";
	}
}
