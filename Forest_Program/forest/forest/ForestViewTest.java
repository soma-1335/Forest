package forest;

// 単体テストのためにJUnitを用いることを宣言しておく。
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForestViewTest {
    @Test
	@DisplayName("クラス「View」の単体テスト")
	public void testView()
	{
		String aString = "京都産業大学";
		assertEquals(aString.charAt(2), '産');
	}
}
