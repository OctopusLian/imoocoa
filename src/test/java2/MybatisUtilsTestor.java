import com.imooc.oa.utils.MybatisUtils;
import org.junit.Test;

public class MybatisUtilsTestor {
    @Test
    public void testCase1() {
        String result = (String) MybatisUtils.executeQuery(sqlSession -> {
            String out = (String) sqlSession.selectOne("test.sample");
            return out;
        });
        System.out.println(result);
    }
}
