package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * \* Created with Chen Zhe on 1/15/2017.
 * \* Description:
 *      DispatcherServlet会加载当前类也就是@Configuration注解的类，用于配置DispatcherServlet
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration                  //声明当前Java类为Spring配置类
@EnableWebMvc                   //启用Spring MVC
@ComponentScan("spittr.web")    //配置组件扫描路径，否则Spring将只能找到显式声明在当前类中的控制器
                                //书上写的包路径有问题，配置包路径与实际包名称不符，需要自己注意
public class WebConfig
        extends WebMvcConfigurerAdapter{

    /**
     * Description : 配置试图解析器, 如果不配置则会使用默认的BeanNameViewResolver.
     */
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * Description : 配置静态资源的处理, 否则的话DispatcherServlet会处理所有静态资源的请求
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
