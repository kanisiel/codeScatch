package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import panels.DesktopPane;
import panels.FlowChartPane;

public class FlowChart extends InternalFrame {
	private static final long serialVersionUID = 1L;
	private FlowChartPane flowChartPane;
	private JScrollPane scrollPane;
	
	public FlowChart(String title) {
        super(title);//iconifiable
        this.getContentPane().setLayout(new BorderLayout());
        
        flowChartPane = new FlowChartPane();
        scrollPane = new JScrollPane(flowChartPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(flowChartPane.getWidth(), flowChartPane.getHeight()));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
        
	}
	
	public void init(DesktopPane parent){
		this.setLocation(0,0);
		this.setSize(parent.getWidth()/2, parent.getHeight());
		this.setVisible(true);
		scrollPane.setVisible(true);
	}
	
	public FlowChartPane getFlowChartPane() {	return flowChartPane;	}
}
