package xmlconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ChenZhePC on 2016/12/27.
 */

@Configuration // 声明当前类为Spring配置类
public class CDPlayerConfig {
    @Bean
    public CompactDisc sgtPeppers() {
        return new SgtPeppers();
    }

    /*@Bean
    public CDPlayer cdPlayer(){
        return new CDPlayer(sgtPeppers());
    }*/

    @Bean
    public CDPlayer cdPlayer(CompactDisc compactDisc) {
        CDPlayer cdPlayer = new CDPlayer(compactDisc);
        return cdPlayer;
    }
}
