package application;

import java.util.HashMap;
import java.util.Map;

import Settings.Buttons.EToolBarButton;
import Settings.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ToolBarController extends VBox {
	private FlowChartCanvas fcc;
	private ToolBar toolbar;
	private ToggleGroup buttonGroup;
	
	public ToolBarController(VBox toolBar, FlowChartCanvas fcc){
		this.fcc = fcc;
		setToolBarNode();
    	Pane canvas = new Pane();
    	toolBar.getChildren().add(canvas);
    	canvas.getChildren().add(toolbar);
	}
	public void setToolBarNode(){
		// attributes initialization
		toolbar = new ToolBar();
		toolbar.setOrientation(Orientation.VERTICAL);
		this.setPrefSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
		buttonGroup = new ToggleGroup();
		buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	  if (buttonGroup.getSelectedToggle() != null) {
		    		  System.out.println(buttonGroup.getSelectedToggle().getUserData().toString());
		    	  }
		      }
		});
		for (EToolBarButton eButton : EToolBarButton.values()) {
			ToggleButton button = new ToggleButton();
			button.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(eButton.getIconDefName()))));
			Map<String, String> userData = new HashMap<>();
			userData.put("Name", eButton.name());
			userData.put("Icon", eButton.getIconDefName());
			userData.put("SLT", eButton.getIconSLTName());
			button.setUserData(userData);
			button.setTooltip(new Tooltip(eButton.getName()));
			button.setToggleGroup(buttonGroup);
			toolbar.getItems().add(button);
			//toolbar.getItems().add(new Separator());
		}
	}

}
