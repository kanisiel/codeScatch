package models;

import java.awt.Font;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Setting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Font> fontSettings;
	private String language;

	public Setting() {
		super();
		this.setFontSettings(new LinkedHashMap<>());
		this.setLanguage("English");
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Map<String, Font> getFontSettings() {
		return fontSettings;
	}

	public void setFontSettings(Map<String, Font> fontSettings) {
		this.fontSettings = fontSettings;
	}
	
	public void addFontSettings(String target, Font font){
		if(this.fontSettings.size()<3){
			this.fontSettings.put(target, font);
		} else if(this.fontSettings.containsKey(target)) {
			this.fontSettings.replace(target, font);
		}
	}

}
