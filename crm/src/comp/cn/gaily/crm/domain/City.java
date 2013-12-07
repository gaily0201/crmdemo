package cn.gaily.crm.domain;

@SuppressWarnings("serial")
public class City implements java.io.Serializable {
	/*
	 * CREATE TABLE  b_city(                                                                                                  
		   id  INT(11) NOT NULL AUTO_INCREMENT   PRIMARY KEY,      #城市编号                                                                          
		   name  VARCHAR(100) DEFAULT NULL,                        #城市名称                                                                
		   pycode  VARCHAR(50) DEFAULT NULL,                       #城市拼音码                                                             
		   pid  INT(11) DEFAULT NULL,                              #省编号                                                              
		   postcode  VARCHAR(50) DEFAULT NULL,                     #邮编                                                             
		   areacode  VARCHAR(50) DEFAULT NULL                      #区号 
		 );
	 */	
	private Integer id;
	private String name;
	private String pycode;
	private Integer pid;
	private String postcode;
	private String areacode;

	public City() {
	}

	public City(String name, String pycode, Integer pid, String postcode,
			String areacode) {
		this.name = name;
		this.pycode = pycode;
		this.pid = pid;
		this.postcode = postcode;
		this.areacode = areacode;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPycode() {
		return this.pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

}
