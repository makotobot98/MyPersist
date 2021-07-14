package com.mako.session;

import com.mako.executor.Executor;
import com.mako.mapping.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }


    @Override
    public <E> List<E> selectList(String statement) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return selectList(statement, null);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.query(this.configuration, mappedStatement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) throws SQLException, NoSuchFieldException,
            IllegalAccessException {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.update(this.configuration, mappedStatement, parameter);
    }

    @Override
    public int insert(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return update(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return update(statement, parameter);
    }
}
