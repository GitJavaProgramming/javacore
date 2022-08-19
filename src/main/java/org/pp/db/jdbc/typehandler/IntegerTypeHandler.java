package org.pp.db.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerTypeHandler implements TypeHandler<Integer> {

    @Override
    public Integer getValue(ResultSet resultSet, int i) {
        try {
            return resultSet.getInt(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void setValue(ResultSet resultSet, int i, Integer integer) {

    }
}
