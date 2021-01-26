package setting_editor.settings;

import java.awt.Graphics;

import setting_editor.image_canvas.ImageCanvas;

public class AreaCallback extends SettingCallback {

	@Override
	public void onSelected() {
		// TODO Auto-generated method stub
		System.out.println(settingRef.toString() + " is now selected");
	}

	@Override
	public void onDeselected() {
		// TODO Auto-generated method stub
		System.out.println(settingRef.toString() + " is now deselected");
	}

	@Override
	public void addPoint(int x, int y, ImageCanvas c) {
	}

	@Override
	public void paint(ImageCanvas c, Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onWindowClosed() {
		
	}

	@Override
	public void onSaved(ImageCanvas c) {
	}

}
