package org.pp.db.jdbc;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class User {
    private Integer id;
    private Date createTime;
    private String name;
    private String password;
    private String phone;
    private String nickName;
}
