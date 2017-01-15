package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * \* Created with Chen Zhe on 1/15/2017.
 * \* Description: 负责将请求路由到其他的组件中
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class SpittrWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer{

    /**
     * 配置RootConfig类的位置
     * @return 返回带有@configuration注解的类，用于配置ContextLoaderListener应用上下文
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class};
    }

    /**
     * 配置WebConfig类的位置
     * @return 返回带有@Configuration注解的bean，用于配置DispatcherServlet应用上下文
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class};
    }

    /**
     * 将一个或多个路径映射到DispatcherServlet上
     * 这里通过 “/” 声明它会是默认Servlet，他会处理进入应用的所有请求。
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
