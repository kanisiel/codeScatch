package panels;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	private PreferenceFrame parent;
	private TreeViewPanel treePanel;
	private JScrollPane scrollPane;
	private Map<String, PreferenceDetailPanel> panelList;
	private BorderLayout layout;
	
	public PreferencePanel(){
		this.panelList = new LinkedHashMap<>();
		for(preferenceItemsChilds items : Preference.preferenceItemsChilds.values()){
			PreferenceDetailPanel panel = items.getPanel();
			panelList.put(items.getParent()+">"+items.getItemName(), panel);
		}
		panelList.get(Preference.preferenceItems[0]+">"+Preference.preferenceItemsChilds.GL.getItemName()).setVisible(true);
		// set components
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H-30);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.layout = new BorderLayout();
		this.setLayout(this.layout);
		for(Entry<String, PreferenceDetailPanel> entry : panelList.entrySet()){
			this.add(entry.getValue(), BorderLayout.CENTER);
		}
		
	}
	public void setDetailPanel(String key){
		for(Entry<String, PreferenceDetailPanel> entry : panelList.entrySet()){
			entry.getValue().setVisible(false);
		}
		panelList.get(key).setVisible(true);
	}
	public void init(CFrame mainFrame, PreferenceFrame parent){
		this.parent = parent;
        for(Entry<String, PreferenceDetailPanel> entry : panelList.entrySet()){
			entry.getValue().init(mainFrame);
			entry.getValue().setParent(this);
		}
	}
}
