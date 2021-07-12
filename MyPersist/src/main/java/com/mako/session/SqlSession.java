package com.mako.session;

import java.util.List;

public interface SqlSession {
    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Object parameter);

    void update(String statement, Object parameter);

    void delete(String statement, Object parameter);
}
