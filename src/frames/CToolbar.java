package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import Settings.Buttons.EToolBarButton;
import Settings.Constants;
import Settings.Constants.EShapeType;
import panels.FlowChartPane;
import shapes.CShapeNode;

public class CToolbar extends JToolBar{
	private static final long serialVersionUID = 1L;
	
	private ActionHandler actionHandler;
	private ButtonGroup buttonGroup;
	private FlowChartPane flowChartPane;
	
	public CToolbar(){
		// attributes initialization
		super(null, JToolBar.VERTICAL);
		this.setSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
		actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
		
		for (EToolBarButton eButton : EToolBarButton.values()) {
			CRadioButton button = new CRadioButton(eButton.getIconDefName(), eButton.getIconSLTName());
			this.add(button);
			
			button.addActionListener(actionHandler);
			button.setActionCommand(eButton.name());
			button.setToolTipText(eButton.getName());
			
			buttonGroup.add(button);
		}
		
		this.setFloatable(true);
		this.setFocusable(true);
	}
	
	public void init(FlowChartPane flowChartPane) {
		this.flowChartPane = flowChartPane;
	}
	
	public class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// message to confirm
			System.out.println(e.getActionCommand() + " Button pressed[" + EToolBarButton.valueOf(e.getActionCommand()).getShape() + "]");
			
			CShapeNode node;
			String contents = (
					EToolBarButton.valueOf(e.getActionCommand()).getShape().getShapeType() != EShapeType.STOP) ? 
					JOptionPane.showInputDialog("Enter the content of Input/Ouput box: ") : "";
					
			node = new CShapeNode(EToolBarButton.valueOf(
					e.getActionCommand()).getShape().getShapeType(),
					contents
					);
			
			if (flowChartPane.getRootNode().insertNode(node))	// doens't operate
				flowChartPane.getRootNode().printNode(flowChartPane.getRootNode());
			
			flowChartPane.drawNode(flowChartPane.getGraphics(), node);
		}
	}
}
