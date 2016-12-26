import java.io.PrintStream;

/**
 * Created by ChenZhePC on 2016/12/26.
 */
public class Minstrel {

    private PrintStream stream;

    public Minstrel(PrintStream stream){
        this.stream = stream;
    }

    // 发生在knight响应quest前
    public void singBeforeQuest(){
        stream.println("Fa la la, the knight is so brave!");
    }

    // 发生在knight响应quest后
    public void singAfterQuest(){
        stream.println("Do la mi fa so, the brave knight " +
            "did embark on a quest！");
    }
}
