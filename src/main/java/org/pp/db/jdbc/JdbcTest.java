package org.pp.db.jdbc;

import org.pp.db.jdbc.core.Environment;
import org.pp.db.jdbc.core.Executor;
import org.pp.db.jdbc.core.UserMapper;

public class JdbcTest {

    public static void main(String[] args) {
        Environment environment = new Environment();
        Executor executor = new Executor(environment);
//        executor.query("select * from user");
        UserMapper userMapper = executor.getMapper(UserMapper.class);
        System.out.println(userMapper.list("nihao"));
    }
}
