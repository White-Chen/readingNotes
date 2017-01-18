package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by ChenZhePC on 2017/1/18.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */


public class HomeControllerTest {

    @Test
    public void testHomePage() throws Exception{
        HomeController homeController =
                new HomeController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

}
