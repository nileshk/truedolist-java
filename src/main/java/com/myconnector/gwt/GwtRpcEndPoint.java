package com.myconnector.gwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Solution from: http://blog.digitalascent.com/2007/11/gwt-rpc-with-spring-2x_12.html
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface GwtRpcEndPoint {
	String value() default "";
}