package forest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import java.awt.Point;

public class Node extends JLabel {

	private Integer deep;

	private String name;

	private List<Integer> numberList;

	private List<Integer> children;

	public Node(String deep, String name) {
		this.deep = Integer.valueOf(deep);
		this.name = name;
		setText(name);
		this.numberList = new ArrayList<Integer>();
		this.children = new ArrayList<Integer>();
	}

	public String getName() {
		return this.name;
	}

	public boolean addChildren(String children) {
		Integer child = Integer.valueOf(children);
		return this.children.add(child);
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

	public List<Integer> getChildren() {
		return this.children;
	}

}
