package setting_editor.settings;

import java.awt.Graphics;

import setting_editor.image_canvas.ImageCanvas;

@SuppressWarnings("rawtypes")
public abstract class SettingCallback {
	protected Setting settingRef;
	
	public void initialize(Setting setting) {
		settingRef = setting;
	}
	
	public abstract void onSelected();
	public abstract void onDeselected();
	public abstract void addPoint(int x, int y, ImageCanvas c);
	public abstract void paint(ImageCanvas c, Graphics g);
	public abstract void onWindowClosed();
	public abstract void onSaved(ImageCanvas c);
}
