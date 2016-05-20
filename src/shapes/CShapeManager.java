package shapes;

import java.io.Serializable;

import Settings.Constants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class CShapeManager extends Canvas implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String body;
	
	public CShapeManager(){
		this.prefWidth(Constants.windowWidth);
		this.prefHeight(Constants.windowHeight);
	}
	public String getBody(){return body;}
	public void setBody(String body){ this.body = body;}
	public abstract void drawText(GraphicsContext gc);
//	protected EShapeType shapeType;
//	protected Point sw, ne;
//	
//	public CShapeManager(EShapeType shapeType) {
//		this.shapeType = shapeType;
//	}
//	
//	public void drawArrowHead(Graphics2D g, Point tip, Point tail, Color color) {
//		g.setPaint(color);
//        double dy = tip.y - tail.y;
//        double dx = tip.x - tail.x;
//        double theta = Math.atan2(dy, dx);
//        double x, y, rho = theta + Math.toRadians(30);
//        
//        for (int i = 0; i < 2; i++) {
//            x = tip.x - 10 * Math.cos(rho);
//            y = tip.y - 10 * Math.sin(rho);
//            g.draw(new Line2D.Double(tip.x, tip.y, x, y));
//            rho = theta - Math.toRadians(30);
//        }
//	}
//	
//	public EShapeType getShapeType() {	return shapeType;	}
//	
	public void draw(GraphicsContext gc) {};
}
