package cn.gaily.crm.domain;

@SuppressWarnings("serial")
public class Province implements java.io.Serializable {

	private Integer id;
	private String name;
	private String pycode;

	public Province() {
	}

	public Province(String name, String pycode) {
		this.name = name;
		this.pycode = pycode;
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

}
