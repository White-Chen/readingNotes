package autoconfig;

import org.springframework.stereotype.Component;

/**
 * Created by ChenZhePC on 2016/12/27.
 */

// CompactDisc接口实现类
@Component("musicClub") //声明当前类是组件，会被自动扫描
//spring支持将其作为@Component注解的替代方案，有细微差别，但绝大多数场景可以替换，最好还是用@Component
//@Named("musicClub")
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The beatles";

    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
