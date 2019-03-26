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
		Connection connection = null;  //数据库链接对象
		PreparedStatement preparedStatement = null;  //预编译语言
		ResultSet resultSet = null; //返回结果集
		try{
			//1. 得到 ResultSet 对象
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			//2. 得到 ResultSetMetaData 对象
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//3. 创建一个 Map<String, Object> 对象, 键: SQL 查询的列的别名, 
			//值: 列的值
			Map<String, Object> values = new HashMap<>();
			
			//4. 处理结果集. 利用 ResultSetMetaData 填充 3 对应的 Map 对象
			if(resultSet.next()){
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(i + 1);
					
					values.put(columnLabel, columnValue);
				}
			}
			
			//5. 若 Map 不为空集, 利用反射创建 clazz 对应的对象
			if(values.size() > 0){
				entity = clazz.newInstance();
				
				//5. 遍历 Map 对象, 利用反射为 Class 对象的对应的属性赋值. 
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
