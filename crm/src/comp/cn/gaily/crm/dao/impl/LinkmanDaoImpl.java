package cn.gaily.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.gaily.crm.dao.LinkmanDao;
import cn.gaily.crm.domain.Linkman;

@Repository(value="linkmanDao")
public class LinkmanDaoImpl extends CommonDaoImpl<Linkman> implements LinkmanDao {

}
