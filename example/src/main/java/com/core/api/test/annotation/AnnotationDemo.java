package com.core.api.test.annotation;

import java.lang.reflect.Method;

public class AnnotationDemo {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object c = Class.forName("jarinstall.Student").newInstance();
		
		try{
			Method[] methods = c.getClass().getDeclaredMethods();
			if(methods != null && methods.length > 0){
				for(Method method : methods){
					if(method.isAnnotationPresent(Annotation.class)){
						Annotation anno = method.getAnnotation(Annotation.class);
						String type = String.valueOf(anno.type());
						String value = anno.value();
						if("INT".equals(type)){
							method.invoke(c, new Integer(value));
						}else{
							method.invoke(c, value);
						}
						
					}
				}
			}
			
			Student stu = (Student)c;
			System.out.println("name:"+ stu.getName() + ",age:" + stu.getAge() + ",studentId:" + stu.getStudentId());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
