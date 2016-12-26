import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * \* Created with Chen Zhe on 12/25/2016.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class BraveKnightTest {

    @Test
    public void knightShouldEmbarkOnQuest(){
        // static导入
        //创建 mock Quest
        Quest mockQuest = mock(Quest.class);
        //注入 mock Quest
        BraveKnight knight = new BraveKnight(mockQuest);
        knight.embarkOnQuest();
        //验证方法被调用的次数为 1
        verify(mockQuest,times(1)).embark();
    }
}
