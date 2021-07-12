//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.mako.io.Resources;
import com.mako.session.DefaultSqlSession;
import com.mako.session.SqlSession;
import com.mako.session.SqlSessionFactory;
import com.mako.session.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;

public class DummyTest {
    @Test
    public void test() throws IOException, PropertyVetoException, DocumentException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
    }
}
