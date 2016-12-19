package com.mybatis.test;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.vo.Teacher;
import com.mybatis.vo.User;

public class Test {
	
	/**
	 * MyBatis��Ƕ�׽��/Ƕ�ײ�ѯ��������˵���ǽ��ӳټ��أ�
	 * 
	 * 1��һ��һ��
	 * 		1.1 һ��һǶ�׽����ʵ�ʾ��ǽ�������join�������ο�userMapper.xml��selectUserById������
	 * 			�ó��������ݣ����У���Ҫ��resultMap���أ�
	 * 		����resultMap
	 * 		<resultMap type="User" id="selectUserMap" autoMapping="true"></resultMap>
	 * 		type�����ص����͵�ȫ����/���ñ��� 
	 * 		autoMapping���Զ���Ӧ�ֶΣ������д������Ҫ�ֶ�ӳ������
	 * 					�����Ǽ���<id column="xxx" property="xxx" />/<result column="xxx" property="xxx"/>
	 * 		
	 * 		1.2 ͨ�� <association> ��ǩ�����÷��ص�һ��һ��bean����User�ж�Ӧһ��Teacher����association������Ҫ����Teacher��class
	 * 			���磺<association property="teacher" javaType="Teacher" autoMapping="true"><id column="teacherId" property="id"/><association/>
	 * 
	 * 
	 * 		1.3һ��һǶ�ײ�ѯ��ʵ���������β�ѯ���ο�userMapper.xml��selectSingleUserById��
	 * 			��Ƕ�׽�����ƣ�ֻ��������associationʱ����Ҫ�������������дselect������id���ο�selectSingleUser������
	 * 			���磺<association javaType="Teacher" property="teacher" column="teacherId" select="selectSingleUser"  autoMapping="true"></association>
	 * 			������Ҫ�ἰһ�µ���column Ϊ������������
	 * 
	 * 
	 * 2��һ�Զ�
	 *		2.1 һ�Զ�Ƕ�׽������һ��һǶ�׽�����ƣ��������ڣ��ο�teacherMapper.xml��getTeacherByJoin��������
	 *			һ��һǶ�׽��ʹ��<association>��һ�Զ���ʹ��<collection>��ǩ
	 * 			һ��һ��ʹ��javaTypeָ�����ͣ�һ�Ѷ���ʹ��ofTypeָ�����ͣ�
	 * 			resultMap�б���ָ��id �ı�ǩ������selectone�з��ؽ���ᱨ��
	 * 		2.2һ�Զ�Ƕ�ײ�ѯ��ͬ����һ��һǶ�׽������
	 * 			ע��һ��collection��ǩ��ofType��ָ��id��ǩ��Ӧ��column
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
		/*һ��һǶ�׽��*/
		String selectOneByJoin = userNameSpace+".selectUserById";
		User user = session.selectOne(selectOneByJoin,3);
		System.out.println(user);


		/*һ��һǶ�ײ�ѯ*/
		String selectOneBySelect = userNameSpace+".selectSingleUserById";
		User user2 = session.selectOne(selectOneBySelect,1);
		System.out.println(user2);
		
		
		
		String teacherNameSpace = "com.mybatis.vo.Teacher";
		/*һ�Զ�Ƕ�׽��*/
		String getTeacherByJoin = teacherNameSpace+".getTeacherByJoin";
		Teacher teacher = session.selectOne(getTeacherByJoin,1);
		System.out.println(teacher);
		
		
		/*һ�Զ�Ƕ�ײ�ѯ*/
		String getTeacherById = teacherNameSpace+".getTeacherById";
		Teacher teacher2 = session.selectOne(getTeacherById,2);
		System.out.println(teacher2);
		
	}
}
