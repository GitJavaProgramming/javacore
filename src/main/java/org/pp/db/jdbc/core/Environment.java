package org.pp.db.jdbc.core;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Environment {

    public Connection dataSourceGetConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setLogWriter(new PrintWriter(new OutputStreamWriter(System.out)));
            dataSource.setUrl("jdbc:mysql://localhost");
            dataSource.setDatabaseName("mytest");
            dataSource.setPort(3306);
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setConnectTimeout(3000);
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Connection driverManagerGetConnection() {
        System.out.println(System.getProperty("jdbc.drivers"));
        try {
            DriverManager.setLogWriter(new PrintWriter(new OutputStreamWriter(System.out)));
            Properties prop = getProperties();
            //AccessController.doPrivileged->ServiceLoader.load()->Driver[static]->register->driver.connect
            Connection connection = DriverManager.getConnection(prop.getProperty("jdbc.url")
                    , prop.getProperty("jdbc.username")
                    , prop.getProperty("jdbc.password"));
            System.out.println(connection);
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            String CLASSPATH = Environment.class.getClassLoader().getResource("").getFile();
            InputStream inputStream = new FileInputStream(CLASSPATH + File.separator + "mysql.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
