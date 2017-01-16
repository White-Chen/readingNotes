package spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * \* Created with Chen Zhe on 1/15/2017.
 * \* Description:
 *      DispatcherServlet会加载当前类也就是@Configuration注解的类，用于配置ContextLoaderListener
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration
//书上写的包路径有问题，配置包路径与实际包名称不符，需要自己注意
@ComponentScan(basePackages = {"spittr.web"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
    })
public class RootConfig{
}
