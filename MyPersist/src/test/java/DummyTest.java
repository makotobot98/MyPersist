//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.mako.dao.UserDao;
import com.mako.io.Resources;
import com.mako.pojo.User;
import com.mako.session.DefaultSqlSession;
import com.mako.session.SqlSession;
import com.mako.session.SqlSessionFactory;
import com.mako.session.SqlSessionFactoryBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DummyTest {
    @Test
    public void testGetMapperFindAllByName() throws PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("eddie");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.findAllByName(user);
        System.out.println(list);
    }

    @Test
    public void testGetMapperInsertOne() throws PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(12);
        user.setUsername("jake");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int res = userDao.insertOne(user);
        System.out.println("insert rows affected: " + res);
    }

    @Test
    public void testGetMapperDeleteById() throws PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(12);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int res = userDao.deleteById(user);
        System.out.println("delete rows affected: " + res);
    }

    @Test
    public void testGetMapperUpdateById() throws PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(11);
        user.setUsername("james");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int res = userDao.updateById(user);
        System.out.println("update rows affected: " + res);
    }

    @Test
    public void testSelectList() throws IOException, PropertyVetoException, DocumentException, ClassNotFoundException,
            SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("eddie");
        List<User> ls = sqlSession.selectList("user.selectListByName", user);
        System.out.println(ls);
    }

    @Test
    public void testInsertOne() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("peter");
        user.setId(14);
        int res = sqlSession.insert("user.insertOne", user);
        System.out.println("records inserted: " + res);
    }

    @Test
    public void testDeleteById() throws SQLException, NoSuchFieldException, IllegalAccessException, PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(8);
        int res = sqlSession.delete("user.deleteById", user);
        System.out.println("records inserted: " + res);
    }

    @Test
    public void testUpdateById() throws SQLException, NoSuchFieldException, IllegalAccessException,
            PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(7);
        user.setUsername("cain");
        int res = sqlSession.update("user.updateById", user);
        System.out.println("records updated: " + res);
    }

    @Test
    public void testDBConnection() throws Exception{
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setUser("root");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/my_persist");
        ds.setPassword("15925117737");

        ds.setAcquireRetryAttempts(2);
        Connection connection = ds.getConnection();
        System.out.println("connection established successfully");
    }
}
