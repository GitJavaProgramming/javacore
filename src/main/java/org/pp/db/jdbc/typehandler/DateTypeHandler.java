package org.pp.db.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateTypeHandler implements TypeHandler<Date> {
    @Override
    public Date getValue(ResultSet resultSet, int i) {
        try {
            return resultSet.getTimestamp(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void setValue(PreparedStatement statement, int i, Date date) {
        try {
            statement.setDate(i, (java.sql.Date) date);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
