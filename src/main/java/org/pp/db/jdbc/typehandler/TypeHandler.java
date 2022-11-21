package org.pp.db.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface TypeHandler<T> {
    T getValue(ResultSet resultSet, int i);

    void setValue(PreparedStatement statement, int i, T t);
}
