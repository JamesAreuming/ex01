package com.yi.ex01;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class sqlSessionFactoryTest {
	
	@Autowired //자동으로 주입받겠다
	private SqlSessionFactory sqlFactory;
	
	
	@Test //test시 항상 써주기
	public void testSqlSessionFactory() {
		System.out.println("sqlFactory값 : "+ sqlFactory); // org.apache.ibatis.session.defaults.DefaultSqlSessionFactory@350aac89
	}
}
