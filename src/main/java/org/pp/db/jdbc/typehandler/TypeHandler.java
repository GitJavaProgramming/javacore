package org.pp.db.jdbc.typehandler;

import java.sql.ResultSet;

public interface TypeHandler<T> {
    T getValue(ResultSet resultSet, int i);

    void setValue(ResultSet resultSet, int i, T t);
}
