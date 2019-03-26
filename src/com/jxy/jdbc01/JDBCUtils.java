package com.jxy.jdbc01;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JDBCUtils {
	private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/test";
    private static String user="root";
    private static String password="root";
    
    private JDBCUtils(){}
    
    static {
        /**
         * 驱动注册
         */
        try {
            Class.forName(driver);
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
        return (Connection) DriverManager.getConnection(url, user, password);
    }
    
    /**
     * 释放资源
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
             conn = JDBCUtils.getConnection();
             
             // 编写sql
             String sql = "insert into user values (?,?)";
             
             // 创建语句执行者
             st= conn.prepareStatement(sql);
             
             //设置参数
             st.setString(1, "10");
             st.setString(2, "测试目录");
             
             // 执行sql
             int i = st.executeUpdate();
             
             if(i==1) {
                 System.out.println("数据添加成功！");
             }else {
                 System.out.println("数据添加失败！");
             }
             
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             JDBCUtils.colseResource(conn, (Statement) st, rs);
         }
         
     }
	

}
