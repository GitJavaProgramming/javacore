package org.pp.db.jdbc.core;

import org.pp.db.jdbc.registry.TypeHandlerRegistry;
import org.pp.db.jdbc.typehandler.TypeHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor {

    private final Environment environment;
    private final Connection conn;

    private final Map<Class, Object> map = new ConcurrentHashMap<>();

    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Executor(Environment environment) {
        this.environment = environment;
        this.conn = environment.driverManagerGetConnection();
        scan();
        hook();
    }

    private void scan() {
        Class aClass = UserMapper.class;
        if (!aClass.isInterface()) {
            return;
        }
        MapperProxy<?> proxy = new MapperProxy(this, aClass);
        UserMapper proxy1 = (UserMapper) proxy.proxy();
        map.put(aClass, proxy1);
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) map.get(clazz);
    }

    public Future execute(final MethodWrapper methodWrapper) {
        return service.submit(() -> query(methodWrapper));
    }

    public Object query(final MethodWrapper methodWrapper) {
        try {
            PreparedStatement statement = conn.prepareStatement(SqlParser.parse(methodWrapper));
            List<MethodWrapper.Obj> objList = methodWrapper.getObjList();
            for (int i = 0; i < objList.size(); i++) {
                MethodWrapper.Obj o = objList.get(i);
                Class<?> clazz = o.getParamValue().getClass();
                TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(clazz);
                typeHandler.setValue(statement, i + 1, o.getParamValue());
            }
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount(); // 每条记录有多少列
            DatabaseMetaData connMetaData = conn.getMetaData();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            // 如何判断查询的结果是什么类型
            Class returnType = methodWrapper.getReturnType();

            List<Map<String, Object>> list = new ArrayList();
            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int index = 1; index <= columnCount; index++) {
                    String columnName = metaData.getColumnName(index);
                    Class<?> clazz = classLoader.loadClass(metaData.getColumnClassName(index));
                    TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(clazz);
                    Object obj = typeHandler.getValue(resultSet, index);
                    rowMap.put(columnName, obj);
                }
                list.add(rowMap);
            }
            return (list.size() == 0) ? list.get(0) : list;
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
        return null;
    }

    public void hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> service.shutdownNow()));
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

            List<Map<String, Object>> list = new ArrayList();
            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int index = 1; index <= columnCount; index++) {
                    String columnName = metaData.getColumnName(index);
                    Class<?> clazz = classLoader.loadClass(metaData.getColumnClassName(index));
                    TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(clazz);
                    Object obj = typeHandler.getValue(resultSet, index);
                    rowMap.put(columnName, obj);
                }
                list.add(rowMap);
            }
            System.out.println(list);
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
