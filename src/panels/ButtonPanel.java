package panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frames.CFrame;

public class ButtonPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JFrame parent;
	private ActionHandler actionHandler;
	
	public ButtonPanel(JFrame parent) {
		super();
		this.parent = parent;
		this.actionHandler = new ActionHandler();
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		JButton okButton = new JButton("OK");
		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setActionCommand("ok");
		okButton.addActionListener(this.actionHandler);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this.actionHandler);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setSize(okButton.getWidth()+cancelButton.getWidth(), okButton.getHeight());
		this.add(buttonPanel);
		this.setBackground(Color.GREEN);
//		buttonPanel.setSize(okButton.getWidth()+cancelButton.getWidth(), okButton.getHeight());
//		this.setLayout(new BorderLayout());
//		this.add(buttonPanel, BorderLayout.EAST);
	}
	
	public class ActionHandler implements ActionListener {
		private CFrame parents;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("cancel")){
				parent.dispose();
			}
			//System.out.println(e.getActionCommand());
		}
		public void init(CFrame parents){
			this.parents = parents;
		}

	}
	
}

