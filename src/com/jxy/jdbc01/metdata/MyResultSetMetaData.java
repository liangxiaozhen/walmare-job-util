package com.jxy.jdbc01.metdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class MyResultSetMetaData {
	
	public <T> T get(Class<T> clazz, String sql, Object... args) {
		T entity = null;
		Connection connection = null;  //���ݿ����Ӷ���
		PreparedStatement preparedStatement = null;  //Ԥ��������
		ResultSet resultSet = null; //���ؽ����
		try{
			//1. �õ� ResultSet ����
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			//2. �õ� ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//3. ����һ�� Map<String, Object> ����, ��: SQL ��ѯ���еı���, 
			//ֵ: �е�ֵ
			Map<String, Object> values = new HashMap<>();
			
			//4. ��������. ���� ResultSetMetaData ��� 3 ��Ӧ�� Map ����
			if(resultSet.next()){
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(i + 1);
					
					values.put(columnLabel, columnValue);
				}
			}
			
			//5. �� Map ��Ϊ�ռ�, ���÷��䴴�� clazz ��Ӧ�Ķ���
			if(values.size() > 0){
				entity = clazz.newInstance();
				
				//5. ���� Map ����, ���÷���Ϊ Class ����Ķ�Ӧ�����Ը�ֵ. 
				for(Map.Entry<String, Object> entry: values.entrySet()){
					String fieldName = entry.getKey();
					Object value = entry.getValue();
					ReflectionUtils.setFieldValue(entity, fieldName, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}

		
		return entity;
	}

}
