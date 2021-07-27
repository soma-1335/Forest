package forest;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import mvc.View;

public class ForestView extends View {

	ForestModel model;

	public ForestView(ForestModel aModel){
		super(aModel,new ForestController());
		model = aModel;
	}

	public ForestView(ForestModel aModel, ForestController aController) {
		super(aModel,aController);
	}

	public void paintComponent(Graphics aGraphics) {
		
		List<Node> nodeList = model.getNodeList();
		Map<Integer,Node> nodeMap = model.nodeMap;
		Map<String,String> linkMap = model.linkMap;

		for(Node node : nodeList){
			add(node);
			System.out.println(node);
			String[] strs = linkMap.get(node.getNumber().toString()).split(",");
			for(String str : strs){
				Node child = nodeMap.get(Integer.valueOf(str));
				aGraphics.drawLine(node.point.x, node.point.y, child.point.x, child.point.y);
			}
		}



		// nodeList.forEach(
		// 	node -> 
		// 	{
		// 		add(node);
		// 		linkMap.get(Integer.valueOf(node.getNumber()))
		// 	});
	}

}
