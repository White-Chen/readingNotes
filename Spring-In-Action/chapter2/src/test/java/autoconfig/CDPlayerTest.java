package autoconfig;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ChenZhePC on 2016/12/27.
 * 测试类
 */

//调用Spring-test中的测试类，需要在maven中配置spring-test包依赖
@RunWith(SpringJUnit4ClassRunner.class)

//通过Java配置类，从而开启组件扫描
@ContextConfiguration(classes = CDPlayerConfig.class)

//通过xml加载xml，从而开启组件扫描
//@ContextConfiguration(locations = "classpath:autoconfig/cfg/CDPlayerConfig.xml")
public class CDPlayerTest {

    //来源于System Rules库的JUnit规则，该股则能够基于控制台的输出编写断言
    @Rule
    public final StandardOutputStreamLog log =
            new StandardOutputStreamLog();

    //自动装配cd
    @Autowired
    private CompactDisc cd;

    //自动装配player
    @Autowired
    private MediaPlayer player;

    //测试方法
    @Test
    public void cdShouldNotBeNull(){
        assertNotNull(cd);
    }

    @Test
    public void play(){
        player.play();
        //注意因为java的println换行符是\r\n，需要统一，否则会判断不等
        assertEquals(
                "Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles\r\n",
                log.getLog()
        );
    }
}
