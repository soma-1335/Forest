package forest;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class ForestController extends eventModel {

	private ForestView forestView;

	private ForestModel forestModel;

	public void ForestController(ForestModel forestModel) {
		this.forestModel = forestModel;
	}

	@Override
	public void mouseEntered(MouseEvent aMouseEvent) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void mouseExited(MouseEvent aMouseEvent) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent aMouseEvent) {
		// TODO Auto-generated method stub
		Cursor aCursor = Cursor.getDefaultCursor();
		Component aComponent = (Component)aMouseEvent.getSource();
		aComponent.setCursor(aCursor);
		current = aMouseEvent.getPoint();
		previous = current;
		return;
		
	}	

	@Override
	public void mouseClicked(MouseEvent aMouseEvent) {
		// TODO Auto-generated method stub
		Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
		System.out.println(aPoint);
		return;
		
	}

	@Override
	public void mousePressed(MouseEvent aMouseEvent) {
		// TODO Auto-generated method stub
		Cursor aCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		Component aComponent = (Component)aMouseEvent.getSource();
		aComponent.setCursor(aCursor);
		current = aMouseEvent.getPoint();
		previous = current;
		return;
		
	}

	
}
