package mvc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * モデル。データ管理を専門に行う。
 */
public class Model extends Object
{
	/**
	 * 依存物（Observerデザインパターンの観測者）：Viewのインスタンスたちを束縛する。
	 */
	protected ArrayList<View> dependents;

	/**
	 * 内容物として画像を束縛する。
	 */
	private BufferedImage picture;

	/**
	 * インスタンスを生成して初期化して応答する。
	 */
	public Model()
	{
		super();
		this.initialize();
		return;
	}

	/**
	 * 指定されたビューを依存物に設定する。
	 * @param aView このモデルの依存物となるビュー
	 */
	public void addDependent(View aView)
	{
		this.dependents.add(aView);
		return;
	}

	/**
	 * モデルの内部状態が変化していたので、自分の依存物へupdateのメッセージを送信する。
	 */
	public void changed()
	{
		Iterator<View> anIterator = this.dependents.iterator();
		while (anIterator.hasNext())
		{
			View aView = anIterator.next();
			aView.update();
		}
		return;
	}

	/**
	 * 初期化する。
	 */
	private void initialize()
	{
		this.dependents = new ArrayList<View>();
		this.picture = null;
		return;
	}

	/**
	 * 何もしない。
	 */
	public void perform()
	{
		return;
	}

	/**
	 * 画像（モデルの内容物）を応答する。
	 * @return このモデルのpictureフィールドに格納されている画像
	 */
	public BufferedImage picture()
	{
		return this.picture;
	}

	/**
	 * 画像（モデルの内容物）を設定する。
	 * @param anImage このモデルのpictureフィールドに格納する画像
	 */
	public void picture(BufferedImage anImage)
	{
		this.picture = anImage;
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
		aBuffer.append("[picture=");
		aBuffer.append(this.picture);
		aBuffer.append("]");
		return aBuffer.toString();
	}
}
