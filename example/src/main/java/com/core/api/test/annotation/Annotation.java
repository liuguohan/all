package com.core.api.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation {
	
	enum fieldType{
		INT,STRING
	}
	
	fieldType type();
	
	String value();

}
