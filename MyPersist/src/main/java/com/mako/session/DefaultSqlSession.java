package com.mako.session;

import com.mako.executor.Executor;
import com.mako.mapping.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
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
    public <T> T selectOne(String statement) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<T> results = selectList(statement, parameter);
        if (results.size() > 1) {
            throw new RuntimeException("Returned mapped objects are not unique");
        }
        return results.get(0);
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

    @Override
    public <T> T getMapper(Class<?> daoClass) {
        T daoImplProxy = (T) Proxy.newProxyInstance(daoClass.getClassLoader(), new Class[]{daoClass},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String declaringClassName = method.getDeclaringClass().getName();
                String mapperStatementKey = declaringClassName + "." + methodName;
                //obtain associated mapped statement
                //MappedStatement mappedStatement = configuration.getMappedStatement(mapperStatementKey);
                Type genericReturnType = method.getGenericReturnType();
                Class<?> type = method.getReturnType();
                Object parameter = args[0];
                if (type.isPrimitive()) {
                    //insert, delete, update
                    return update(mapperStatementKey, parameter);
                } else if (genericReturnType instanceof ParameterizedType) {
                    //selectList
                    return selectList(mapperStatementKey, parameter);
                }
                //selectOne
                return selectOne(mapperStatementKey, parameter);
            }
        });
        return daoImplProxy;
    }
}
