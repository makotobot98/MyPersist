package com.mako.builder;

import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class XMLConfigBuilder {
    private Configuration configuration;
    private Document document;

    public XMLConfigBuilder(InputStream is) throws DocumentException {
        this(new SAXReader().read(is));
    }
    public XMLConfigBuilder(Document document) {
        this.document = document;
    }

    public Configuration parse() {
        parseConfig(this.document.getRootElement());
        return this.configuration;
    }

    /**
     * Based on the dom4j's root element of the xml file, parse and store properties into configuration
     * @param rootElement dom4j's parsed root element of the xml file
     */
    public void parseConfig(Element rootElement) {
        return;
    }
}
