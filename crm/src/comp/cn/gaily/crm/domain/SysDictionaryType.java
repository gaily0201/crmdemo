package cn.gaily.crm.domain;

@SuppressWarnings("serial")
public class SysDictionaryType implements java.io.Serializable {
	/*
	 * CREATE TABLE `sys_dictionary_type` (
		  `id` INT(11) NOT NULL AUTO_INCREMENT,             #编号
		  `sort` INT(11) DEFAULT NULL,                      #排序序号  该值初始值等id的值(上移 下移)********
		  `remark` TEXT,                                    #备注
		  `code`  VARCHAR(100) DEFAULT NULL,                 #类型唯一标识
		  `value` VARCHAR(200) DEFAULT NULL,                #细节名称例如(东北,华北),
		  `sysFlag` CHAR(1) DEFAULT NULL,                   #是否有效 'N'   'Y'
		   PRIMARY KEY  (`id`)
		)
	 */
	private Integer id;
	private Integer sort;
	private String remark;
	private String code; // 区分的唯一标识
	private String value;
	private Character sysFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Character getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(Character sysFlag) {
		this.sysFlag = sysFlag;
	}
}