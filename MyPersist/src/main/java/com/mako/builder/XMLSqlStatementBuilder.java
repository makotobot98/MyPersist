package com.mako.builder;

import com.mako.mapping.BoundSql;
import com.mako.mapping.MappedStatement;
import com.mako.mapping.SqlCommandType;
import com.mako.mapping.utils.GenericTokenParser;
import com.mako.mapping.utils.ParameterMapping;
import com.mako.mapping.utils.ParameterMappingTokenHandler;
import com.mako.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import java.util.List;
import java.util.Locale;

public class XMLSqlStatementBuilder extends BaseBuilder {
    public Element element;
    public String namespace; //TODO: add this to BuilderAssistant along with <cache> tags

    /**
     * @param configuration sql session configuration object, only 1 configuration per sql session
     * @param element sql xml tag element, i.e. <select>select * from user</select>
     * @param namespace name space of the mapper, i.e. user
     */
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
     * 3. store the mappedStatement into configuration object
     */
    //TODO: Debug to see if all params are set correctly
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
        BoundSql boundSql = buildBoundSql(sqlText);

        //build MappedStatement
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .id(id)
                .sqlText(sqlText)
                .resultTypeStr(resultTypeStr)
                .resultType(resultTypeClass)
                .parameterTypeStr(parameterTypeStr)
                .parameterType(parameterTypeClass)
                .sqlCommandType(sqlCommandType)
                .namespace(namespace)
                .boundSql(boundSql)
                .build();

        addMappedStatement(mappedStatementKey, mappedStatement);
    }

    public void addMappedStatement(String statementKey, MappedStatement ms) {
        this.configuration.getMappedStatements().put(statementKey, ms);
    }

    private Class<?> resolveClass(String classStr) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(classStr);
        return aClass;
    }

    private String applyNamespace(String s) {
        return namespace + "." + s;
    }

    private BoundSql buildBoundSql(String sql) { //TODO: apply builder pattern for BoundSql
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("# {", "}", parameterMappingTokenHandler);
        String tokenizedSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(tokenizedSql, parameterMappingList);
        return boundSql;
    }
}
