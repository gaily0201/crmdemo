package cn.gaily.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.ProvinceDao;
import cn.gaily.crm.domain.Province;
import cn.gaily.crm.service.ProvinceService;

@Repository(value="provinceDao")
public class ProvinceDaoImpl extends CommonDaoImpl<Province> implements ProvinceDao {

}
