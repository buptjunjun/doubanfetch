package org.junjun.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junjun.douban.bean.DoubanSubject;
import org.junjun.douban.mapper.DoubanSubjectMapper;
/**
 * myBatis数据库测试
 *
 */
public class TestMybatis {
 /**
  * 获得MyBatis SqlSessionFactory
  * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句，commit，rollback，close等方法。
  * @return
  */
 private static SqlSessionFactory getSessionFactory() 
 {
	  SqlSessionFactory sessionFactory = null;
	  String resource = "src/conf/mybaits/configuration.xml";
	  try {
	   sessionFactory = new SqlSessionFactoryBuilder().build(new FileReader(resource));
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
  return sessionFactory;
 }
 
 public static final void main(String[] args) {
  SqlSession sqlSession = getSessionFactory().openSession();
  DoubanSubjectMapper userMapper = sqlSession.getMapper(DoubanSubjectMapper.class);
  
 
  // test select
  DoubanSubject ds = userMapper.findById(333);
  System.out.println(ds);
 
  // test insert
  DoubanSubject ds1 = new DoubanSubject();
  ds1.setId(12);
  ds1.setFetchCount(11);
  ds1.setType(2);
  ds1.setAssociatedId("11,22,d33");
  userMapper.updateoubanSubject(ds1); 
  //it is a must or no data will be insert into server.
  
  List<DoubanSubject> dses = userMapper.findDoubanSubjectByFetchCount(0, 3);
  
  System.out.println(dses);
  sqlSession.commit();
 
 }
}
