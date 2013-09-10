package tmp.j2d;

import java.awt.geom.Point2D;

public class FallPanel extends ThreadPanel {
	
	private static final long serialVersionUID = 1L;
	private double x;
	private double y;
	private Point2D circlePoint;
	private double r = 10.0;;
	private double xBeginValue;
	private double xEndValue;
	private double yBeginValue;
	private double yEndValue;

	public FallPanel(){
		super(600, 300);
		xBeginValue = r/2.0;
		xEndValue = this.getWidth() - r/2.0;
		yBeginValue = r/2.0;
		yEndValue = this.getHeight() - r/2.0;
	}

	@Override
	protected void call() throws InterruptedException {
		x = (xEndValue - xBeginValue)*Math.random() + xBeginValue;
		y = (yEndValue - yBeginValue)*Math.random() + yBeginValue;
		
		Point2D startPosition = new Point2D.Double(x, y);
	}

}

