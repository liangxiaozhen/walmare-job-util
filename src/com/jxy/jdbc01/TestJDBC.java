package com.jxy.jdbc01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class TestJDBC {
		@SuppressWarnings("null")
		public static void main(String[] args) {
			//预编译的Statement,使用预编译的Statement可以提高数据库的性能
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			Connection conn = null;
			try {
				//1、加载驱动
				Class.forName("com.mysql.jdbc.Driver");
				//2、创建连接
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
				conn.setAutoCommit(false);//事务默认自动提交,这里设置为手动提交
				//3、执行sql
				String sql = "insert into users (username,password) values(?,?)";
				ps1=conn.prepareStatement(sql);
				
				//一个一个参数的插入
				ps1.setString(1, "哈哈哈");
				ps1.setString(2, "8888888");
				
				ps1.execute();//执行
				System.out.println("插入一个数据1");
			
				
				/*String sql2 = "select * from user where username=?";
				ps1=conn.prepareStatement(sql2);
				ps1.setString(1, "蒋介石5");
				ResultSet result = ps1.executeQuery();
				while (result.next()) {
					System.out.println(result.getString("id"));
					System.out.println(result.getString("username")); 
					System.out.println(result.getString("birthday"));
					System.out.println(result.getString("sex"));
					System.out.println(result.getString("address"));
					
				}*/
				
				/*String sql2 = "insert into user (username,birthday,sex,address) values(?,?,?,?)";
				ps2=conn.prepareStatement(sql2);
				ps2.setObject(1, "蒋介石6");
				ps2.setObject(2, "123456");
				ps2.execute();//执行
				System.out.println("插入一个数据2");*/
				
				conn.commit();//提交事务
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				try {
					conn.rollback();//遇到异常事务回滚
				} catch (SQLException e1) {
					System.out.println("我是异常");
					e1.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException{
			String driverClass = null;
			String jdbcUrl = null;
			String user = null;
			String password = null;
			
			
			//1.读取配置文件
			InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(in);
			
			//2.取出配置文件里面的数据
			driverClass = properties.getProperty("driver");
			jdbcUrl = properties.getProperty("jdbcUrl");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			//启动注册
			Driver driver = (Driver) Class.forName(driverClass).newInstance();
			
			Properties info = new Properties();
			info.put("user", user);
			info.put("password", password);
			Connection connection = driver.connect(jdbcUrl, info);
			return connection;
			
		}
		
}
