package frames;

import javax.swing.JToolBar;

public class CToolbar extends JToolBar{
//	private static final long serialVersionUID = 1L;
//	private ActionHandler actionHandler;
//	private ButtonGroup buttonGroup;
//	
//	private FlowChartPane flowChartPane;
//	
////	public CToolbar(){
////		// attributes initialization
////		super(null, JToolBar.VERTICAL);
////		this.setSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
////		actionHandler = new ActionHandler();
////		buttonGroup = new ButtonGroup();
////		
////		for (EToolBarButton eButton : EToolBarButton.values()) {
////			CRadioButton button = new CRadioButton(eButton.getIconDefName(), eButton.getIconSLTName());
////			this.add(button);
////			
////			button.addActionListener(actionHandler);
////			button.setActionCommand(eButton.name());
////			button.setToolTipText(eButton.getName());
////			
////			buttonGroup.add(button);
////		}
////		
////		this.setFloatable(true);
////		this.setFocusable(true);
////	}
////	
////	public void init(FlowChartPane flowChartPane) {
////		this.flowChartPane = flowChartPane;
////	}
////	
////	public class ActionHandler implements ActionListener {
////
////		@Override
////		public void actionPerformed(ActionEvent e) {
////			// message to confirm
////			System.out.println(e.getActionCommand() + " Button pressed[" + EToolBarButton.valueOf(e.getActionCommand()).getShape() + "]");
////			
////			CShapeNode node;
////			boolean isDiamond = false;
////			String contents = (
////					EToolBarButton.valueOf(e.getActionCommand()).getShape().getShapeType() != EShapeType.STOP) ? 
////					JOptionPane.showInputDialog("Enter the content of Input/Ouput box: ") : "";
////			
////			//flowChartPane.setShapeManager(EToolBarButton.valueOf(e.getActionCommand()).getShape());
////			if (e.getActionCommand().equals(EShapeType.CONDITION.name()) ||
////					e.getActionCommand().equals(EShapeType.LOOP.name()))
////				isDiamond = true;
////			
////			node = new CShapeNode(EToolBarButton.valueOf(
////					e.getActionCommand()).getShape().getShapeType(),
////					contents, 
////					isDiamond
////					);
////			flowChartPane.getRootNode().insertNode(node);
////			flowChartPane.drawNode(flowChartPane.getGraphics(), node);
////		}
////	}
}
