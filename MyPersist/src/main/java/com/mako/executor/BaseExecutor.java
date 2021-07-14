package com.mako.executor;

import com.mako.mapping.BoundSql;
import com.mako.mapping.MappedStatement;
import com.mako.mapping.utils.ParameterMapping;
import com.mako.session.Configuration;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseExecutor implements Executor {
    private Connection connection = null;
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        //obtain datasource and db connection
        connection = configuration.getDataSource().getConnection();
        PreparedStatement preparedStatement = buildPreparedStatement(connection, mappedStatement, parameter);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.getStatement());
        Class<?> resultType = mappedStatement.getResultType();
        if (resultType == null) { // if query is of insert, update, delete
            return null;
        }
        return buildResultsFromResultSet(resultSet, resultType);
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        connection = configuration.getDataSource().getConnection();
        PreparedStatement preparedStatement = buildPreparedStatement(connection, mappedStatement, parameter);
        int res = preparedStatement.executeUpdate();
        return res;
    }

    private <E> List<E> buildResultsFromResultSet(ResultSet resultSet, Class<?> resultTypeClass) throws SQLException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Object> results = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            Object resultObject = resultTypeClass.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                //reflect
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultObject, value);
            }
            //add to result list
            results.add(resultObject);
        }
        return (List<E>) results;
    }

    private PreparedStatement buildPreparedStatement(Connection connection, MappedStatement mappedStatement,
                                                 Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        //obtain sql statement
        BoundSql boundSql = mappedStatement.getBoundSql();
        String sql = boundSql.getSql();
        //obtain prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //set parameters for prepared statement
        parameterize(mappedStatement, preparedStatement, parameter);

        return preparedStatement;
    }

    public void parameterize(MappedStatement mappedStatement, PreparedStatement ps, Object parameter) throws NoSuchFieldException, SQLException, IllegalAccessException {
        Class<?> parameterType = mappedStatement.getParameterType();
        BoundSql boundSql = mappedStatement.getBoundSql();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        if (parameterMappingList == null || parameterMappingList.isEmpty()) { //if no parameters to set
            return;
        }

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getName();
            //reflect
            Field declaredField = parameterType.getDeclaredField(name);
            declaredField.setAccessible(true);  //force fields to be accessible
            //obtain value for that parameter
            Object value = declaredField.get(parameter);
            //set parameter in preparedStatement at its index
            ps.setObject(i + 1, value);
        }
    }
}
