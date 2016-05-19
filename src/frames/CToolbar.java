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
	
	public void init(FlowChartPane flowChartPane) {	this.flowChartPane = flowChartPane;	}
	
	public class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CShapeNode node;
			int attachTo = 1;
			
			String contents = (EToolBarButton.valueOf(e.getActionCommand()).getShape().getShapeType() != EShapeType.STOP) ? 
					JOptionPane.showInputDialog("Enter the content of " + EToolBarButton.valueOf(e.getActionCommand()) + " box: ") : EShapeType.STOP.name();
			
			if (contents == null) return;
			
			node = new CShapeNode(EToolBarButton.valueOf(
					e.getActionCommand()).getShape().getShapeType(),
					contents
					);
			
			/*
			if (EToolBarButton.valueOf(e.getActionCommand()).getShape().getShapeType() == EShapeType.CONDITION)
				attachTo = (int) JOptionPane.showInputDialog(null, "0:NO 1:YES ",
	                    "Link to shape no: ", JOptionPane.QUESTION_MESSAGE, null,
	                    Constants.DECISION,
	                    Constants.DECISION[0]);
			*/
			if (flowChartPane.getRootNode().insertNode(attachTo, node))
				flowChartPane.setCoords(flowChartPane.getRootNode(), 55, 30);
			
			flowChartPane.getRootNode().printNode(flowChartPane.getRootNode());
			System.out.println("\n");
			flowChartPane.drawNode(flowChartPane.getGraphics(), node);
		}
	}
}
