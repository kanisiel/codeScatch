package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;

import Settings.Buttons.EToolBarButton;
import Settings.Constants;
import frames.CRadioButton;
import javafx.scene.control.ToolBar;
import panels.FlowChartPane;

public class FxToolBar extends ToolBar {
	private static final long serialVersionUID = 1L;
	private ActionHandler actionHandler;
	private ButtonGroup buttonGroup;
	
	private FlowChartPane flowChartPane;
	
	public void FxToolbar(){
		// attributes initialization
		//super();
		this.setPrefSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
		actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
		for (EToolBarButton eButton : EToolBarButton.values()) {
			CRadioButton button = new CRadioButton(eButton.getIconDefName(), eButton.getIconSLTName());
			//this.getChildren().add(button);
			
			button.addActionListener(actionHandler);
			button.setActionCommand(eButton.name());
			button.setToolTipText(eButton.getName());
			
			buttonGroup.add(button);
		}
		
	}
	
	public void init(FlowChartPane flowChartPane) {
		this.flowChartPane = flowChartPane;
	}
	
	public class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			// message to confirm
//			System.out.println(e.getActionCommand() + " Button pressed[" + EToolBarButton.valueOf(e.getActionCommand()).getShape() + "]");
//			
//			CShapeNode node;
//			boolean isDiamond = false;
//			String contents = (
//					EToolBarButton.valueOf(e.getActionCommand()).getShape().getShapeType() != EShapeType.STOP) ? 
//					JOptionPane.showInputDialog("Enter the content of Input/Ouput box: ") : "";
//			
//			//flowChartPane.setShapeManager(EToolBarButton.valueOf(e.getActionCommand()).getShape());
//			if (e.getActionCommand().equals(EShapeType.CONDITION.name()) ||
//					e.getActionCommand().equals(EShapeType.LOOP.name()))
//				isDiamond = true;
//			
//			node = new CShapeNode(EToolBarButton.valueOf(
//					e.getActionCommand()).getShape().getShapeType(),
//					contents, 
//					isDiamond
//					);
//			flowChartPane.getRootNode().insertNode(node);
//			flowChartPane.drawNode(flowChartPane.getGraphics(), node);
		}
	}
}

