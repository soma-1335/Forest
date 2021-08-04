package forest;

import java.awt.Point;
import java.awt.event.MouseEvent;
import mvc.Controller;

/**
 * コントローラー　イベント処理を主に行う
 */
public class ForestController extends Controller {


	private ForestModel forestModel;

	/**
	 * コンストラクター
	 */
	public ForestController(){
		super();
	}

	/**
	 * コンストラクター
	 * @param forestModel model
	 */
	public ForestController(ForestModel forestModel) {
		super();
		this.forestModel = forestModel;
	}

	/**
	 * マウスがクリックされた時の処理
	 */
	@Override
	public void mouseClicked(MouseEvent aMouseEvent) {
		Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
		ForestModel amodel = (ForestModel)this.model;
		amodel.mouseClicked(aPoint);
		return;
	}

}
