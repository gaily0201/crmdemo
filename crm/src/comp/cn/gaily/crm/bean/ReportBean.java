package cn.gaily.crm.bean;

public class ReportBean {

	private String type;
	private Long count;
	
	public ReportBean() {
	}
	public ReportBean(String type, Long count) {
		super();
		this.type = type;
		this.count = count;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getCount() {
		return count;
	}
}
