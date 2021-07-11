package com.mako.session;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.util.Properties;

public class Configuration {

    ComboPooledDataSource dataSource;

    public ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
