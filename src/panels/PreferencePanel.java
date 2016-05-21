package panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JPanel;

import Settings.Constants;
import Settings.Preference;
import Settings.Preference.preferenceItemsChilds;
import frames.CFrame;
import frames.PreferenceFrame;

public class PreferencePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PreferenceFrame parents;
	private Vector<PreferenceDetailPanel> panelVector;
	private Map<String, Integer> panelList;
	private CardLayout layout;
	
	public PreferencePanel(){
		this.panelVector = new Vector<>();
		this.panelList = new LinkedHashMap<>();
		for(preferenceItemsChilds items : Preference.preferenceItemsChilds.values()){
			PreferenceDetailPanel panel = items.getPanel();
			panel.setVisible(true);
			panelVector.add(panel);
			panelList.put(items.getParent()+">"+items.getItemName(), panelVector.indexOf(panel));
		}
		
		this.add(panelVector.get(0), BorderLayout.CENTER);
		
		// set components
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H-30);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.layout = new CardLayout();
		this.setLayout(this.layout);
		for(Entry<String, Integer> entry : panelList.entrySet()){
			this.add(panelVector.get(entry.getValue()), entry.getKey());
		}
		
	}
	
	public void setDetailPanel(String key){
		CardLayout c1 = (CardLayout) this.getLayout();
		c1.show(this, key);
		this.repaint();
	}
	public void init(CFrame mainFrame, PreferenceFrame parent){
		this.setParents(parent);
        for(PreferenceDetailPanel entry : panelVector){
			entry.init(mainFrame);
			entry.setParent(this);
		}
	}

	public PreferenceFrame getParents() {
		return parents;
	}

	public void setParents(PreferenceFrame parents) {
		this.parents = parents;
	}
}
