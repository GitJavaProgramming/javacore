package org.pp.db.jdbc.core;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.pp.db.jdbc.User;
import org.pp.db.jdbc.registry.Registry;
import org.pp.db.jdbc.typehandler.TypeHandler;

import java.sql.*;
import java.util.List;

public class Executor {

    private final Environment environment;
    private final Connection conn;

    public Executor(Environment environment) {
        this.environment = environment;
        this.conn = environment.driverManagerGetConnection();
    }

    public void query(String query) {
        try {
            Statement statement = conn.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount(); // 每条记录有多少列
            DatabaseMetaData connMetaData = conn.getMetaData();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            // 如何判断查询的结果是什么类型
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for (int index = 1; index <= columnCount; index++) {
                    String columnName = metaData.getColumnName(index);
                    Class<?> clazz = classLoader.loadClass(metaData.getColumnClassName(index));
                    TypeHandler typeHandler = Registry.getTypeHandler(clazz);
                    Object obj = typeHandler.getValue(resultSet, index);
                    json.putIfAbsent(columnName, obj);
                }
                jsonArray.add(json);
            }
            List<User> users = jsonArray.toList(User.class);
            System.out.println(users);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
