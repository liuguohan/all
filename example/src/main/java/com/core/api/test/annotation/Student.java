package com.core.api.test.annotation;

import com.core.api.test.annotation.Annotation.fieldType;

public class Student {

	String name;
	
	int age;
	
	int studentId;

	public String getName() {
		return name;
	}

	@Annotation(type=fieldType.STRING,value="liugh")
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	@Annotation(type=fieldType.INT,value="18")
	public void setAge(int age) {
		this.age = age;
	}

	public int getStudentId() {
		return studentId;
	}

	@Annotation(type=fieldType.INT,value="1")
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
