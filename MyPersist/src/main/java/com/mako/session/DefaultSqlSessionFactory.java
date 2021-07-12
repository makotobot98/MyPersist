package com.mako.session;

import com.mako.executor.DefaultExecutor;
import com.mako.executor.Executor;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new DefaultExecutor();
        return new DefaultSqlSession(this.configuration, executor);
    }
}
