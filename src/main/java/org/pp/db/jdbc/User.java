package org.pp.db.jdbc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@EqualsAndHashCode
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
