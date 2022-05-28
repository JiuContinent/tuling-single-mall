import com.tulingxueyuan.mall.StartApp;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author JiuContinent
 * @create 2022/5/18 16:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StartApp.class})
public class ProductTests {
    @Autowired
    PmsProductAttributeCategoryMapper mapper;
    @Test
    public void test01(){
        System.out.println(mapper.getListWithAttr());
    }
}
