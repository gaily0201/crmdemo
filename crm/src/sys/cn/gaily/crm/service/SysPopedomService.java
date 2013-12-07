package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.SysPopedom;

public interface SysPopedomService {

	/**
	 * 获取操作权限表的所有数据
	 * @return
	 */
	List<SysPopedom> findAllPopedoms();

}
