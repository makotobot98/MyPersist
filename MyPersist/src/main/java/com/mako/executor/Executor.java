package com.mako.executor;

import com.mako.mapping.MappedStatement;
import com.mako.session.Configuration;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException;

    int update(Configuration configuration, MappedStatement mappedStatement, Object parameter) throws SQLException,
            NoSuchFieldException, IllegalAccessException;
}
