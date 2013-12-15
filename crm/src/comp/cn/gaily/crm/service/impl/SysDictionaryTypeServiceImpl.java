package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.SysDictionaryTypeDao;
import cn.gaily.crm.domain.SysDictionaryType;
import cn.gaily.crm.service.SysDictionaryTypeService;

@Transactional(readOnly=true)
@Service(value="sysDictionaryTypeService")
public class SysDictionaryTypeServiceImpl implements SysDictionaryTypeService{

	
	@Resource(name="sysDictionaryTypeDao")
	private SysDictionaryTypeDao sysDictionaryTypeDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<SysDictionaryType> findSysDictionaryTypeByCode(String code) {
		if(StringUtils.isNotBlank(code)){
			String whereHql = " and o.code=?";
			Object[] params = {code};
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.sort", "asc");
			return sysDictionaryTypeDao.findObjectsByConditionWithNoPageCache(whereHql, params,orderby);
		}
		return null;
	}

	
}
