package com.mako.mapping;

public class MappedStatement {

    private String id;
    private String resultTypeStr;
    private String parameterTypeStr;
    private SqlCommandType sqlCommandType;
    private String namespace;
    private String sqlSourceStr;

    /**
     * TODO:
     * add Class<?> for result and parameter type
     * add BoundSql to tokenize sqlSourceStr
     */

    MappedStatement() {
        //disable constructor
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder() {
        }
    }
}
