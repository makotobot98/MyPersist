import com.mako.io.Resources;
import org.junit.Test;

public class MyPersistTest {
    @Test
    public void test() {
        Resources.getResourceAsStream("sqlMapConfig.xml");
    }
}
