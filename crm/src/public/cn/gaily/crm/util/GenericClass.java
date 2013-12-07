package cn.gaily.crm.util;

import java.lang.reflect.ParameterizedType;

public class GenericClass {

	public static Class getGenericClass(Class clazz) {
		// TODO Auto-generated method stub
		
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];
		
		return entityClass;
	}

}
