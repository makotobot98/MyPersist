package com.mako.session;

import com.mako.executor.Executor;

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
    public <E> List<E> selectList(String statement) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return null;
    }

    @Override
    public void update(String statement, Object parameter) {

    }

    @Override
    public void delete(String statement, Object parameter) {

    }
}
