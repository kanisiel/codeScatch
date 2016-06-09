package models;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import Settings.Preference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class FontChooserDialog extends Dialog<Pair<String, String>> {
	private ObservableList<String> fontList, sizeList;
	private ComboBox<String> cbFont, cbSize;
	private ButtonType buttonType;
	private GridPane grid;
	private RSyntaxTextArea textArea;
	
	public FontChooserDialog(RSyntaxTextArea textArea) {
		this.setTitle("Preference");
		this.setHeaderText("Choose Font and Size");
		
		this.fontList = FXCollections.observableArrayList();
		this.sizeList = FXCollections.observableArrayList();
		this.buttonType = new ButtonType("확인", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(buttonType, new ButtonType("Set Default", ButtonData.OTHER), ButtonType.CANCEL);
		this.grid = new GridPane();
		this.textArea = textArea;
		
		init();
	}
		
	public void init() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		for (Font font : ge.getAllFonts()) { 
			fontList.add(font.getFontName());
		}
		
		for (String size : Preference.fontSizes) {
			sizeList.add(size);
		}
		
		this.cbFont = new ComboBox<String>(fontList);
		this.cbSize = new ComboBox<String>(sizeList);
		
		cbFont.setValue(fontList.get(0));
		cbSize.setValue(sizeList.get(0));
				
		this.grid.setHgap(10);
		this.grid.setVgap(10);
		this.grid.setPadding(new Insets(20, 150, 10, 10));
		this.grid.add(new Label("Font"), 0, 0);
		this.grid.add(cbFont, 0, 1);
		this.grid.add(new Label("Size"), 1, 0);
		this.grid.add(cbSize, 1, 1);

		this.getDialogPane().setContent(grid);
	}
	
	public void requestFocus() {
		Platform.runLater(() -> cbFont.requestFocus());

		this.setResultConverter(dialogButton -> {
			System.out.println(dialogButton.getText());
		    if (dialogButton == buttonType) {
		        return new Pair<>(cbFont.getValue(), cbSize.getValue());
		    }
		    return null;
		});

		this.showAndWait().ifPresent(fontInfo -> {
		    this.changeFont(fontInfo.getKey(), Integer.parseInt(fontInfo.getValue()));
		});
	}

	private void changeFont(String selectedFontName, int size) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textArea.setFont(new Font(selectedFontName, Font.PLAIN, size));
				textArea.revalidate();
			}
		});
	}
}
