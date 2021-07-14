# MyPersist





## Implementing Insert, Update, Delete

`DefaultSqlSession.sql`

```java
    @Override
    public int update(String statement, Object parameter) throws SQLException, NoSuchFieldException,
            IllegalAccessException {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.update(this.configuration, mappedStatement, parameter);
    }

    @Override
    public int insert(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return update(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return update(statement, parameter);
    }
```

`BaseExecutor.java`

```java
    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        connection = configuration.getDataSource().getConnection();
        PreparedStatement preparedStatement = buildPreparedStatement(connection, mappedStatement, parameter);
        int res = preparedStatement.executeUpdate();
        return res;
    }

    private PreparedStatement buildPreparedStatement(Connection connection, MappedStatement mappedStatement,
                                                 Object parameter) throws SQLException, NoSuchFieldException, IllegalAccessException {
        //obtain sql statement
        BoundSql boundSql = mappedStatement.getBoundSql();
        String sql = boundSql.getSql();
        //obtain prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //set parameters for prepared statement
        parameterize(mappedStatement, preparedStatement, parameter);

        return preparedStatement;
    }

    public void parameterize(MappedStatement mappedStatement, PreparedStatement ps, Object parameter) throws NoSuchFieldException, SQLException, IllegalAccessException {
        Class<?> parameterType = mappedStatement.getParameterType();
        BoundSql boundSql = mappedStatement.getBoundSql();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        if (parameterMappingList == null || parameterMappingList.isEmpty()) { //if no parameters to set
            return;
        }

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getName();
            //reflect
            Field declaredField = parameterType.getDeclaredField(name);
            declaredField.setAccessible(true);  //force fields to be accessible
            //obtain value for that parameter
            Object value = declaredField.get(parameter);
            //set parameter in preparedStatement at its index
            ps.setObject(i + 1, value);
        }
    }
```



## Implementing `getMappers(Dao.class)`

```java
    @Override
    public <T> T getMapper(Class<?> daoClass) {
        T daoImplProxy = (T) Proxy.newProxyInstance(daoClass.getClassLoader(), new Class[]{daoClass},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String declaringClassName = method.getDeclaringClass().getName();
                String mapperStatementKey = declaringClassName + "." + methodName;
                //obtain associated mapped statement
                //MappedStatement mappedStatement = configuration.getMappedStatement(mapperStatementKey);
                Type genericReturnType = method.getGenericReturnType();
                Class<?> type = method.getReturnType();
                Object parameter = args[0];
                if (type.isPrimitive()) {
                    //insert, delete, update
                    return update(mapperStatementKey, parameter);
                } else if (genericReturnType instanceof ParameterizedType) {
                    //selectList
                    return selectList(mapperStatementKey, parameter);
                }
                //selectOne
                return selectOne(mapperStatementKey, parameter);
            }
        });
        return daoImplProxy;
    }
```



## Testing

- see `MyPersist/src/test/java/DummyTest.java`

# TODO

- add `BuilderAssistant` or anything similar for `XMLMapperBuilder`, so `XMLStatementBuilder` can have access to cache information, namepsace ... and other necessary xml tag information in the `mapper.xml`

-  since in `XMLConfigBuilder` and all related builders, `configuration` object is of 1 configuration per sql session, maybe make configuration a singleton

