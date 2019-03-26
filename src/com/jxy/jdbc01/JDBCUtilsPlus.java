package com.jxy.jdbc01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtilsPlus {
	private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    
    private JDBCUtilsPlus(){}
    
    static {
    	// ResourceBundle.getBundle 主要用于解析properties 文件
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        
        DRIVER = bundle.getString("driver");
        URL = bundle.getString("jdbcUrl");
        USER = bundle.getString("user");
        PASSWORD = bundle.getString("password");
        
        /**
         * 驱动注册
         */
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    /**
     * 获取 Connetion
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * 释放资源  秉持着后开先关的原则
     * @param conn
     * @param st
     * @param rs
     */
    public static void colseResource(Connection conn,Statement st,ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }
    
    /**
     * 释放连接 Connection
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        conn = null;
    }
    
    /**
     * 释放语句执行者 Statement
     * @param st
     */
    public static void closeStatement(Statement st) {
        if(st !=null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        st = null;
    }
    
    /**
     * 释放结果集 ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if(rs !=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //等待垃圾回收
        rs = null;
    }
    
    public static void main(String[] args) {
    	Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            // 获取连接
            conn = JDBCUtilsPlus.getConnection();
            
            // 编写sql
            String sql = "select * from user";
            
            // 创建语句执行者
            st= conn.prepareStatement(sql);
            //执行sql查询
            rs = st.executeQuery();
            
            while(rs.next()) {
                System.out.println(rs.getString(1)+"..."+rs.getString(2)+"..."+rs.getString(3));
            }
            
            
            //插入一条数据
            String sql2 = "insert into users (username,password) values(?,?)";
            st=conn.prepareStatement(sql2);
			
			//一个一个参数的插入
            st.setString(1, "哈哈哈");
            st.setString(2, "99999");
			
            st.execute();//执行
			System.out.println("插入一个数据1");
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsPlus.colseResource(conn, st, rs);
        }
        
    }
	
}
