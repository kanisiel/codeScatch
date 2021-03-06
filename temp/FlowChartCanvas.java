package application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.text.BadLocationException;

import Settings.CConstants;
import Settings.Constants;
import Settings.Windows.InternalWindows;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jfxtras.scene.control.window.Window;
import shapes.CArrowHead;
import shapes.CConnectManager;
import shapes.CIteratorManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CShapeNode;
import shapes.CStartEndManager;

public class FlowChartCanvas extends BorderPane {
	public Pane canvas;
	private FlowChartManager manager;
	private Point2D startPoint;
	private CRootManager root;
	private Map<String, Double> boundaryWidth;
	private Map<String, Double> layoutY;
	private Map<String, Double> lastLowerAnchor;
	private double centerLineX = (Constants.windowWidth/2);
	public double height = Constants.windowHeight-30;
	private DesktopPaneController parent;
	private Vector<Object> tags;
	
	public FlowChartCanvas(DesktopPaneController parent) {
		this.parent = parent;
		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
    	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);		
    	this.setBackground(new Background(bi));
//    	this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		this.manager = new FlowChartManager();
		this.boundaryWidth = new LinkedHashMap<>();
		this.layoutY = new LinkedHashMap<>();
		this.lastLowerAnchor = new LinkedHashMap<>();
		this.tags = new Vector<>();
		canvas = new Pane();
		startPoint = new Point2D(0, 0);
		this.setPrefSize(Constants.windowWidth, Constants.windowHeight);
		this.getChildren().add(canvas);
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
		canvas = new Pane();
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
//		showAll();
		drawNodes(root);
		drawBounds(root);
		drawConnects(root);
	}
	public void showAll(){
//		for(Node n :this.getChildren()){
//			System.out.println(n);
//		}
		show(root);
	}
	public void show(CShapeNode node){
		for(CShapeNode n : root.getNodes()){
			if(n.getNodes().size()==0){
				System.out.print(n.getParent().getType()+":"+n.getType());
				System.out.println();
			} else {
				show(n);
			}
		}
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
		}
	}

	public void drawConnects(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				drawConnects(node);
			}else {
				drawConnect(node);
			}
		}
	}
	
	public void drawBounds(CShapeNode root){
//		Vector<CShapeNode> nodes = ;
		for(CShapeNode node : root.getNodes()){
			if(node.getClass().equals(CIteratorManager.class)||node.getType().equals(CConstants.IF)||node.getType().equals(CConstants.ELSE)){
				drawBound(node);
			}
		}
	}
	public void drawBound(CShapeNode node){
		double width = 0;
		double height = 0;
		CShapeNode first = node.getNodes().firstElement();
		CShapeNode last = node.getNodes().lastElement();
		StackPane fsp = find(first);
		StackPane lsp = find(last);
		Color fill;
		Color stroke;
		double upperAnchor = fsp.getLayoutY()-(fsp.getPrefHeight()/2), lowerAnchor = (lsp.getLayoutY()+(lsp.getPrefHeight()/2));
		for(CShapeNode n : node.getNodes()){
			StackPane sp = find(n);
			if(width < sp.getPrefWidth()){
				width = sp.getPrefWidth();
			}
		}
		height = (lsp.getLayoutY()+(lsp.getPrefHeight()/2)) - (fsp.getLayoutY()-(fsp.getPrefHeight()/2));
		Rectangle rect = new Rectangle();
		rect.setWidth(width+50);
		rect.setHeight(height+20);
		rect.setOpacity(0.4);
		switch(node.getType()){
		case CConstants.FOR:
			fill = Color.ROYALBLUE;
			stroke = Color.DARKBLUE;
			break;
		case CConstants.WHILE:
			fill = Color.OLIVE;
			stroke = Color.DARKOLIVEGREEN;
			break;
		case CConstants.IF:
			fill = Color.ORCHID;
			stroke = Color.DARKORCHID;
			break;
		default:
			fill = Color.MOCCASIN;
			stroke = Color.ORANGE;
			break;
		}
		rect.setFill(fill);
		rect.setStroke(stroke);
		rect.setStrokeWidth(2);
		rect.setLayoutX(centerLineX-(rect.getWidth()/2));
		rect.setLayoutY(upperAnchor-10);
		Label label = new Label(node.getType());
		label.setLayoutX(centerLineX-(rect.getWidth()/2)+5);
		label.setLayoutY(upperAnchor-25);
		Group bound = new Group();
		bound.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		       rect.setStroke(Color.RED);
		       int[] lines = node.lines;
		       for(int i = lines[0]; i < lines[1]; i++){
		    	   try {
		    		   Object tag = parent.textArea.addLineHighlight(i, java.awt.Color.ORANGE);
		    		   tags.add(tag);
		    	   } catch (BadLocationException e) {
		    		   // TODO Auto-generated catch block
		    		   e.printStackTrace();
		    	   }
		       }
		    }
		});
		bound.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		       rect.setStroke(stroke);
		       for(Object tag : tags){
		    	   parent.textArea.removeLineHighlight(tag);
		       }
		       tags.clear();
		    }
		});
		bound.getChildren().addAll(rect,label);
		canvas.getChildren().add(1, bound);
	}
	public Boolean checkBlock(CShapeNode node){
		if(isRoot(node.getParent())){
			if(node.getType().equals(CConstants.CODE)){
				return true;
			} else return false;
		}else {
			return checkBlock(node.getParent());
		}
	}
	public void draw(CShapeNode node){
		CShapeManager shape = node.getShape();
		this.manager.addNode(shape);
		setCoord(shape);
		if(node.getType().equals(Constants.EShapeType.STOP.name())){
			if(node.getParent().getchildNum()>2){
				startPoint = this.manager.findPrev(node.shape).getLowerAnchor();
				Point2D p = new Point2D(startPoint.getX(), startPoint.getY()+55);
				CStartEndManager end = (CStartEndManager) this.manager.getNodes().lastElement();
				end.setP(p);
				end.setTp(new Point2D(p.getX()+(end.getD().getWidth()+5), (p.getY())+(5.0)));
				end.setUpperAnchor(new Point2D(p.getX(), p.getY()-15));
				this.height = p.getY()+40;
				CStartEndManager start = (CStartEndManager) this.manager.getNodes().firstElement();
				startPoint = node.shape.getUpperAnchor();
				Point2D p2 = new Point2D(startPoint.getX(), 30);
				start.setP(p2);
				start.setTp(new Point2D(p2.getX()+(start.getD().getWidth()+5), (p2.getY())+(5.0)));
				start.setLowerAnchor(new Point2D(p2.getX(), p2.getY()+15));
				StackPane sp = findFirst();
				sp.getChildren().set(0,start.getShape()); 
			}
			
		}
		Shape s = shape.getShape();
		s.setUserData(shape.getUpperAnchor().getY());
		s.setId("");
		Text text = shape.getText();
		StackPane sp = new StackPane();
		sp.setPrefSize(shape.getD().getWidth(),	 shape.getD().getHeight());
		sp.getChildren().addAll(s, text);
//		sp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//		    @Override
//		    public void handle(MouseEvent event) {
//		        if(event.isPrimaryButtonDown()){
//		        	
//		        	System.out.println("Axis Y : "+sp.getLayoutY());
//		        	System.out.println("Height : "+sp.getPrefHeight());
//		        	System.out.println("Sum : "+(sp.getLayoutY()+sp.getPrefHeight()));
////		        	Text t = (Text)sp.getChildren().get(1);
////		    		System.out.println(t.getWidth());
//		        }
//		    }
//		});
		if(checkBlock(node)){
			sp.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			       Shape s = (Shape) sp.getChildren().get(0);
			       s.setStroke(Color.RED);
			       int[] lines = node.lines;
			       for(int i = lines[0]; i < lines[1]; i++){
			    	   try {
			    		   Object tag = parent.textArea.addLineHighlight(i, java.awt.Color.ORANGE);
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
		sp.setLayoutX(centerLineX);
		if(canvas.getChildren().size()<1){
			sp.setLayoutY(30);
		}else {
			StackPane prev = (StackPane) canvas.getChildren().get(canvas.getChildren().size()-1);
			
			if(node.getType().equals(Constants.EShapeType.STOP.name())){
				if(prev.getLayoutY()<this.getPrefHeight()-30){
					sp.setLayoutY(this.getPrefHeight()-30);	
					Window w = parent.getWindows().get(InternalWindows.Flow.getTitle());
					height = w.heightProperty().get();
				} else {
					sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
					height = sp.getLayoutY()+sp.getPrefHeight();
				}
			} else {
//				if(node.getParent().getClass().equals(CIteratorManager.class)){
//					sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+70);
//				}
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
		sp.setUserData(shape.sid);
//			System.out.println(sp.getUserData());
		if(node.getParent().findNode(node)==0){
			this.layoutY.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()-15);
		}
		if(node.getParent().findNode(node) == (node.getParent().getNodes().size()-1)){
			this.lastLowerAnchor.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()+sp.getPrefHeight()+15);
		}
		double bw = this.boundaryWidth.get(node.getParent().getType()+node.getParent().getDepth());
		if(node.getParent().findNode(node)==0){
			this.boundaryWidth.put(node.getParent().getType()+node.getParent().getDepth(), sp.getPrefWidth()/2);
		}else {
			if(sp.getPrefWidth()/2 > bw){
				this.boundaryWidth.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getPrefWidth()/2);
			}
		}
		canvas.getChildren().add(sp);
		
	}
	public void drawOrthogoral(CShapeNode node, StackPane fromPane, StackPane toPane, String to){
		Point2D north, east, south, west, southD;
		CShapeNode parent = node.getParent();
		Group arrow = new Group();
		CConnectManager cm = new CConnectManager();
		CArrowHead lh = new CArrowHead();
		CShapeNode ancester = parent.getParent();
		CShapeNode nextBody;
		Shape line;
		Shape head;
		StackPane prev = null;
		for(int i = this.getChildren().indexOf(fromPane)-1; i > 0; i--){
			if(canvas.getChildren().get(i).getClass().equals(StackPane.class)){
				prev = (StackPane) canvas.getChildren().get(i);
			}
		}
		if(ancester.findNext(parent).getType().equals(CConstants.CODE)||ancester.findNext(parent).getType().equals(Constants.EShapeType.STOP.name())){
			nextBody = ancester.findNext(parent);
		}else {
			nextBody = ancester.findNext(parent).getFirstNode();
		}
		double gap = boundaryWidth.get(parent.getType()+parent.getDepth())/2+30;
		if(to.equals(Constants.NO)){
			east = new Point2D(fromPane.getLayoutX()+(fromPane.getPrefWidth()/2), fromPane.getLayoutY());
			north = new Point2D(toPane.getLayoutX(), toPane.getLayoutY()-(toPane.getPrefHeight()/2));
			cm.setHVertex(east, north, gap);//node.shape.getRightAnchor(), nextBody.shape.getUpperAnchor(), gap);
			lh.setHead(Constants.SOUTH, cm.lower, cm.upper);
			line = cm.getShape();
			head = lh.getShape();
			Text tn = new Text(Constants.NO);
			tn.setX(east.getX()+10);
			tn.setY(east.getY()-10);
			arrow.getChildren().addAll(line, head, tn);
			this.manager.getConnects().put(node.getType()+node.getDepth()+"N", arrow);
			canvas.getChildren().add(arrow);
		}if(to.equals(CConstants.ITERATIONSTATEMENT)){
			south = new Point2D(fromPane.getLayoutX(), fromPane.getLayoutY()+(fromPane.getPrefHeight()/2));
			west = new Point2D(toPane.getLayoutX()-(toPane.getPrefWidth()/2), toPane.getLayoutY());
			cm.setVVertex(south, west, gap);
			lh.setHead(Constants.EAST, cm.lower, cm.upper);
			line = cm.getShape();
			head = lh.getShape();
			arrow.getChildren().addAll(line, head);
			this.manager.getConnects().put(node.getType()+node.getDepth()+"While", arrow);
			canvas.getChildren().add(arrow);		
		}if(to.equals(CConstants.ELSE)){
			south = new Point2D(fromPane.getLayoutX(), fromPane.getLayoutY()+(fromPane.getPrefHeight()/2));
			southD = new Point2D(toPane.getLayoutX(), toPane.getLayoutY()-(toPane.getPrefHeight()/2));
			cm.setVVertexL(south, southD, gap);
			lh.setHead(Constants.SOUTH, cm.lower, cm.upper);
			line = cm.getShape();
			head = lh.getShape();
			arrow.getChildren().addAll(line, head);
			this.manager.getConnects().put(node.getType()+node.getDepth()+"Else", arrow);
			canvas.getChildren().add(arrow);		
		}
	}
	public void drawStraight(CShapeNode node, StackPane fromPane, StackPane toPane){
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
		if(node.getType().equals(CConstants.CONDITION)){
			Text ty = new Text("Yes");
			ty.setX(south.getX()+10);
			ty.setY(south.getY()+20);
			arrow.getChildren().addAll(line, head, ty);
		}else {
			arrow.getChildren().addAll(line, head);	
		}
		this.manager.getConnects().put(node.getType()+node.getDepth(), arrow);
		canvas.getChildren().add(arrow);
	}
	public void drawConnect(CShapeNode node){
		//StackPane spConnect = new StackPane();
		CShapeNode parent = node.getParent();
		if(node.getType().equals(CConstants.CONDITION)){
			StackPane nextShape = findNext(node);
			StackPane sp = find(node);
			CShapeNode ancester = parent.getParent();
			CShapeNode nextBody;
			if(ancester.findNext(parent).getType().equals(CConstants.CODE)||ancester.findNext(parent).getType().equals(Constants.EShapeType.STOP.name())){
				nextBody = ancester.findNext(parent);
			}else {
				nextBody = ancester.findNext(parent).getFirstNode();
			}
			drawOrthogoral(node, sp, nextShape, Constants.NO);
			int index = this.getChildren().indexOf(sp);
			StackPane next = (StackPane) this.getChildren().get(index+1);
			drawStraight(node, sp, next);
		}else if(node.getType().equals(CConstants.CODE)){
			CShapeNode prev = parent.findCondition();
			StackPane sp = find(node);
			int index = this.getChildren().indexOf(sp);
			StackPane next = (StackPane) this.getChildren().get(index+1);
			if(prev!=null){
				StackPane prevShape = find(prev);
				if(parent.findNode(node) == (parent.getNodes().size()-1)&&!(parent.getType().equals(CConstants.IF))){
					drawOrthogoral(node, sp, prevShape, CConstants.ITERATIONSTATEMENT);
				} else {
					if(parent.getType().equals(CConstants.IF)){
						CShapeNode ancester = parent.getParent();
						if(ancester.findNext(parent).getType().equals(CConstants.ELSE)){
							CShapeNode elsenode = parent.getParent().findNext(parent);
							CShapeNode nextBlock = ancester.findNext(elsenode);
							CShapeNode nextBlockFirst;
							if(nextBlock.getType().equals(CConstants.CODE)){
								nextBlockFirst = nextBlock;
							} else {
								nextBlockFirst = nextBlock.getNodes().firstElement();
							}
							StackPane nextBShape = find(nextBlockFirst);
							drawOrthogoral(node, sp, nextBShape, CConstants.ELSE);
						}
					}else {
						drawStraight(node, sp, next);
					}
				}
			} else {
				drawStraight(node, sp, next);
			}
		}
		if(root.findNode(node)==0){
//			CShapeNode end = root.getNodes().lastElement();
			StackPane currShape = find(node);
			StackPane nextShape = findNext(node);
			
			drawStraight(node, currShape, nextShape);
		}
		Group g = this.manager.getConnects().get(Constants.EShapeType.STOP.name()+"1");
		if(g!=null){
			canvas.getChildren().remove(g);	
		}
	}
	public StackPane findFirst(){
		for(Node n : canvas.getChildren()){
			if(n.getClass().equals(StackPane.class)){
				return (StackPane)n;
			}
		}
		return null;
	}
	public StackPane find(CShapeNode node){
		double sid = node.shape.sid;
		for(Node n : canvas.getChildren()){
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
	public Boolean isRoot(CShapeNode node){
		if(node.getType().equals(CConstants.ROOT)){
			return true;
		} else return false;
	}
	public FlowChartManager getManager(){
		return manager;
	}
	public CRootManager getRootNode() { return this.root; }
}
