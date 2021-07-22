package mvc;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * 例題プログラム。
 */
public class Example extends Object
{
	/**
	 * 画面をキャプチャして画像化し、ビューとコントローラの3つのペア
	 *（MVC-1, MVC-2, MVC-3のウィンドウたち）から1つのモデルを観測している状態を作り出す。
	 * その後、モデルの内容物を先ほどキャプチャした画像にして、
	 * 自分が変化したと騒いだ瞬間、MVC-1, MVC-2, MVC-3のすべてのウィンドウが更新される。
	 * そして、モデルの内容物をnull化して、自分が変化したと騒ぎ、すべてのウィンドウが空に更新される。
	 * この過程を何回か繰り返すことで、MVC: Model-View-Controller（Observerデザインパターン）が
	 * きちんと動いているかを確かめる例題プログラムである。
	 * @param arguments 引数の文字列の配列
	 */
	public static void main(String[] arguments)
	{
		// スクリーンのサイズを求め、スクリーン全体をキャプチャ（画像に）する。
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Robot aRobot = null;
		try
		{
			aRobot = new Robot();
		}
		catch (Exception anException)
		{
			System.err.println(anException);
			throw new RuntimeException(anException.toString());
		}
		BufferedImage anImage = aRobot.createScreenCapture(new Rectangle(screenSize));

		// ウィンドウのサイズを決め、モデルを作る。
		Dimension aDimension = new Dimension(800, 600);
		Model aModel = new Model();

		// MVCの出現数から、最初のウィンドウの出現位置を計算する。
		Integer howMany = 3; // MVCの出現回数
		Point offsetPoint = new Point(80, 60); // ウィンドウを出現させる時のオフセット(ズレ：ずらし)
		Integer width = aDimension.width + (offsetPoint.x * (howMany - 1));
		Integer height = aDimension.height + (offsetPoint.y * (howMany - 1));
		Integer x = (screenSize.width / 2) - (width / 2);
		Integer y = (screenSize.height / 2) - (height / 2);
		Point displayPoint = new Point(x, y);

		// MVCを出現回数分だけ出現させる。
		for (Integer index = 0; index < howMany; index++)
		{
			// 上記のモデルのビューとコンピュローラのペアを作り、ウィンドウに乗せる。
			View aView = new View(aModel);
			JFrame aWindow = new JFrame("MVC-" + Integer.toString(index + 1));
			aWindow.getContentPane().add(aView);

			// 高さはタイトルバーの高さを考慮してウィンドウの大きさを決定する。
			aWindow.addNotify();
			Integer titleBarHeight = aWindow.getInsets().top;
			width = aDimension.width;
			height = aDimension.height + titleBarHeight;
			Dimension windowSize = new Dimension(width, height);
			aWindow.setSize(windowSize.width, windowSize.height);

			// ウィンドウに各種の設定を行って出現させる。
			aWindow.setMinimumSize(new Dimension(400, 300 + titleBarHeight));
			aWindow.setResizable(true);
			aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			x = displayPoint.x + (index * offsetPoint.x);
			y = displayPoint.y + (index * offsetPoint.y);
			aWindow.setLocation(x, y);
			aWindow.setVisible(true);
			aWindow.toFront();
		}

		// モデルのピクチャを、奇数の時はnullに、偶数の時はスクリーン全体のキャプチャ画像にする。
		for (Integer index = 0; index < (howMany * 4 - 1); index++)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException anException)
			{
				System.err.println(anException);
				throw new RuntimeException(anException.toString());
			}
			if (index % 2 == 0)
			{
				aModel.picture(anImage);
			}
			else
			{
				aModel.picture(null);
			}
			aModel.changed();
		}

		return;
	}
}
