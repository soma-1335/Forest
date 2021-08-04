package forest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;

public class Node extends JLabel {

	private Integer deep;

	private String name;

	private Point point;

	private Integer number;

	private List<Integer> childrenNumber;

	private List<Node> children;

	private boolean compute = false;

	private Dimension size;

	private boolean initFlag = false;

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

	public Node(String deep, String name,int x, int y) {
		this.deep = Integer.valueOf(deep);
		this.name = name;
		this.point = new Point(x,y);
		setText(name);
		setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		setBorder(new LineBorder(Color.BLACK, 1, false));
		size = getPreferredSize();
		setBounds(point.x, point.y,size.width, size.height);
		this.childrenNumber = new ArrayList<Integer>();
		this.children = new ArrayList<Node>();
	}

	public String getName() {
		return this.name;
	}

	public boolean addChildren(Node node) {
		children.add(node);
		return 	true;
	}

	public boolean addchildrenNumber(String childrenNumber) {
		String[] childrenNumbers = childrenNumber.split(",");
		for(String child : childrenNumbers){
			Integer number = Integer.valueOf(child);
			this.childrenNumber.add(number);
		}
		return true;
	}

	public boolean inited(){
		initFlag = true;
		return initFlag;
	}

	public boolean getFlag(){
		return initFlag;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return this.number;
	}

	public boolean sameNumber(String numString){
		Integer num = Integer.valueOf(numString);
		return this.number.equals(num)? true : false;
	}

	public boolean sameChildrenNumberList(Integer num){
		
		for(Integer value : this.childrenNumber)
		{
			if(value.equals(num)){return true;}
		}
		return false;
	}

	public List<Node> getChildren(){
		return children;
	}

	public List<Integer> getchildrenNumber(int deep) {
		return this.childrenNumber;
	}

	public boolean haveChildren() {
		return (this.children.isEmpty() || this.children.size() ==0) ? false : true;
	}

	public boolean computePoint(int x,int y) {
		this.point.x = x;
		this.point.y = y;
		return true;
	}

	public Point getPoint() {
		return this.point;
	}

	public Dimension getSize() {
		return this.size;
	}

	public Integer getDeep(){
		return deep;
	}
}
