package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.CityDao;
import cn.gaily.crm.domain.City;
import cn.gaily.crm.service.CityService;

@Service(value="cityService")
@Transactional(readOnly=true)
public class CityServiceImpl implements CityService {

	@Resource(name="cityDao")
	private CityDao cityDao;

	@Override
	@Transactional(readOnly=true)
	public List<City> findCitiesByPid(Integer id) {
		if(id!=null){
			String whereHql = " and o.pid=?";
			Object[] params = {id};
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.id", "asc");
			return cityDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);
		}
		return null;
	}
}
