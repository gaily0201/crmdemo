package cn.gaily.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.gaily.crm.dao.CompanyDao;
import cn.gaily.crm.domain.Company;

@Repository(value="companyDao")
public class CompanyDaoImpl extends CommonDaoImpl<Company> implements CompanyDao {


}
