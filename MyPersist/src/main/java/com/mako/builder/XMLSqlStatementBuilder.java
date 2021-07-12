package com.mako.builder;

import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;

public class XMLSqlStatementBuilder extends BaseBuilder {
    public Element element;

    public XMLSqlStatementBuilder(Configuration configuration, Element element) {
        super(configuration);
        this.element = element;
    }

    /**
     * TODO:
     * 1. parse sql statement dom4j element:
     * - sql command type
     * - namespace
     * - sql statement id
     * - sql text
     * - parameter type
     * - result type
     *
     * 2. tokenize the sql text '#{param}` into '?' tokens, and store the parameter mapping list
     *
     * 3. store the mapped statement into configuration object
     */
    public void parseSqlStatementElement() {

    }

    public void addMappedStatement() {

    }
}
