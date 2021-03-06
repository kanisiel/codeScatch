package frames;

import java.awt.BorderLayout;

import panels.DesktopPane;
import panels.FlowChartCanvas;


public class FlowChart extends InternalFrame {

	private static final long serialVersionUID = 1L;
	private FlowChartCanvas flowChartPane;
	

	public FlowChart(String title) {
        super(title);//iconifiable
        this.getContentPane().setLayout(new BorderLayout());
        flowChartPane = new FlowChartCanvas();
		this.add(flowChartPane, BorderLayout.CENTER);

	}

//	@Override
//	public void init(DesktopPane parent){
//		this.parent = parent;
//		this.setLocation(0,0);
//		this.setSize(parent.getWidth()/2, parent.getHeight());
//		this.setVisible(true);
//	}
	
	public void init(DesktopPane parent){
		this.setLocation(0,0);
		this.setSize(parent.getWidth()/2, parent.getHeight());
		this.setVisible(true);
	}
	
	public FlowChartCanvas getFlowChartPane() {	return flowChartPane;	}
}
