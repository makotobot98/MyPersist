//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
    public void test() throws IOException, PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("eddie");
        List<User> ls = sqlSession.selectList("user.selectListByName", user);
        System.out.println(ls);
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
