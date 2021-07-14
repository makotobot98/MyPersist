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
    private String sqlText;
    private BoundSql boundSql;
    private Boolean isSelect;


    MappedStatement() {
        //disable constructor
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder id(String id) {
            mappedStatement.id = id;
            return this;
        }
        public Builder resultTypeStr(String resultTypeStr) {
            mappedStatement.resultTypeStr = resultTypeStr;
            return this;
        }
        public Builder resultType(Class<?> resultType) {
            mappedStatement.resultType = resultType;
            return this;
        }
        public Builder parameterTypeStr(String parameterTypeStr) {
            mappedStatement.parameterTypeStr = parameterTypeStr;
            return this;
        }
        public Builder parameterType(Class<?> parameterType) {
            mappedStatement.parameterType = parameterType;
            return this;
        }
        public Builder sqlCommandType(SqlCommandType sqlCommandType) {
            mappedStatement.sqlCommandType = sqlCommandType;
            return this;
        }
        public Builder namespace(String namespace) {
            mappedStatement.namespace = namespace;
            return this;
        }
        public Builder sqlText(String sqlText) {
            mappedStatement.sqlText = sqlText;
            return this;
        }
        public Builder boundSql(BoundSql boundSql) {
            mappedStatement.boundSql = boundSql;
            return this;
        }
        public Builder isSelect(boolean isSelect) {
            mappedStatement.isSelect = isSelect;
            return this;
        }
        public MappedStatement build() {
            assert mappedStatement.namespace != null;
            assert mappedStatement.id != null;
            assert mappedStatement.sqlCommandType != null;
            assert mappedStatement.sqlText != null;
            assert mappedStatement.isSelect != null;
            return mappedStatement;
        }
    }

    public String getId() {
        return id;
    }

    public String getResultTypeStr() {
        return resultTypeStr;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public String getParameterTypeStr() {
        return parameterTypeStr;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getSqlText() {
        return sqlText;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public Boolean getIsSelect() {
        return isSelect;
    }
}
