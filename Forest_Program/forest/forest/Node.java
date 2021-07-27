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

	public Point point;

	private List<Integer> numberList;

	private List<Integer> childrenNumber;

	public List<Node> children;

	private boolean compute = false;

	private Dimension size;

	private ForestModel listener;

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("Name =");
		stringBuilder.append(this.name);
		stringBuilder.append("  Deep =");
		stringBuilder.append(this.deep);
		stringBuilder.append("  number =");
		stringBuilder.append(this.numberList.get(0));
		stringBuilder.append("  haveChildren = ");
		stringBuilder.append(haveChildren());

		return stringBuilder.toString();
	}

	public Node(ForestModel listener, String deep, String name,int x, int y) {
		this.listener = listener;
		this.deep = Integer.valueOf(deep);
		this.name = name;
		this.point = new Point(x,y);
		setText(name);
		setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		setBorder(new LineBorder(Color.BLACK, 1, false));
		size = getPreferredSize();
		setBounds(point.x, point.y,size.width, size.height);
		this.numberList = new ArrayList<Integer>();
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

	public boolean addNumberList(Integer number) {
		return this.numberList.add(number);
	}

	public List<Integer> getNumberList() {
		return this.numberList;
	}

	public boolean sameNumberList(String numString){
		Integer num = Integer.valueOf(numString);
		for(Integer value : this.numberList)
		{
			if(value.equals(num)){return true;}
		}

		return false;
	}

	public boolean sameChildrenNumberList(Integer num){
		
		for(Integer value : this.childrenNumber)
		{
			if(value.equals(num)){return true;}
		}

		return false;
	}

	public List<Integer> getchildrenNumber(int deep) {
		return this.childrenNumber;
	}

	private boolean computed(){
		return compute;
	}

	private boolean haveChildren() {
		return !this.children.isEmpty();
	}

	public boolean computePoint(Point point) {

		// this.point = point;
		setBounds(point.x,point.y,size.width,size.height);
		// this.listener.changed();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// if(haveChildren()){
		// 	int yoffset =  (int)(xoffset * Math.tan(Math.toRadians(radian)));
		// 	Point point2 = new Point(point.x + xoffset,point.y - yoffset);
		// 	radian -= 8; 
		// 	for(Node node : children)
		// 	{
		// 		node.computePoint(point2,0,xoffset,radian);
		// 		point2.y += yoffset;
		// 	}
		// }
		
		compute = true;

		return compute;
	}

	public Integer getDeep(){
		return deep;
	}
}
