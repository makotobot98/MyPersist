package com.mako.session;

import com.mako.builder.XMLConfigBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    /**
     * build Configuration (and MapperStatement) objects using input stream
     * @param is input stream to the sql-config.xml
     * @return returns a sql session factory builder
     */
    public SqlSessionFactory build(InputStream is) throws DocumentException, PropertyVetoException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(is);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration configuration) {
        return null;
    }
}
