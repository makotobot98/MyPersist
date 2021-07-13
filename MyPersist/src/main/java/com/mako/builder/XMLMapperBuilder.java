package com.mako.builder;

import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder extends BaseBuilder {
    private Element rootElement;
    private String namespace; //TODO: add this to BuilderAssistant along with <cache> tags

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration);
    }

    public XMLMapperBuilder(Document document, Configuration configuration) {
        this(document.getRootElement(), configuration);
    }

    public XMLMapperBuilder(Element rootElement, Configuration configuration) {
        this(rootElement, configuration, rootElement.attributeValue("namespace"));
    }

    public XMLMapperBuilder(Element rootElement, Configuration configuration, String namespace) {
        super(configuration);
        this.rootElement = rootElement;
        this.namespace = namespace;
    }

    /**
     * parse mapper xml files stored in dom4j document, this.document
     * -. parse SQL command type (insert, update, delete, select, flush)
     * -. parse namespace of the mapper
     * -. parse SQL statement id
     * -. parse resultType class
     * -. parse parameterType
     *
     * wrap and store the parsed result into MappedStatement, and store each MappedStatement
     * into a Map<StatementId, MappedStatement>, `StatementId` should be unique = sql namespace + sql id
     */
    public void parse() throws ClassNotFoundException {
        //parse all select, insert, update, delete xml tags
        buildSqlStatements(rootElement.selectNodes("select|insert|update|delete"));
    }

    /**
     * 1. parse each of the sql statement
     * 2. add parsed statement onto the 'MappedStatements<String, MappedStatement>' in configuration object
     * @param list a list of sql statement dom4j element
     */
    public void buildSqlStatements(List<Element> list) throws ClassNotFoundException {
        for (Element statementElement : list) {
            XMLSqlStatementBuilder sqlStatementParser = new XMLSqlStatementBuilder(this.configuration,
                    statementElement, this.namespace);
            sqlStatementParser.parseSqlStatementElement();
        }
    }
}
