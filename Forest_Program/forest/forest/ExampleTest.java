package forest;

// 単体テストのためにJUnitを用いることを宣言しておく。
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 例題をJUnitを用いて単体テストするクラスである。
 */
public class ExampleTest
{
	/**
	 * このクラスExampleにおける単体テストのメソッドであることを「@Test」のアノテーションで表す。
	 */
	@Test
	@DisplayName("クラス「Example」の単体テスト")
	public void testExample()
	{
		String aString = "京都産業大学";
		assertEquals(aString.charAt(2), '産');
		// assertEquals(aString.charAt(2), '業');
	}
}