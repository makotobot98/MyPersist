package com.mako.session;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

    /**
     * Read a list of rows using the sql select statement identified by string 'statement'
     * @param statement a string uniquely identifies a sql select statement, i.e. if namespace = 'user',
     *                  <select id = selectAll'>, then statement = "user.selectAll"
     * @param <E> the returned list element type
     * @return list of rows of records based on the sql statement
     */
    <E> List<E> selectList(String statement) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException;

    /**
     * @param statement a string uniquely identifies a sql select statement, i.e. if namespace = 'user',
     *                  <select id = selectAll'>, then statement = "user.selectAll"
     * @param parameter parameter for the select statement, parameter should be of a Pojo class that has fields maps
     *                  to the columns in the database
     * @param <E> the returned list element type
     * @return list of rows of records based on the sql statement
     */
    <E> List<E> selectList(String statement, Object parameter) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException;

    int update(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException;
    int insert(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException;
    int delete(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException;
}
