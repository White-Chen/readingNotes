package autoconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ChenZhePC on 2016/12/27.
 */

@Configuration // 声明当前类为Spring配置类
@ComponentScan("autoconfig") // 启用组件扫描！！
public class CDPlayerConfig {
    // 不需要定义类
}
