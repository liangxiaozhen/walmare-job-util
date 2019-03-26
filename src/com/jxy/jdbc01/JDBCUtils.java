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
         * ����ע��
         */
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
        
    }
    
    /**
     * ��ȡ Connetion
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return (Connection) DriverManager.getConnection(url, user, password);
    }
    
    /**
     * �ͷ���Դ
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
     * �ͷ����� Connection
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
        //�ȴ���������
        conn = null;
    }
    
    /**
     * �ͷ����ִ���� Statement
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
        //�ȴ���������
        st = null;
    }
    
    /**
     * �ͷŽ���� ResultSet
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
        //�ȴ���������
        rs = null;
    }
    
    public static void main(String[] args) {
    	 Connection conn = null;
         PreparedStatement st = null;
         ResultSet rs = null;
         
         try {
             // ��ȡ����
             conn = JDBCUtils.getConnection();
             
             // ��дsql
             String sql = "insert into user values (?,?)";
             
             // �������ִ����
             st= conn.prepareStatement(sql);
             
             //���ò���
             st.setString(1, "10");
             st.setString(2, "����Ŀ¼");
             
             // ִ��sql
             int i = st.executeUpdate();
             
             if(i==1) {
                 System.out.println("������ӳɹ���");
             }else {
                 System.out.println("�������ʧ�ܣ�");
             }
             
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             JDBCUtils.colseResource(conn, (Statement) st, rs);
         }
         
     }
	

}
