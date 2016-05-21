package application;

import java.util.HashMap;
import java.util.Map;

import Settings.Buttons.EToolBarButton;
import Settings.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FxToolBar1 extends ToolBar {
	private ToggleGroup buttonGroup;
	private Group g;
	
	private FlowChartCanvas flowChartCanvas;
	
	public void FxToolbar(){
		// attributes initialization
		//super();
		g = new Group();
		this.setPrefSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
		buttonGroup = new ToggleGroup();
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	  if (buttonGroup.getSelectedToggle() != null) {
		    		  System.out.println(buttonGroup.getSelectedToggle().getUserData().toString());
		    	  }
		      }
		});
		System.out.println(1);
		for (EToolBarButton eButton : EToolBarButton.values()) {
			
			RadioButton button = new RadioButton();
			button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(eButton.getIconDefName()))));
			Map<String, String> userData = new HashMap<>();
			userData.put("Name", eButton.name());
			userData.put("Icon", eButton.getIconDefName());
			userData.put("SLT", eButton.getIconSLTName());
			button.setUserData(userData);
			button.setTooltip(new Tooltip(eButton.getName()));
			button.setToggleGroup(buttonGroup);
			g.getChildren().add(button);
		}
		this.getChildren().add(g);
	}
	
	public void init(FlowChartCanvas flowChartCanvas) {
		this.flowChartCanvas = flowChartCanvas;
	}
	
	
}

