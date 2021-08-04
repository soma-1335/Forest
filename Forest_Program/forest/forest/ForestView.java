package forest;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Map;

import mvc.View;

public class ForestView extends View {

	ForestModel model;

	ForestController controller;

	public ForestView(ForestModel aModel){
		super(aModel,new ForestController());
		model = aModel;
		
	}


	public ForestView(ForestModel aModel, ForestController aController) {
		super(aModel,aController);
		model = aModel;
		controller = aController;
	}

	public void paintComponent(Graphics aGraphics) {
		int width = this.getWidth();
		int height = this.getHeight();
		aGraphics.setColor(Color.white);
		aGraphics.fillRect(0, 0, width, height);
		Map<Integer,Node> map = model.getNodeMap();
		Point point = scrollAmount();

		for(var entrySet : map.entrySet()){
			
			Node node = entrySet.getValue();
			Point point2 = node.getPoint();
			node.setBounds(point2.x - point.x,point2.y - point.y,node.getSize().width,node.getSize().height);
			for(Node child : node.getChildren()){
				Point point3 = child.getPoint();
				aGraphics.setColor(Color.BLACK);
				aGraphics.drawLine(point2.x + node.getSize().width - point.x,point2.y + node.getSize().height/2 - point.y,point3.x - point.x,point3.y - point.y);
			}
			this.add(node);
		}
	}

}
