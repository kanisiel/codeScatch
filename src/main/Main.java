package main;

import frames.CFrame;
import frames.PreferenceFrame;

public class Main {

	public static void main(String[] args) {
		CFrame frame = new CFrame();
		PreferenceFrame preferenceFrame = new PreferenceFrame();
		frame.init(preferenceFrame);
		preferenceFrame.init(frame);
	}
	
}
