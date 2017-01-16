package spittr.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;
import spittr.entity.Spitter;
import spittr.entity.Spittle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ChenZhePC on 2017/1/16.
 * Description : 这种测试方法可以避免在测试Controller时，反复的部署服务器然后测试
 *               测试结果为通过
 */
public class HomeControllerTest {

    @Test   //test passed
    public void testHomePage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/homepage"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test   //test passed
    public void shouldShowRecentSpittles() throws Exception{
        List<Spittle> expectedSpittles = createsSpittleList(20);
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito
                .when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
                .thenReturn(expectedSpittles);

        SpittleController controller =
                new SpittleController(mockRepository);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/spittles"))
                .andExpect(MockMvcResultMatchers.view().name("spittles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
                .andExpect(MockMvcResultMatchers.model().attribute("spittleList", CoreMatchers.hasItems(expectedSpittles.toArray())));
    }

    @Test   //test passed
    public void shouldShowPagedSpittles() throws Exception{
        List<Spittle> expectedSpittles = createsSpittleList(50);
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito
                .when(mockRepository.findSpittles(238900, 50))
                .thenReturn(expectedSpittles);

        SpittleController controller =
                new SpittleController(mockRepository);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setSingleView(
                        new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/spittles?max=238900&count=50"))
                .andExpect(MockMvcResultMatchers.view().name("spittles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
                .andExpect(MockMvcResultMatchers.model().attribute("spittleList", CoreMatchers.hasItems(expectedSpittles.toArray())));
    }

    @Test   //test passed
    public void testSpittleByParams() throws Exception{
        Spittle expectedSpittle = new Spittle("Hello", new Date());
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito.when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/spittles/show?spittle_id=12345"))
                .andExpect(MockMvcResultMatchers.view().name("spittle"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
                .andExpect(MockMvcResultMatchers.model().attribute("spittle", expectedSpittle));
    }

    @Test   //test passed
    public void testSpittleByPath() throws Exception{
        Spittle expectedSpittle = new Spittle("Hello", new Date());
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito.when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/spittles/12345"))
                .andExpect(MockMvcResultMatchers.view().name("spittle"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
                .andExpect(MockMvcResultMatchers.model().attribute("spittle", expectedSpittle));
    }

    @Test   //test passed
    public void shouldShowRegistration() throws Exception{

        SpitterRepository spitterRepository = Mockito.mock(SpitterRepository.class);
        SpitterController controller =  new SpitterController(spitterRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc
                .perform(MockMvcRequestBuilders.get("/spitter/register"))
                .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

    @Test
    public void shouldProcessRegistration() throws Exception{

        SpitterRepository spitterRepository = Mockito.mock(SpitterRepository.class);
        Spitter unsaved =
                new Spitter(
                        "jbauer",
                        "24hours",
                        "Jack",
                        "Bauer",
                        "q953387601@163.com");
        Spitter saved =
                new Spitter(
                        24L,
                        "jbauer",
                        "24hours",
                        "Jack",
                        "Bauer",
                        "q953387601@163.com");
        Mockito.when(spitterRepository.save(unsaved)).thenReturn(saved);

        SpitterController controller = new SpitterController(spitterRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/spitter/register")
                        .param("firstName", "Jack")
                        .param("lastName", "Bauer")
                        .param("username", "jbauer")
                        .param("password", "24hours")
                        .param("email", "q953387601@163.cn"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/jbauer"))
                .andExpect(MockMvcResultMatchers.model().attribute("spitter", unsaved));

        Mockito.verify(spitterRepository, Mockito.atLeastOnce()).save(unsaved);
    }

    private List<Spittle> createsSpittleList(int count){
        List<Spittle> spittles = new ArrayList<>();
        for (int spi = 0; spi < count; spi++) {
            spittles.add(new Spittle("Spittle" + spi, new Date()));
        }
        return spittles;
    }

}
