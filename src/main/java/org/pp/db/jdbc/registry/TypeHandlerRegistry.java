package org.pp.db.jdbc.registry;

import org.pp.db.jdbc.typehandler.TypeHandler;
import org.pp.db.jdbc.typehandler.DateTypeHandler;
import org.pp.db.jdbc.typehandler.IntegerTypeHandler;
import org.pp.db.jdbc.typehandler.StringTypeHandler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TypeHandlerRegistry {
    private static final Map<Class, TypeHandler> typeHandlerMap = new HashMap();

    static {
        typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        typeHandlerMap.put(String.class, new StringTypeHandler());
        typeHandlerMap.put(Timestamp.class, new DateTypeHandler());
    }

    public static TypeHandler getTypeHandler(Class clazz) {
        return typeHandlerMap.get(clazz);
    }
}
