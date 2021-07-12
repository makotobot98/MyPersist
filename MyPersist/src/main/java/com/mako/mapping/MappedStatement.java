package com.mako.mapping;

//TODO: finish Builder class
public class MappedStatement {

    private String id;
    private String resultTypeStr;
    private Class<?> resultType;
    private String parameterTypeStr;
    private Class<?> parameterType;
    private SqlCommandType sqlCommandType;
    private String namespace;
    private String sqlSourceStr;
    private BoundSql boundSql;

    MappedStatement() {
        //disable constructor
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder() {
        }
    }
}
