package application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.text.BadLocationException;

import Settings.CConstants;
import Settings.Constants;
import Settings.Windows.InternalWindows;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jfxtras.scene.control.window.Window;
import shapes.CArrowHead;
import shapes.CConnectManager;
import shapes.CIfManager;
import shapes.CIteratorManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CShapeNode;

public class FlowChartCanvas extends BorderPane {
	private Canvas canvas;
	private FlowChartManager manager;
	private Point2D startPoint;
	private CRootManager root;
	private Map<String, Double> boundaryWidth;
	private Map<String, Double> layoutY;
	private Map<String, Double> lastLowerAnchor;
	private double centerLineX;
	public double height = Constants.windowHeight-30;
	private DesktopPaneController parent;
	private Vector<Object> tags;
	private int trimmed;
	
	public FlowChartCanvas(DesktopPaneController parent) {
		this.parent = parent;

//    	this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		this.manager = new FlowChartManager();
		this.boundaryWidth = new LinkedHashMap<>();
		this.layoutY = new LinkedHashMap<>();
		this.lastLowerAnchor = new LinkedHashMap<>();
		this.tags = new Vector<>();
		canvas = new Canvas();
		startPoint = new Point2D(0, 0);
		this.setPrefSize(Constants.windowWidth, Constants.windowHeight-30);
		this.centerLineX = this.getPrefWidth()/2;
		//Initialize canvas component;
		this.manager.initManager();		
	}
	public void init(){
		this.manager.initManager();
	}
	public void setRootNode(CRootManager root){
		this.root = root;
		redraw(root);
	}
	public void clearCanvas(){
		this.manager = new FlowChartManager();
		this.boundaryWidth = new LinkedHashMap<>();
		this.layoutY = new LinkedHashMap<>();
		this.lastLowerAnchor = new LinkedHashMap<>();
		this.tags = new Vector<>();
		canvas = new Canvas();
		this.getChildren().clear();
		Window w = parent.getWindows().get(InternalWindows.Flow.getTitle());
		if(w != null){
			this.setPrefHeight(w.getPrefHeight());
			if(this.getPrefHeight()>=w.getPrefHeight()-30){
				this.setPrefHeight(w.getPrefHeight()-30);
			}
		} else {
			this.setPrefHeight(Constants.canvasHeight);
		}
		
//		
	}
	public void addShape(CShapeManager csm){
		this.manager.addNode(csm);
		setCoord(csm);
		reDraw();
		
	}
	public void setCoord(CShapeManager csm){
		Point2D p = this.manager.findPrev(csm).getP();
		Dimension2D d = this.manager.findPrev(csm).getD();
		Dimension2D w = new Dimension2D(this.getWidth(), this.getHeight());
		
		csm.setCoords(p, d, w);
	}
	public void reDraw(){
		clearCanvas();
		this.manager.getNodes().clear();
		drawAllNodes(root);
	}
	public void redraw(CRootManager root){
		clearCanvas();
		this.manager.getNodes().clear();
		this.root = root;
		drawAllNodes(root);
	}
	public void setAllNodes(Vector<CShapeNode> root){
		
	}
	public void setNodes(Vector<CShapeNode> nodes){
		for(CShapeNode node : nodes){
			this.boundaryWidth.put(node.getType()+node.getDepth(), 0.0);
			if(node.getchildNum()>0){
				setNodes(node.getNodes());
			}else {
				draw(node);
			}
		}
	}
	public void set(CShapeNode node){
		CShapeManager shape = node.getShape();
		this.manager.addNode(shape);
		setCoord(shape);
	}
	public void drawAllNodes(CRootManager root){
		drawNodes(root);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drawBounds(root);
		drawConnects(root);
		addToThis();
	}
	public void drawNodes(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		this.boundaryWidth.put(root.getType()+root.getDepth(), 0.0);
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				this.boundaryWidth.put(node.getType()+node.getDepth(), 0.0);
				this.layoutY.put(node.getType()+node.getDepth(), 0.0);
				this.lastLowerAnchor.put(node.getType()+node.getDepth(), 0.0);
				drawNodes(node);
			}else {
				draw(node);
			}
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

	
	public void drawBounds(CShapeNode root){
//		Vector<CShapeNode> nodes = ;
//		System.out.println(root.getClass()+" > "+root.getType()+"("+root.getchildNum()+")");
		for(CShapeNode node : root.getNodes()){
			drawBounds(node);
			drawBound(node);
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	public double largestWidth(CShapeNode parent){
		double rv = 0;
		for(CShapeNode node : parent.getNodes()){
//			System.out.println(node.getParent().getType() +" : " + node.getType() + "("+parent.getNodes().indexOf(node)+"/"+(parent.getchildNum()-1)+")");
			if(node.getClass().equals(CIteratorManager.class)||node.getClass().equals(CIfManager.class)){
				if(node.getType().equals(CConstants.ELSEIF)){
					if(parent.getType().equals(CConstants.IF)||parent.getType().equals(CConstants.ELSEIF)){
						largestWidth(node);
					}
				}else {
					if(rv<=node.getBoundWidth()){
						rv = node.getBoundWidth()+30;
					}
				}
			} else {
				StackPane sp = find(node);
				if(rv < sp.getPrefWidth()){
					rv = sp.getPrefWidth();
				
				}
			}
		}
		return rv;
	}
	public CShapeNode findlast(CShapeNode node){
		
		CShapeNode n = node.getNodes().get(node.getchildNum()-1);
		if(n.getType().equals(CConstants.ELSEIF)){
			if(node.getType().equals(CConstants.IF)||node.getType().equals(CConstants.ELSEIF)){
				return node.getNodes().get(node.getchildNum()-2);
			}
		}
		if(n.getchildNum()>0){
			return findlast(n);
		} else {
			return n;
		}
		
	}
	public void drawBound(CShapeNode node){
//		System.out.println(node.getType());
		if(node.getClass().equals(CIteratorManager.class)||node.getClass().equals(CIfManager.class)){
//			System.out.println(node.getType());
			double width = 0;
			double height = 0;
			int childNum = node.getchildNum();
			CShapeNode first = node.getNodes().get(0);
			CShapeNode last = findlast(node);
//			System.out.println("first: "+first.shape.sid);
//			System.out.println("last: "+last.shape.sid);
//			System.out.println(childNum);
//			System.out.println();
			StackPane fsp = first.getSp();
			StackPane lsp = last.getSp();
			Color fill;
			Color stroke;
			double upperAnchor = fsp.getLayoutY()-(fsp.getPrefHeight()/2), lowerAnchor = (lsp.getLayoutY()+(lsp.getPrefHeight()/2));
			width = largestWidth(node);
			height = (lsp.getLayoutY()+(lsp.getPrefHeight()/2)) - (fsp.getLayoutY()-(fsp.getPrefHeight()/2));
			Rectangle rect = new Rectangle();
			rect.setWidth(width+50);
			rect.setHeight(height+20);
			rect.setOpacity(0.4);
			rect.setFill(getFill(node.getType()));
			rect.setStroke(getStroke(node.getType()));
			rect.setStrokeWidth(2);
			rect.setLayoutX(centerLineX-(rect.getWidth()/2));
			rect.setLayoutY(upperAnchor-10);
			Label label = new Label(node.getType());
			label.setLayoutX(centerLineX-(rect.getWidth()/2)+5);
			label.setLayoutY(upperAnchor-25);
			node.setBoundWidth(rect.getWidth());
//			if(rect.getWidth()/2 > this.boundaryWidth.get(node.getType()+node.getDepth())){
//				this.boundaryWidth.replace(node.getType()+node.getDepth(), sp.getPrefWidth()/2);
//			}
			Group bound = new Group();
			bound.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			       rect.setStroke(Color.RED);
			       int[] lines = node.lines;
			       for(int i = lines[0]; i <= lines[1]; i++){
			    	   try {
			    		   Object tag = parent.textArea.addLineHighlight(i+trimmed, java.awt.Color.ORANGE);
			    		   tags.add(tag);
			    	   } catch (BadLocationException e) {
			    		   // TODO Auto-generated catch block
			    		   e.printStackTrace();
			    	   }
			       }
//			       Node n = lsp.getChildren().get(0);
////			       System.out.println(n.getClass().toString());
////			       System.out.println(Polygon.class.toString());
//			       Class<? extends Node> c = n.getClass();
//			       if(c.equals(Rectangle.class)){
//			    	   Rectangle r = (Rectangle) n;
//			    	   r.setStroke(Color.RED);
//			       } else if(c.equals(Polygon.class)){
//			    	   Polygon p = (Polygon)n;
//			    	   p.setStroke(Color.RED);
//			       }
			    }
			});
			bound.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			       rect.setStroke(getStroke(node.getType()));
			       for(Object tag : tags){
			    	   parent.textArea.removeLineHighlight(tag);
			       }
			       tags.clear();
//			       Node n = lsp.getChildren().get(0);
////			       System.out.println(n.getClass().toString());
////			       System.out.println(Polygon.class.toString());
//			       Class<? extends Node> c = n.getClass();
//			       if(c.equals(Rectangle.class)){
//			    	   Rectangle r = (Rectangle) n;
//			    	   r.setStroke(getStroke(node.getType()));
//			       } else if(c.equals(Polygon.class)){
//			    	   Polygon p = (Polygon)n;
//			    	   p.setStroke(getStroke(node.getType()));
//			       }
			    }
			});		
			bound.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        if(event.isPrimaryButtonDown()){
			        	System.out.println(last.shape.sid);
//			        	int[] lines = node.lines;
//			        	System.out.println(trimmed);
//			        	System.out.println(lines[0]+"~"+lines[1]);
//			        	System.out.println((lines[0]+trimmed)+"~"+(lines[1]+trimmed));
//			        	System.out.println();
	//		        	System.out.println("Height : "+sp.getPrefHeight());
	//		        	System.out.println("Sum : "+(sp.getLayoutY()+sp.getPrefHeight()));
	//		        	Text t = (Text)sp.getChildren().get(1);
	//		    		System.out.println(t.getWidth());
			        }
			    }
			});
			bound.getChildren().addAll(rect,label);
			StackPane sp = new StackPane(bound);
			sp.setUserData(0);
			sp.layoutXProperty().bind(this.prefWidthProperty().divide(2));
			sp.setLayoutY(upperAnchor+(height/2)-10);
			this.getChildren().add(1, sp);
			
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	private Paint getFill(String type){
		Paint rv;
		switch(type){
		case CConstants.FOR:
			rv = Color.ROYALBLUE;
			break;
		case CConstants.DO:
			rv = Color.DARKKHAKI;
			break;
		case CConstants.WHILE:
			rv = Color.OLIVE;
			break;
		case CConstants.IF:
			rv = Color.ORCHID;
			break;
		case CConstants.ELSEIF:
			rv = Color.CADETBLUE;
			break;
		default:
			rv = Color.MOCCASIN;
			break;
		}
		return rv;
	}
	private Paint getStroke(String type){
		Paint rv;
		switch(type){
		case CConstants.FOR:
			rv = Color.DARKBLUE;
			break;
		case CConstants.DO:
			rv = Color.DARKGREEN;
			break;
		case CConstants.WHILE:
			rv = Color.DARKOLIVEGREEN;
			break;
		case CConstants.IF:
			rv = Color.DARKORCHID;
			break;
		case CConstants.ELSEIF:
			rv = Color.DARKCYAN;
			break;
		default:
			rv = Color.ORANGE;
			break;
		}
		return rv;
	}
	
	public void draw(CShapeNode node){
		CShapeManager shape = node.getShape();
		this.manager.addNode(shape);
//		setCoord(shape);
		StackPane sp = node.getSp();
		if(checkBlock(node)){
			sp.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			       Shape s = (Shape) sp.getChildren().get(0);
			       s.setStroke(Color.RED);
			       int[] lines = node.lines;
			       for(int i = lines[0]; i <= lines[1]; i++){
			    	   try {
			    		   Object tag = parent.textArea.addLineHighlight(i+trimmed, java.awt.Color.ORANGE);
			    		   tags.add(tag);
			    	   } catch (BadLocationException e) {
			    		   // TODO Auto-generated catch block
			    		   e.printStackTrace();
			    	   }
			       }
			    }
			});
			sp.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			       Shape s = (Shape) sp.getChildren().get(0);
			       s.setStroke(Color.BLACK);
			       for(Object tag : tags){
			    	   parent.textArea.removeLineHighlight(tag);
			       }
			       tags.clear();
			    }
			});
		}
		if(this.getChildren().size()<1){
			sp.setLayoutY(30);
		}else {
			StackPane prev = (StackPane) this.getChildren().get(this.getChildren().size()-1);
			
			if(node.getType().equals(Constants.EShapeType.STOP.name())){
				if(prev.getLayoutY()<this.getPrefHeight()-30){
					sp.setLayoutY(this.getPrefHeight()-30);	
					Window w = parent.getWindows().get(InternalWindows.Flow.getTitle());
					height = w.heightProperty().get();
				} else {
					sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
					height = sp.getLayoutY()+sp.getPrefHeight();
					this.setPrefHeight(height);
				}
			} else {
				if(isRoot(node.getParent())){
					if(node.getParent().findPrev(node).getClass().equals(CIteratorManager.class)){
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					} else if(node.getParent().findPrev(node).getType().equals(CConstants.ELSE)){
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					} else if(node.getParent().findPrev(node).getType().equals(CConstants.IF)){
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					} else {
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					}
				}else{
					if(node.getParent().getParent().findPrev(node.getParent()).getType().equals(CConstants.ELSE)){
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					} else if(node.getParent().getParent().findPrev(node.getParent()).getType().equals(CConstants.IF)){
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+60);
					} else {
						sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
					}
				}
			}
			if(root.findNode(node)==1&&!node.getType().equals(Constants.EShapeType.STOP.name())){
				sp.setLayoutY(150);
//				sp.getChildren().set(0, shape.getShape());
			}
		}
//			System.out.println(sp.getUserData());
		if(node.getParent().findNode(node)==0){
			this.layoutY.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()-15);
		}
		if(node.getParent().findNode(node) == (node.getParent().getNodes().size()-1)){
			this.lastLowerAnchor.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()+sp.getPrefHeight()+15);
		}
		double bw = this.boundaryWidth.get(node.getParent().getType()+node.getParent().getDepth());
		
		if(node.getParent().findNode(node)>=0){
			this.boundaryWidth.put(node.getParent().getType()+node.getParent().getDepth(), sp.getPrefWidth()/2);
		}else {
			if(sp.getPrefWidth()/2 > bw){
				this.boundaryWidth.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getPrefWidth()/2);
			}
		}

		sp.layoutXProperty().bind(this.prefWidthProperty().divide(2));
		this.getChildren().add(sp);
		
	}
	public void drawOrthogoral(CShapeNode node, StackPane fromPane, StackPane toPane, String to){
		StackPane sp = new StackPane();
		Group arrow = new Group();
		CConnectManager cm = new CConnectManager();
		CArrowHead lh = new CArrowHead();
		Text text = new Text("");
		double gap = boundaryWidth.get(node.getType()+node.getDepth())/2+30;
//		double gap = node.getBoundWidth()/2+10;
		double height = 0;
		SimpleDoubleProperty sdpw = new SimpleDoubleProperty(0.0);
		if(to.equals(Constants.NO)){
			cm.setNoConn(fromPane, toPane, gap);
			lh.setHead(Constants.SOUTH, new Point2D(toPane.getLayoutX(), toPane.getLayoutY()-(toPane.getPrefHeight()/2)));
			text = new Text(Constants.NO);
			text.setX(fromPane.getLayoutX()+(fromPane.getPrefWidth()/2)+10);
			text.setY(fromPane.getLayoutY()-10);
			height = toPane.getLayoutY()-fromPane.getLayoutY();
			sdpw.add(fromPane.layoutXProperty()).add(sp.getPrefWidth()/2);
		} else if(to.equals(CConstants.ELSE)){
			cm.setElse(fromPane, toPane, gap);
			lh.setHead(Constants.SOUTH, new Point2D(toPane.getLayoutX(), toPane.getLayoutY()-(toPane.getPrefHeight()/2)));
			height = toPane.getLayoutY()-fromPane.getLayoutY();
		} else if(to.equals(CConstants.ITERATIONSTATEMENT)){
			cm.setIter(fromPane, toPane, gap);
			lh.setHead(Constants.EAST, new Point2D(toPane.getLayoutX()-(toPane.getPrefWidth()/2), toPane.getLayoutY()));
			height = fromPane.getLayoutY()-toPane.getLayoutY();
		} else if(to.equals(CConstants.DO)){
			cm.setDo(fromPane, toPane, gap);
			lh.setHead(Constants.EAST, new Point2D(toPane.getLayoutX()-(toPane.getPrefWidth()/2), toPane.getLayoutY()));
			height = fromPane.getLayoutY()-toPane.getLayoutY();
		}
		Shape line = cm.getShape();
		Shape head = lh.getShape();
		arrow.getChildren().addAll(line, head);
		this.manager.getConnects().put(node.getType()+node.getDepth(), arrow);
		sp.getChildren().addAll(arrow);
		sp.setPrefWidth(gap);
		sp.setPrefHeight(height);
		if(to.equals(Constants.NO)){
			sp.layoutXProperty().bind(fromPane.layoutXProperty().add(gap-5));
			sp.layoutYProperty().bind(fromPane.layoutYProperty().add((sp.getPrefHeight()/2)));
		} else if(to.equals(CConstants.ELSE)){
			sp.layoutXProperty().bind(fromPane.layoutXProperty().subtract(sp.getPrefWidth()/2));
			sp.layoutYProperty().bind(fromPane.layoutYProperty().add(sp.getPrefHeight()/2));
		} else if(to.equals(CConstants.ITERATIONSTATEMENT)){
			sp.layoutXProperty().bind(fromPane.layoutXProperty().subtract(gap));
			sp.layoutYProperty().bind(fromPane.layoutYProperty().subtract(sp.getPrefHeight()/2-15));
		} else if(to.equals(CConstants.DO)){
			sp.layoutXProperty().bind(fromPane.layoutXProperty().subtract(sp.getPrefWidth()/2));
			sp.layoutYProperty().bind(fromPane.layoutYProperty().subtract(sp.getPrefHeight()/2));
		}
		StackPane tsp = new StackPane(text);
		tsp.layoutXProperty().bind(sp.layoutXProperty().add(gap+30));
		tsp.layoutYProperty().bind(sp.layoutYProperty());
		this.manager.getConnection().add(sp);
		this.manager.getConnection().add(tsp);
	}
	public void drawStraight(String type, CShapeNode node, StackPane fromPane, StackPane toPane){
		double label = 0;
		Group arrow = new Group();
		CConnectManager cm = new CConnectManager();
		CArrowHead lh = new CArrowHead();
		Point2D north, south;
		south = new Point2D(fromPane.getLayoutX(), fromPane.getLayoutY()+(fromPane.getPrefHeight()/2));
		north = new Point2D(toPane.getLayoutX(), toPane.getLayoutY()-(toPane.getPrefHeight()/2));
		cm.setVertex(south, north);
		lh.setHead(Constants.SOUTH, cm.lower, cm.upper);		
		Shape line = cm.getShape();
		Shape head = lh.getShape();
		arrow.getChildren().addAll(line, head);	
		StackPane sp = new StackPane(arrow);
		this.manager.getConnects().put(node.getType()+node.getDepth(), arrow);
		sp.setPrefHeight(north.getY()-south.getY());
		sp.layoutXProperty().bind(fromPane.layoutXProperty().add(label));
		double temp = sp.getPrefHeight();
//		System.out.println(temp);
		sp.setLayoutY(south.getY()+(sp.getPrefHeight()/2));
		if(type.equals(CConstants.YES)){
			Text ty = new Text(type.toUpperCase());
			ty.setX(south.getX()+10);
			ty.setY(south.getY()+20);
			StackPane lsp = new StackPane(ty);
			lsp.layoutXProperty().bind(sp.layoutXProperty().add(25.0));
			lsp.layoutYProperty().bind(sp.layoutYProperty());
			this.manager.getConnection().add(sp);
			this.manager.getConnection().add(lsp);
		} else if(type.equals(Constants.NO)){
			Text ty = new Text(type.toUpperCase());
			ty.setX(south.getX()+10);
			ty.setY(south.getY()+20);
			StackPane lsp = new StackPane(ty);
			lsp.layoutXProperty().bind(sp.layoutXProperty().add(25.0));
			lsp.layoutYProperty().bind(sp.layoutYProperty());
			this.manager.getConnection().add(sp);
			this.manager.getConnection().add(lsp);
		} else if(type.equals(CConstants.PLAIN)){
			this.manager.getConnection().add(sp);
		}
		
	}
	public CShapeNode getNext(CShapeNode node){
		
		CShapeNode parent = node.getParent();
		if(parent.findNode(node)==parent.getchildNum()-1){
			return getNext(parent);
		} else {
			if(parent.findNext(node).getchildNum()>0){
				return getFirst(parent.findNext(node));
			} else {
				return parent.findNext(node);
			}
		}
	}
	public CShapeNode getByType(String type, CShapeNode node){
		for(CShapeNode n : node.getNodes()){
			if(n.getType().equals(type)){
				return n;
			}
		}
		return null;
	}
	public CShapeNode getFirst(CShapeNode node){
		if(node.getchildNum()>0){
			return getFirst(node.getNodes().get(0));
		} else {
			return node;
		}
	}
	public CShapeNode findIF(CShapeNode node){
		if(node.getParent().getType().equals(CConstants.IF)){
			return node.getParent();
		} else {
			return findIF(node.getParent());
		}
	}

	public void drawConnects(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		for(CShapeNode node : nodes){
				drawConnect(node);
				drawConnects(node);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void drawConnect(CShapeNode node){
		StackPane fromPane, toPane;
		if(node.getType().equals(CConstants.BODY)){
			CShapeNode next = getNext(node);
//			System.out.println(next.getType()+" ["+next.shape.getBody()+"] {"+node.getParent().findNode(node)+"}");
			if(next.getClass().equals(CIteratorManager.class)||next.getClass().equals(CIfManager.class)){
				toPane = getFirst(next).getSp();
			} else {
				toPane = next.getSp();
			}
			drawStraight(CConstants.PLAIN, node, node.getSp(), toPane);
		} else {
			if(node.getType().equals(Constants.EShapeType.START.name())){
				drawStraight(CConstants.PLAIN, node, node.getSp(), getNext(node).getSp());
			}else {
				
				if(node.getType().equals(CConstants.FOR)){
					CShapeNode condition = node.getNodes().get(1);
					drawStraight(CConstants.PLAIN, node, node.getNodes().get(0).getSp(), condition.getSp());
					drawStraight(CConstants.YES, node, condition.getSp(), getNext(condition).getSp());
					drawOrthogoral(node, getByType(CConstants.AMOUNT, node).getSp(), condition.getSp(), CConstants.ITERATIONSTATEMENT);
					CShapeNode next = getNext(node);
//					System.out.println(next.getType()+" ["+next.shape.getBody()+"] {"+node.getParent().findNode(node)+"}");
					if(next.getClass().equals(CIteratorManager.class)||next.getClass().equals(CIfManager.class)){
						toPane = getFirst(next).getSp();
					} else {
						toPane = next.getSp();
					}
					drawOrthogoral(node, condition.getSp(), toPane, Constants.NO);
					for(int i = 2; i < node.getchildNum()-2; i++){
						drawConnect(node.getNodes().get(i));
					}
				} else if(node.getType().equals(CConstants.WHILE)){
					CShapeNode condition = node.getNodes().get(0);
					drawStraight(CConstants.YES, node, condition.getSp(), getNext(condition).getSp());
					CShapeNode next = getNext(node);
					if(next.getClass().equals(CIteratorManager.class)||next.getClass().equals(CIfManager.class)){
						toPane = getFirst(next).getSp();
					} else {
						toPane = next.getSp();
					}
					drawOrthogoral(node, condition.getSp(), toPane, Constants.NO);
					for(int i = 1; i < node.getchildNum()-1; i++){
						drawConnect(node.getNodes().get(i));
					}
				} else if(node.getType().equals(CConstants.DO)){
					CShapeNode condition = node.getNodes().get(node.getchildNum()-1);
					drawStraight(Constants.NO, node, condition.getSp(), getNext(condition).getSp());
					toPane = node.getNodes().get(0).getSp();
					drawOrthogoral(node, condition.getSp(), toPane, CConstants.DO);
					for(int i = 0; i < node.getchildNum()-2; i++){
						drawConnect(node.getNodes().get(i));
					}
				} else if(node.getType().equals(CConstants.IF)){
					CShapeNode condition = node.getNodes().get(0);
					drawStraight(CConstants.YES, node, condition.getSp(), getNext(condition).getSp());
					CShapeNode next = getNext(node);
					if(next.getClass().equals(CIteratorManager.class)||next.getClass().equals(CIfManager.class)){
						toPane = getFirst(next).getSp();
					} else {
						toPane = next.getSp();
					}
					drawOrthogoral(node, condition.getSp(), toPane, Constants.NO);
					for(int i = 1; i < node.getchildNum()-1; i++){
						drawConnect(node.getNodes().get(i));
					}
				} else if(node.getType().equals(CConstants.ELSEIF)){
					CShapeNode condition = node.getNodes().get(0);
					drawStraight(CConstants.YES, node, condition.getSp(), getNext(condition).getSp());
					CShapeNode ifNode = findIF(node);
					CShapeNode next = getNext(ifNode);
					if(next.getClass().equals(CIteratorManager.class)||next.getClass().equals(CIfManager.class)){
						toPane = getFirst(next).getSp();
					} else {
						toPane = next.getSp();
					}
					drawOrthogoral(node, condition.getSp(), toPane, Constants.NO);
					for(int i = 1; i < node.getchildNum()-1; i++){
						drawConnect(node.getNodes().get(i));
					}
				}
			}
		}
	
		
	}
	public StackPane findFirst(){
		for(Node n : this.getChildren()){
			if(n.getClass().equals(StackPane.class)){
				return (StackPane)n;
			}
		}
		return null;
	}
	public StackPane find(CShapeNode node){
		double sid = node.shape.sid;
		for(Node n : this.getChildren()){
			if(n.getClass().equals(StackPane.class)){
				StackPane sp = (StackPane)n;
				if(n.getUserData().equals(sid)){
					return sp;
				}
			}
		}
		return null;
	}
	public StackPane findPrev(CShapeNode node){
		CShapeNode parent = node.getParent();
		CShapeNode ancester;
		if(isRoot(parent)){
			if(parent.findPrev(node).getNodes().size()>0){
				return find(parent.findPrev(node).getNodes().lastElement());
			} else return find(parent.findPrev(node));
		} else {
			ancester = parent.getParent();
			if(isRoot(ancester)){
				if(ancester.findPrev(parent).getNodes().size()>0){
					return find(ancester.findPrev(parent).getNodes().lastElement());
				} else return find(ancester.findPrev(parent));
			}
			else return null;
		}
	}
	public StackPane findNext(CShapeNode node){
		CShapeNode parent = node.getParent();
		CShapeNode ancester;
		if(isRoot(parent)){
			if(parent.findNext(node).getNodes().size()>0){
				return find(parent.findNext(node).getNodes().firstElement());
			} else return find(parent.findNext(node));
		} else {
			ancester = parent.getParent();
			if(isRoot(ancester)){
				if(ancester.findNext(parent).getNodes().size()>0){
					return find(ancester.findNext(parent).getNodes().firstElement());
				} else return find(ancester.findNext(parent));
			}
			else return null;
		}
	}
	private void addToThis(){
		for(StackPane sp : this.manager.getConnection()){
			this.getChildren().add(1, sp);
		}
	}
	public Boolean checkBlock(CShapeNode node){
		if(isRoot(node.getParent())){
			if(node.getType().equals(CConstants.BODY)){
				return true;
			} else return false;
		}else {
			return checkBlock(node.getParent());
		}
	}
	public Boolean isRoot(CShapeNode node){
		if(node.getType().equals(CConstants.ROOT)){
			return true;
		} else return false;
	}
	public FlowChartManager getManager(){
		return manager;
	}
	public CRootManager getRootNode() { return this.root; }
	public double getCenterLineX() {
		return centerLineX;
	}
	public void setCenterLineX(double centerLineX) {
		this.centerLineX = centerLineX;
	}
	public int getTrimmed() {
		return trimmed;
	}
	public void setTrimmed(int trimmed) {
		this.trimmed = trimmed;
	}
}
