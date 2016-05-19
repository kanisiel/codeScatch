package frames;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollPane;

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
        scrollPane = new JScrollPane(flowChartPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
            }
        });
	}
	
	public void init(DesktopPane parent){
		this.setLocation(0,0);
		this.setSize(parent.getWidth()/2, parent.getHeight());
		this.setVisible(true);
		scrollPane.setVisible(true);
	}
	
	public FlowChartPane getFlowChartPane() {	return flowChartPane;	}
}
