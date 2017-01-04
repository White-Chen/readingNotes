package exercise24;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with Chen Zhe on 1/5/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Controller {
    private List<Event> eventList = new ArrayList<Event>();
    public void addEvent(Event e){eventList.add(e);}
    public void run(){
        while (eventList.size() > 0){
            // 这里因为需要使用remove方法，在foreach中，必须要重新建一个模板，否则运行时会报错
            for (Event event : new ArrayList<Event>(eventList)) {
                if(event.ready()){
                    System.out.println(event);
                    event.action();
                    eventList.remove(event);
                }
            }
        }
    }
}
