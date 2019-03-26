package com.jxy.jdbc01;

import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class JDBC01 {
	
	//最粗暴的链接数据库的方法
	public static void main(String[] args) throws SQLException {
		//1.创建一个 Driver 实现类的对象
		Driver driver = new  Driver();
		
		//2.准备链接数据路的基本信息 : url user password
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "root";
		Properties p = new Properties();
		p.put("user", user);
		p.put("password", password);
		
		//3.调用Driver接口的connect(url,info) 获取数据库链接
		Connection connection = (Connection) driver.connect(url, p);
		System.out.println(connection);
	}

}
