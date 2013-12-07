package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.SysPopedomDao;
import cn.gaily.crm.domain.SysPopedom;
import cn.gaily.crm.service.SysPopedomService;

@Transactional(readOnly=true)
@Service(value="sysPopedomService")
public class SysPopedomServiceImpl implements SysPopedomService {

	@Resource(name="sysPopedomDao")
	private SysPopedomDao sysPopedomDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<SysPopedom> findAllPopedoms() {

		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.sort", "asc");
		
		return sysPopedomDao.findObjectsByConditionWithNoPage(orderby);

	}

}
