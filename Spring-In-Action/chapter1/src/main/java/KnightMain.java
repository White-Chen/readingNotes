import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ChenZhePC on 2016/12/26.
 */
public class KnightMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "META-INF/spring/knights.xml"
        );
        Knight knight = context.getBean(Knight.class);
        knight.embarkOnQuest();
        context.close();
    }
}
