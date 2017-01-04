package exercise24;

/**
 * \* Created with Chen Zhe on 1/4/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public abstract class Event {
    // 事件开始时间
    private long eventTime;

    // 模拟事件开始运行前的延时时间
    protected final long delayTime;

    public Event(long delayTime) {
        this.delayTime = delayTime;
    }

    // 方法独立出来，方便重置事件时间，达到重用的目的
    public void start(){
        eventTime = System.nanoTime() + delayTime;
    }

    // 判断事件是否运行完毕，如果完毕返回true
    public boolean ready(){
        return System.nanoTime() >= eventTime;
    }

    // 抽象方法，定义事件的具体动作
    public abstract void action();
}
