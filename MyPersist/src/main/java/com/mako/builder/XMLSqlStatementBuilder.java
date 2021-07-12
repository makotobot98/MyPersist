package com.mako.builder;

import com.mako.mapping.SqlCommandType;
import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Locale;

public class XMLSqlStatementBuilder extends BaseBuilder {
    public Element element;
    public String namespace; //TODO: add this to BuilderAssistant along with <cache> tags

    public XMLSqlStatementBuilder(Configuration configuration, Element element, String namespace) {
        super(configuration);
        this.element = element;
        this.namespace = namespace;
    }

    /**
     * Example XML element:
     * <select id="selectOne" resultType="com.mako.pojo.User" parameterType="com.mako.pojo.User">
     *         select * from user where id = #{id} and username = #{username}
     * </select>
     *
     * TODO:
     * 1. parse sql statement dom4j element into a MappedStatement Object:
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
    public void parseSqlStatementElement() throws ClassNotFoundException {
        String parameterTypeStr = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterTypeStr);
        String resultTypeStr = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultTypeStr);

        String id = element.attributeValue("id");
        String mappedStatementKey = applyNamespace(id);
        String sqlText = element.getTextTrim();
        String sqlCommandTypeString = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(sqlCommandTypeString.toUpperCase(Locale.ENGLISH));

        //TODO: Debug to see if all params are set correctly
        //TODO: add BoundSql that tokenizes sqlText and associate it with parameterMappings

    }

    public void addMappedStatement() {

    }

    private Class<?> resolveClass(String classStr) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(classStr);
        return aClass;
    }
    private String applyNamespace(String s) {
        return namespace + "." + s;
    }
}
