package cn.gaily.crm.dao.impl;


import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.CityDao;
import cn.gaily.crm.domain.City;

@Repository(value="cityDao")
public class CityDaoImpl extends CommonDaoImpl<City> implements CityDao {

}
