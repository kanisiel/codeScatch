package shapes;

import java.util.LinkedHashMap;
import java.util.Map;

import Settings.Constants;
import javafx.geometry.Point2D;

public class Anchor {
	public Map<String, Point2D> anchors;

	public Anchor(Point2D north, Point2D east, Point2D south, Point2D west) {
		super();
		this.anchors = new LinkedHashMap<>();
		this.anchors.put(Constants.NORTH, north);
		this.anchors.put(Constants.EAST, east);
		this.anchors.put(Constants.SOUTH, south);
		this.anchors.put(Constants.WEST, west);
	}
	public Point2D getAnchor(String cardinal){
		return this.anchors.get(cardinal);
	}
	
	
}
