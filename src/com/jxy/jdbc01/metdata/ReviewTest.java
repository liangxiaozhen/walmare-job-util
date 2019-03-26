package com.jxy.jdbc01.metdata;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;


public class ReviewTest {
	
	/**
	 * 1. ResultSet ��װ JDBC �Ĳ�ѯ���.
	 * ��ѯ���ݿ����ݱ��������
	 */
	@Test
	public void testResultSet(){
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			//1. ��ȡ���ݿ�����
			connection = getConnection();
			
			//2. ���� Connection ����� createStatement() ������ȡ Statement ����
			statement = connection.createStatement();
			
			//3. ׼�� SQL ���
			String sql = "SELECT * FROM user";
			
			//4. ���� SQL ���: ���� Statement �����  executeQuery(sql) ����.
			//�õ���������� ResultSet
			resultSet = statement.executeQuery(sql);
			
			//5. ��������:
			//5.1 ���� ResultSet �� next() ����: �鿴���������һ����¼�Ƿ���Ч, 
			//����Ч������ָ��
			while(resultSet.next()){
				//5.2 getXxx() ������ȡ������е�ֵ. 
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				int age = resultSet.getInt(3);
				
				System.out.println(id);
				System.out.println(name);
				System.out.println(age);
				
				System.out.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//6. �ر����ݿ���Դ
			releaseDB(resultSet, statement, connection);
		}
		
	}
	
	/**
	 * 1. Statement �����ڲ��� SQL �Ķ���,�޸�
	 */
	@Test
	public void testStatement(){
		Connection connection = null;
		Statement statement = null;
		
		try {
			//1. ��ȡ���ݿ�����
			connection = getConnection();
			
			//2. ���� Connection ����� createStatement() ������ȡ Statement ����
			statement = connection.createStatement();
			
			//3. ׼�� SQL ���
			String sql = "UPDATE user SET name = 'Jerry' " +
					"WHERE id = 1";
			
			//4. ���� SQL ���: ���� Statement �����  executeUpdate(sql) ����
			statement.executeUpdate(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//5. �ر����ݿ���Դ: ��������ر�. 
			releaseDB(null, statement, connection);
		}		
		
	}
	
	/**
	 * �رո�������
	 * @param @param resultSet
	 * @param @param statement
	 * @param @param connection
	 * @return void
	 * @author jiangxueyou
	 */
	public void releaseDB(ResultSet resultSet, Statement statement,Connection connection){
		
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void testGetConnection2(){
		
		Connection connection = getConnection();
		
		System.out.println(connection); 
	}
	
	/**
	 * ��ȡ���ݿ�����
	 * @param @return
	 * @param @throws IOException
	 * @param @throws ClassNotFoundException
	 * @param @throws SQLException
	 * @return Connection
	 * @author jiangxueyou
	 */
	public Connection getConnection(){
		Connection connection = null;
		try {
			//0. ��ȡ jdbc.properties
			/**
			 * 1). �����ļ���Ӧ Java �е� Properties ��
			 * 2). ����ʹ������������� bin Ŀ¼(��·����)���ļ�
			 */
			Properties properties = new Properties();
			InputStream inStream = ReviewTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
			properties.load(inStream);
			
			//1. ׼����ȡ���ӵ� 4 ���ַ���: user, password, jdbcUrl, driverClass
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			String jdbcUrl = properties.getProperty("jdbcUrl");
			String driverClass = properties.getProperty("driver");
			
			//2. ��������: Class.forName(driverClass)
			Class.forName(driverClass);
			//3. ���� 
			//DriverManager.getConnection(jdbcUrl, user, password)
			//��ȡ���ݿ�����
			connection = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * Connection ����Ӧ�ó�������ݿ��һ������.
	 * @throws Exception 
	 * �������ݿ�����
	 */
	@Test
	public void testGetConnection() throws Exception{
		//1. ׼����ȡ���ӵ� 4 ���ַ���: user, password, jdbcUrl, driverClass
		String user = "root";
		String password = "root";
		String jdbcUrl = "jdbc:mysql://localhost:3306/test";
		String driverClass = "com.mysql.jdbc.Driver";
		
		//2. ��������: Class.forName(driverClass)
		Class.forName(driverClass);
		
		//3. ���� 
		//DriverManager.getConnection(jdbcUrl, user, password)
		//��ȡ���ݿ�����
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		
		System.out.println(connection); 
	}

}
