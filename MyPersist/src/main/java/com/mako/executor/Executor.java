package com.mako.executor;

import com.mako.mapping.MappedStatement;
import com.mako.session.Configuration;

import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object parameter);

}
