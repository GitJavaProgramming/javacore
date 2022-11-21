package org.pp.db.jdbc.core;

import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 19:51
 * ************厚德载物************
 **/
@Mapper
public interface UserMapper {

    @Select(sql = "select * from user where name = #{name}")
    List/*<User>*/ list(@Param("name") String name);
}
