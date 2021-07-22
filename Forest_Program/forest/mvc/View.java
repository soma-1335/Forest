package mvc;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * ビュー。表示まわりを専門に行う。
 */
@SuppressWarnings("serial")
public class View extends JPanel
{
	/**
	 * 情報を握っているModelのインスタンスを束縛する。
	 * 束縛されるModelのインスタンスはpicture()というメッセージに応答できなければならない。
	 */
	protected Model model;

	/**
	 * 制御を司るControllerのインスタンスを束縛する。
	 */
	protected Controller controller;

	/**
	 * スクロール量としてPointのインスタンスを束縛する。
	 */
	private Point offset;

	/**
	 * インスタンスを生成して応答する。
	 * 指定されたモデルの依存物となり、コントローラを作り、モデルとビューを設定し、スクロール量を(0, 0)に設定する。
	 * @param aModel このビューのモデル
	 */
	public View(Model aModel)
	{
		super();
		this.model = aModel;
		this.model.addDependent(this);
		this.controller = new Controller();
		this.controller.setModel(this.model);
		this.controller.setView(this);
		this.offset = new Point(0, 0);
		return;
	}

	/**
	 * インスタンスを生成して応答する。
	 * 指定されたモデルの依存物となり、指定されたコントローラにモデルとビューを設定し、スクロール量を(0, 0)に設定する。
	 * @param aModel このビューのモデル
	 * @param aController このビューのコントローラ
	 */
	public View(Model aModel, Controller aController)
	{
		super();
		this.model = aModel;
		this.model.addDependent(this);
		this.controller = aController;
		this.controller.setModel(this.model);
		this.controller.setView(this);
		this.offset = new Point(0, 0);
		return;
	}

	/**
	 * 指定されたグラフィクスに背景色（明灰色）でビュー全体を塗り、その後にモデルの内容物を描画する。
	 * それはスクロール量（offset）を考慮してモデル画像（picture）をペイン（パネル）内に描画することである。
	 * @param aGraphics グラフィックス・コンテキスト
	 */
	public void paintComponent(Graphics aGraphics)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		aGraphics.setColor(Color.lightGray);
		aGraphics.fillRect(0, 0, width, height);
		if (this.model == null) { return; }
		BufferedImage anImage = this.model.picture();
		if (anImage == null) { return; }
		aGraphics.drawImage(anImage, this.offset.x, this.offset.y, null);
		return;
	}

	/**
	 * スクロール量（offsetの逆向きの大きさ）を応答する。
	 * @return X軸とY軸のスクロール量を表す座標
	 */
	public Point scrollAmount()
	{
		int x = 0 - this.offset.x;
		int y = 0 - this.offset.y;
		return (new Point(x, y));
	}

	/**
	 * スクロール量を指定された座標分だけ相対スクロールする。
	 * @param aPoint X軸とY軸のスクロール量を表す座標
	 */
	public void scrollBy(Point aPoint)
	{
		int x = this.offset.x + aPoint.x;
		int y = this.offset.y + aPoint.y;
		this.scrollTo(new Point(x, y));
		return;
	}

	/**
	 * スクロール量を指定された座標に設定（絶対スクロール）する。
	 * @param aPoint X軸とY軸の絶対スクロール量を表す座標
	 */
	public void scrollTo(Point aPoint)
	{
		this.offset = aPoint;
		return;
	}

	/**
	 * このインスタンスを文字列にして応答する。
	 * @return 自分自身を表す文字列
	 */
	public String toString()
	{
		StringBuffer aBuffer = new StringBuffer();
		Class<?> aClass = this.getClass();
		aBuffer.append(aClass.getName());
		aBuffer.append("[model=");
		aBuffer.append(this.model);
		aBuffer.append(",offset=");
		aBuffer.append(this.offset);
		aBuffer.append("]");
		return aBuffer.toString();
	}

	/**
	 * ビューの全領域を再描画する。
	 */
	public void update()
	{
		this.repaint(0, 0, this.getWidth(), this.getHeight());
		return;
	}
}
