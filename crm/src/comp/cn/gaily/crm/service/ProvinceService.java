package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.Province;

public interface ProvinceService {

	/**
	 * 获取所有的省
	 * @return
	 */
	List<Province> findAllProvinces();

	/**
	 * 通过省的名称来获取省的对象
	 * @param name
	 * @return
	 */
	Province findProvinceByName(String name);

}
