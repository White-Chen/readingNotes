package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * \* Created with Chen Zhe on 1/15/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Controller                             //声明为一个控制器, 组件扫描时属于备选bean
@RequestMapping({"/", "homepage"})      //类级别的路径映射, 同时匹配多个请求路径
public class HomeController {

    /**
     * @return 返回渲染视图的名称, 然后根据WebConfig中配置的视图前缀和后缀形成视图路径, 这里自动生成的是/WEB-INF/views/home.jsp
     */
    @RequestMapping(method = RequestMethod.GET) //method对应是请求类型
    public String home() {
        return "home";
    }
}
