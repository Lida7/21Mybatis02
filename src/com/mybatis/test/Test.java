package com.mybatis.test;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.vo.Teacher;
import com.mybatis.vo.User;

public class Test {
	
	/**
	 * MyBatis的嵌套结果/嵌套查询（网上有说这是叫延迟加载）
	 * 
	 * 1、一对一：
	 * 		1.1 一对一嵌套结果：实际就是将两个表join起来（参考userMapper.xml的selectUserById方法）
	 * 			得出多行数据，其中，需要用resultMap返回，
	 * 		配置resultMap
	 * 		<resultMap type="User" id="selectUserMap" autoMapping="true"></resultMap>
	 * 		type：返回的类型的全类名/配置别名 
	 * 		autoMapping：自动对应字段，如果不写，则需要手动映射起来
	 * 					方法是加入<id column="xxx" property="xxx" />/<result column="xxx" property="xxx"/>
	 * 		
	 * 		1.2 通过 <association> 标签，配置返回的一对一的bean（入User中对应一个Teacher），association中则需要配置Teacher的class
	 * 			例如：<association property="teacher" javaType="Teacher" autoMapping="true"><id column="teacherId" property="id"/><association/>
	 * 
	 * 
	 * 		1.3一对一嵌套查询：实际是做两次查询（参考userMapper.xml的selectSingleUserById）
	 * 			和嵌套结果类似：只不过配置association时，需要传入外键，和填写select的语句的id（参考selectSingleUser方法）
	 * 			例如：<association javaType="Teacher" property="teacher" column="teacherId" select="selectSingleUser"  autoMapping="true"></association>
	 * 			其中需要提及一下的是column 为表的外键的列名
	 * 
	 * 
	 * 2、一对多
	 *		2.1 一对多嵌套结果：跟一对一嵌套结果类似，区别在于（参考teacherMapper.xml的getTeacherByJoin方法）：
	 *			一对一嵌套结果使用<association>，一对多中使用<collection>标签
	 * 			一对一中使用javaType指定类型，一堆多中使用ofType指定类型；
	 * 			resultMap中必须指定id 的标签，否则selectone中返回结果会报错
	 * 		2.2一对多嵌套查询：同样跟一对一嵌套结果类似
	 * 			注意一下collection标签、ofType、指定id标签对应的column
	 * 
	 * 
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		
		
		InputStream is = Test.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
		
		System.out.println(is);
		
		SqlSessionFactory sessionFactory =  new SqlSessionFactoryBuilder().build(is);
		
		System.out.println(sessionFactory);
		
		SqlSession session = sessionFactory.openSession();
		
		System.out.println(session);
		
		
		String userNameSpace = "com.mybatis.vo.User";
		/*一对一嵌套结果*/
		String selectOneByJoin = userNameSpace+".selectUserById";
		User user = session.selectOne(selectOneByJoin,3);
		System.out.println(user);


		/*一对一嵌套查询*/
		String selectOneBySelect = userNameSpace+".selectSingleUserById";
		User user2 = session.selectOne(selectOneBySelect,1);
		System.out.println(user2);
		
		
		
		String teacherNameSpace = "com.mybatis.vo.Teacher";
		/*一对多嵌套结果*/
		String getTeacherByJoin = teacherNameSpace+".getTeacherByJoin";
		Teacher teacher = session.selectOne(getTeacherByJoin,1);
		System.out.println(teacher);
		
		
		/*一对多嵌套查询*/
		String getTeacherById = teacherNameSpace+".getTeacherById";
		Teacher teacher2 = session.selectOne(getTeacherById,2);
		System.out.println(teacher2);
		
	}
}
