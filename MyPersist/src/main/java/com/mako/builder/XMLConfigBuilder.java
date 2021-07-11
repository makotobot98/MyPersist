package com.mako.builder;

import com.mako.io.Resources;
import com.mako.session.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder extends BaseBuilder{
    private Document document;

    public XMLConfigBuilder(InputStream is) throws DocumentException {
        this(new SAXReader().read(is));
    }

    public XMLConfigBuilder(Document document) {
        super(new Configuration());
        this.document = document;
    }

    public Configuration parse() throws PropertyVetoException {
        parseConfig(this.document.getRootElement());
        return this.configuration;
    }

    /**
     * Based on the dom4j's root element of the xml file, parse and store properties into configuration
     * @param rootElement dom4j's parsed root element of the xml file
     */
    public void parseConfig(Element rootElement) throws PropertyVetoException {
        dataSourceElements(rootElement.selectSingleNode("dataSource"));
        mapperElements(rootElement.selectSingleNode("mappers"));
    }

    /**
     * plug data source properties into configuration object
     * @param rootElement dom4j's root element for sql-config.xml
     */
    public void dataSourceElements(Node rootElement) throws PropertyVetoException {
        List<Element> propertyList = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element propertyElement : propertyList) {
            String name = propertyElement.attributeValue("name");
            String value = propertyElement.attributeValue("value");
            properties.setProperty(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        this.configuration.setDataSource(comboPooledDataSource);
    }

    /**
     * plug user defined mappers into configuration object
     * @param rootElement dom4j's root element for sql-config.xml
     */
    public void mapperElements(Node rootElement) {
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperList) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(mapperPath);


        }
    }
}
