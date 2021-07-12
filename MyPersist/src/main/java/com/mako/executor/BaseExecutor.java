package com.mako.executor;

import com.mako.mapping.MappedStatement;
import com.mako.session.Configuration;

import java.util.List;

public class BaseExecutor implements Executor{
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object parameter) {
        //1. obtain datasource and db connection

        //2. obtain sql statement
        return null;
    }
}
