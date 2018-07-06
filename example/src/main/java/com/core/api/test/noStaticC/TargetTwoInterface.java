package com.core.api.test.noStaticC;

import java.util.List;

public interface TargetTwoInterface {

	 // 查询某些数据
	  public List<?> findSomething();
	  // 按照某种条件进行查询
	  public List<?> findSomethingByField(Integer field);
}
