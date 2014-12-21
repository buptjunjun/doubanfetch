package org.junjun.douban.utils;

import java.io.FileReader;
import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	static private SqlSessionFactory sessionFactory = null;
	public static SqlSessionFactory getSessionFactory() 
	 {
		 
		  String resource = "src/conf/mybaits/configuration.xml";
		  try {
		   sessionFactory = new SqlSessionFactoryBuilder().build(new FileReader(resource));
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
	  return sessionFactory;
	 }
}
