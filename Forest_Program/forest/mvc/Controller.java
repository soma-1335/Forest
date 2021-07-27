package mvc;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputAdapter;

/**
 * コントローラ。制御まわりを専門に行う。
 */
public class Controller extends MouseInputAdapter implements MouseWheelListener
{
	/**
	 * 情報を握っているModelのインスタンスを束縛する。
	 */
	protected Model model;

	/**
	 * 表示を司るViewのインスタンスを束縛する。
	 */
	protected View view;

	/**
	 * 以前にマウスのボタンが押下された場所をPointのインスタンスとして束縛する。
	 */
	private Point previous;

	/**
	 * 現在にマウスのボタンが押下された場所をPointのインスタンスとして束縛する。
	 */
	private Point current;

	/**
	 * インスタンスを生成して応答する。
	 * すべてのインスタンス変数（model, view, previous, current）をnull化する。
	 */
	public Controller()
	{
		super();
		this.model = null;
		this.view = null;
		this.previous = null;
		this.current = null;
		return;
	}

	/**
	 * 指定されたマウスイベントからマウスカーサの位置を獲得して、
	 * モデル座標系でのクリック位置を割り出して標準出力に出力する。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseClicked(MouseEvent aMouseEvent)
	{
		Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
		System.out.println(aPoint);
		return;
	}

	/**
	 * マウスカーサの形状を移動の形に変化させ、指定されたマウスイベントからマウスカーサの位置を獲得して、
	 * インスタンス変数currentに設定すると共に、以前のマウスカーサの位置からの差分を計算する。
	 * そして、その差分だけビューに対してスクロールを依頼し、その後にビューの再描画を依頼する。
	 * 最後にインスタンス変数previousをインスタンス変数currentに更新する。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseDragged(MouseEvent aMouseEvent)
	{
		Cursor aCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		Component aComponent = (Component)aMouseEvent.getSource();
		aComponent.setCursor(aCursor);
		this.current = aMouseEvent.getPoint();
		Integer x = this.current.x - this.previous.x;
		Integer y = this.current.y - this.previous.y;
		Point aPoint = new Point(x, y);
		this.scrollBy(aPoint, aMouseEvent);
		this.previous = this.current;
		return;
	}

	/**
	 * 何もしない。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseEntered(MouseEvent aMouseEvent)
	{
		return;
	}

	/**
	 * 何もしない。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseExited(MouseEvent aMouseEvent)
	{
		return;
	}

	/**
	 * 何もしない。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseMoved(MouseEvent aMouseEvent)
	{
		return;
	}

	/**
	 * マウスカーサの形状を十字に変化させ、指定されたマウスイベントからマウスカーサの位置を獲得して、
	 * インスタンス変数currentに設定する共にインスタンス変数previousをインスタンス変数currentに更新する。
	 * @param aMouseEvent マウスイベント
	 */
	public void mousePressed(MouseEvent aMouseEvent)
	{
		Cursor aCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		Component aComponent = (Component)aMouseEvent.getSource();
		aComponent.setCursor(aCursor);
		this.current = aMouseEvent.getPoint();
		this.previous = this.current;
		return;
	}

	/**
	 * マウスカーサの形状をデフォルトに戻し、指定されたマウスイベントからマウスカーサの位置を獲得して、
	 * インスタンス変数currentに設定する共にインスタンス変数previousをインスタンス変数currentに更新する。
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseReleased(MouseEvent aMouseEvent)
	{
		Cursor aCursor = Cursor.getDefaultCursor();
		Component aComponent = (Component)aMouseEvent.getSource();
		aComponent.setCursor(aCursor);
		this.current = aMouseEvent.getPoint();
		this.previous = this.current;
		return;
	}

	/**
	 * 何もしない。
	 * @param aMouseWheelEvent マウスホィールイベント
	 */
	public void mouseWheelMoved(MouseWheelEvent aMouseWheelEvent)
	{
		return;
	}

	/**
	 * スクロール量を指定された座標分だけ相対スクロールして、ビューを再描画する。
	 * その際に、シフトキーが押下されていたら、モデルの依存物となっている全てのビューを連動させ、絶対スクロールを行う。
	 * さらに、メタ(command：⌘)キーが押下されていたら、全ビュー連動スクロールを、相対スクロールにて行う。
	 * @param aPoint X軸とY軸のスクロール量を表す座標
	 * @param aMouseEvent マウスイベントまたはマウスホィールイベント
	 */
	public void scrollBy(Point aPoint, MouseEvent aMouseEvent)
	{
		this.view.scrollBy(aPoint);
		this.view.repaint();
		Integer someModifiers = aMouseEvent.getModifiersEx();
		Boolean shiftDown = (someModifiers & InputEvent.SHIFT_DOWN_MASK) != 0;
		if (!shiftDown) { return; } // シフトキーが押下されていないので、全ビュー連動スクロールの必要なし。
		Point scrollAmount = this.view.scrollAmount();
		Point scrollOffset = new Point(0 - scrollAmount.x, 0 - scrollAmount.y);
		Boolean metaDown = (someModifiers & InputEvent.META_DOWN_MASK) != 0;
		for (View aView: view.model.dependents)
		{
			if (aView != this.view)
			{
				if (!metaDown) { aView.scrollBy(aPoint); }
				else { aView.scrollTo(scrollOffset); }
				aView.repaint();
			}
		}
		return;
	}

	/**
	 * 指定されたモデルをインスタンス変数modelに設定する。
	 * @param aModel このコントローラのモデル
	 */
	public void setModel(Model aModel)
	{
		this.model = aModel;
		return;
	}

	/**
	 * 指定されたビューをインスタンス変数viewに設定し、
	 * ビューのマウスのリスナおよびモーションリスナそしてホイールリスナをこのコントローラにする。
	 * @param aView このコントローラのビュー
	 */
	public void setView(View aView)
	{
		this.view = aView;
		this.view.addMouseListener(this);
		this.view.addMouseMotionListener(this);
		this.view.addMouseWheelListener(this);
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
		aBuffer.append(",view=");
		aBuffer.append(this.view);
		aBuffer.append("]");
		return aBuffer.toString();
	}
}
