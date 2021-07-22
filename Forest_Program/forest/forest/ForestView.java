package forest;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.List;

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
		nodeList.forEach(
			node -> {
				add(node);
			});
	}

}
