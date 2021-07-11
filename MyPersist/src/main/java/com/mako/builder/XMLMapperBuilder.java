package com.mako.builder;

import com.mako.session.Configuration;

import java.io.InputStream;

public class XMLMapperBuilder extends BaseBuilder {

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration) {
        super(configuration);
    }
}
