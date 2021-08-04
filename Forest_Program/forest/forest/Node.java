package forest;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;

/**
 * ノードの情報を格納するクラス
 */
@SuppressWarnings("serial")
public class Node extends JLabel {

	private Integer deep;

	private String name;

	private Point point;

	private Integer number;

	private List<Node> children;

	private Dimension size;

	private boolean initFlag = false;


	/**
	 * コンストラクター
	 * @param deep ノードの深さ
	 * @param name ノードの名前
	 * @param x  ノードの初期x座標
	 * @param y  ノードの初期y座標
	 */
	public Node(String deep, String name,int x, int y) {
		this.deep = Integer.valueOf(deep);
		this.name = name;
		this.point = new Point(x,y);
		setText(name);
		setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		setBorder(new LineBorder(Color.BLACK, 1, false));
		size = getPreferredSize();
		setBounds(point.x, point.y,size.width, size.height);
		this.children = new ArrayList<Node>();
	}

	/**
	 * このNodeの名前を応答する
	 * @return ノードの名前
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * このNodeに子要素を格納する
	 * @param このNodeの子要素
	 * @return　追加に成功したか
	 */
	public boolean addChildren(Node node) {
		children.add(node);
		return 	true;
	}

	/**
	 * このNodeを初期移動済みにする
	 * @return 初期化済みかのフラグ
	 */
	public boolean inited(){
		initFlag = true;
		return initFlag;
	}

	/**
	 * このNodeの初期移動済みかのフラグを応答する
	 * @return 初期化済みかのフラグ
	 */
	public boolean getFlag(){
		return initFlag;
	}

	/**
	 * このNodeの番号をセットする
	 * @param number このNodeの番号
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * 子要素のリストを応答する
	 * @return 子要素のリスト
	 */
	public List<Node> getChildren(){
		return children;
	}

	/**
	 * 子要素を持っているかを応答する
	 * @return 子要素を持っているか
	 */
	public boolean haveChildren() {
		return (this.children.isEmpty() || this.children.size() ==0) ? false : true;
	}

	/**
	 * このNodeの座標を変更する
	 * @param x x座標
	 * @param y　Y座標
	 * @return 成功したか
	 */
	public boolean computePoint(int x,int y) {
		this.point.x = x;
		this.point.y = y;
		return true;
	}

	/**
	 * このNodeの座標を応答する
	 * @return Nodeの座標
	 */
	public Point getPoint() {
		return this.point;
	}

	/**
	 * このNodeのサイズを応答する
	 * @return　このNodeのサイズ
	 */
	public Dimension getSize() {
		return this.size;
	}


	/**
	 * このオブジェクトを文字列にして応答する。
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("Name =");
		stringBuilder.append(this.name);
		stringBuilder.append("  Deep =");
		stringBuilder.append(this.deep);
		stringBuilder.append("  number =");
		stringBuilder.append(this.number);
		stringBuilder.append("  haveChildren = ");
		stringBuilder.append(haveChildren());

		return stringBuilder.toString();
	}
}
