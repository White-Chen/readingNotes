package spittr.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

/**
 * \* Created with Chen Zhe on 1/17/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration
@ComponentScan("spittr.web")
@EnableWebMvc
public class WebConfig
        extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(){
        //InternalResourceViewResolver resolver =
        //        new InternalResourceViewResolver();
        //resolver.setPrefix("/WEB-INF/views/");
        //resolver.setSuffix(".jsp");
        //resolver.setExposeContextBeansAsAttributes(true);
        //return resolver;
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{
                "/WEB-INF/layout/tiles.xml"
        });
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Bean
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        return messageSource;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
