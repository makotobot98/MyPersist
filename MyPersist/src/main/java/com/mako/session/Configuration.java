package com.mako.session;

import com.mako.mapping.MappedStatement;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.util.Map;
import java.util.Properties;

public class Configuration {

    ComboPooledDataSource dataSource;
    Map<String, MappedStatement> mappedStatements;

    public MappedStatement getMappedStatement(String statementId) {
        return mappedStatements.get(statementId);
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }

    public ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
