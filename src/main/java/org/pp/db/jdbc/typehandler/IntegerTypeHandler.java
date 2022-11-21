package org.pp.db.jdbc.typehandler;

import java.sql.PreparedStatement;
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
    public void setValue(PreparedStatement statement, int i, Integer integer) {
        try {
            statement.setInt(i, integer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
