package setting_editor.settings;

import java.awt.Graphics;

import setting_editor.image_canvas.ImageCanvas;
import setting_editor.image_canvas.SelectionPoint;

public class PixelCallback extends SettingCallback {
	SelectionPoint selection = new SelectionPoint();
	private void stop() {
		selection.stop();
	}
	@Override
	public void onSelected() {
		// TODO Auto-generated method stub
		System.out.println(settingRef.toString() + " is now selected");
	}

	@Override
	public void onDeselected() {
		// TODO Auto-generated method stub
		System.out.println(settingRef.toString() + " is now deselected");
		stop();
	}

	@Override
	public void addPoint(int x, int y, ImageCanvas c) {
		int z = c.getZoom();
		selection.setColor(c.getImage().getPixel(x / z, y / z));
		selection.moveCursor(x / z, y / z, c);
	}

	@Override
	public void paint(ImageCanvas c, Graphics g) {
		selection.drawCursor(g);
	}
	
	@Override
	public void onWindowClosed() {
		stop();
	}

	@Override
	public void onSaved(ImageCanvas c) {
		selection.save(c, settingRef);
	}

}
