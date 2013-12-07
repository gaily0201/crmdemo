package cn.gaily.crm.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

public class SQLDateConverter implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		// TODO Auto-generated method stub
		if(value == null){
			return null;
		}
		if(type==null){
			return null;
		}
		if(type!=java.sql.Date.class){
			return null;
		}
		
		if(value instanceof java.lang.String){
			String str = (String) value;
			if(StringUtils.isNotBlank(str)){
				return java.sql.Date.valueOf((String)value);
			}
		}
		
		return null;
	}

	
}
