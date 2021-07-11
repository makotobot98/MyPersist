package com.mako.builder;

import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class XMLMapperBuilder extends BaseBuilder {
    private Document document;

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration);
    }

    public XMLMapperBuilder(Document document, Configuration configuration) {
        super(configuration);
        this.document = document;
    }

    public void parse() {
        
    }
}
