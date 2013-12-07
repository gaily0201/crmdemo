package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.ProvinceDao;
import cn.gaily.crm.domain.Province;
import cn.gaily.crm.service.ProvinceService;

@Service(value="provinceService")
@Transactional(readOnly=true)
public class ProvinceServiceImpl implements ProvinceService {

	@Resource(name="provinceDao")
	private ProvinceDao provinceDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Province> findAllProvinces() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		return provinceDao.findObjectsByConditionWithNoPage(orderby);
	}

	@Override
	@Transactional(readOnly=true)
	public Province findProvinceByName(String name) {
		if(StringUtils.isNotBlank(name)){
			String whereHql  = " and o.name =?";
			Object[] params = {name};
			List<Province> list = provinceDao.findObjectsByConditionWithNoPage(whereHql, params);
			if(list!=null&&list.size()==1){
				return list.get(0);
			}
		}
		return null;
	}
}
