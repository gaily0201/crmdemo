package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.City;

public interface CityService {

	/**
	 * 通过省的id获取该省的城市信息
	 * @param id
	 * @return
	 */
	List<City> findCitiesByPid(Integer id);

}
