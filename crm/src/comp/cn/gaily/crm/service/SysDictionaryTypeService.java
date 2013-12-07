package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.SysDictionaryType;

public interface SysDictionaryTypeService {

	/**
	 * 根据code名称查出所有的下拉选
	 * @param code
	 * @return
	 */
	List<SysDictionaryType> findSysDictionaryTypeByCode(String code);

}
