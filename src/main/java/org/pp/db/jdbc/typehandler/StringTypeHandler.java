package org.pp.db.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public String getValue(ResultSet resultSet, int i) {
        try {
            return resultSet.getString(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void setValue(PreparedStatement statement, int i, String s) {
        try {
            statement.setString(i, s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
