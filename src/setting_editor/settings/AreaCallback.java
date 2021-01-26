package setting_editor.settings;

import java.awt.Graphics;

import setting_editor.image_canvas.ImageCanvas;
import setting_editor.image_canvas.SelectionArea;

public class AreaCallback extends SettingCallback {
	SelectionArea selection = new SelectionArea();

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
		int z = c.getZoom();
		selection.addPoint(x / z, y / z, c);
	}

	@Override
	public void paint(ImageCanvas c, Graphics g) {
		// TODO Auto-generated method stub
		selection.paint(g);
	}
	
	@Override
	public void onWindowClosed() {
		selection.stop();
	}

	@Override
	public void onSaved(ImageCanvas c) {
		selection.save(c, settingRef);
	}

}
