package forest;

// 単体テストのためにJUnitを用いることを宣言しておく。
import org.junit.jupiter.api.Test;

import java.awt.Point;
import forest.Node;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {
    @Test
	@DisplayName("クラス「Model」の単体テスト")
	public void testNode()
	{
        Node node1 = new Node("1", "test1", 100, 100);
        Node node2 = new Node("2", "test2", 200, 200);
        assertEquals(node1.getName(), "test1");
        assertEquals(node1.addChildren(node2), true);
        assertEquals(node1.getFlag(), false);
        assertEquals(node1.inited(), true);
        assertEquals(node1.getChildren().get(0).equals(node2), true);
        assertEquals(node1.haveChildren(), true);
        assertEquals(node1.getPoint(), new Point(100, 100));
	}

}
