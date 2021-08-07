package forest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
// 単体テストのためにJUnitを用いることを宣言しておく。
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForestModelTest {
    @Test
	@DisplayName("クラス「Model」の単体テスト")
	public void testModel()
	{
        File file = new File("resource/data/test.txt");
        ForestModel model = new ForestModel(file);
        List<Node> list = new ArrayList<Node>();
        Map<Integer,Node> map = new TreeMap<Integer,Node>();
		assertEquals(model.getNodeMap(), map);
	}
}
