package com.core.api.test.dubbo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.core.api.test.dubbo.service.MyService;

/**
 * SPI:Service Provider Interface
 * 
 * 实际上，从JDK1.5版本开始，您无需使用new关键字指定具体的实现类。
 * 您可以在META-INF/searvices文件夹下建立一个名叫xxxx.BusinessInterface的文件（注意xxxx代表您的包名，整个文件名与BusinessInterface接口的完整类名相同），
 * 然后在文件内容中书写“xxxxx.RealBusinessImpl”（注意是完整BusinessInterface接口实现类的名字）。
 * 保存这个文件后，您就可以通过JDK提供的java.util.ServiceLoader工具类实例化这个接口了
 * @author liugh
 *
 */
public class ServiceLoaderMain {

	public static void main(String[] args) {
		ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class);
		
		Iterator<MyService> services = loader.iterator();
		
		if( services.hasNext() ){
			MyService myService = services.next();
			System.out.println(myService.doMyTest("hello,", "world"));
		}
		
	}
}
