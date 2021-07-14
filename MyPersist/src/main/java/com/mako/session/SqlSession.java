package com.mako.session;

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
    <E> List<E> selectList(String statement);

    /**
     * @param statement a string uniquely identifies a sql select statement, i.e. if namespace = 'user',
     *                  <select id = selectAll'>, then statement = "user.selectAll"
     * @param parameter parameter for the select statement, parameter should be of a Pojo class that has fields maps
     *                  to the columns in the database
     * @param <E> the returned list element type
     * @return list of rows of records based on the sql statement
     */
    <E> List<E> selectList(String statement, Object parameter);

    void update(String statement, Object parameter);

    void delete(String statement, Object parameter);
}
