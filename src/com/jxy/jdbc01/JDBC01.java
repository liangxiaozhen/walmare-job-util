package com.jxy.jdbc01;

import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class JDBC01 {
	
	//��ֱ����������ݿ�ķ���
	public static void main(String[] args) throws SQLException {
		//1.����һ�� Driver ʵ����Ķ���
		Driver driver = new  Driver();
		
		//2.׼����������·�Ļ�����Ϣ : url user password
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "root";
		Properties p = new Properties();
		p.put("user", user);
		p.put("password", password);
		
		//3.����Driver�ӿڵ�connect(url,info) ��ȡ���ݿ�����
		Connection connection = (Connection) driver.connect(url, p);
		System.out.println(connection);
	}

}
