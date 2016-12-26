import java.io.PrintStream;

/**
 * Created by ChenZhePC on 2016/12/26.
 */
public class SlayDragonQuest implements Quest {

    private PrintStream stream;

    public SlayDragonQuest(PrintStream stream){this.stream = stream;}

    @Override
    public void embark() {
        stream.println("Embarking on quest to slay the dragon!");
    }
}
