package cn.gaily.crm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解
 * @author Administrator
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

	String module();
	String privilege();
}
