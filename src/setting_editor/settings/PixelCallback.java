package setting_editor.settings;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import setting_editor.image_canvas.Flashing;
import setting_editor.image_canvas.ImageCanvas;
import utils.Pixel;

class Selection {
	int x = 0, y = 0, width = 3;
	Pixel color;
	Flashing flashing;
	ImageCanvas canvas;
	
	public Flashing createFlashing() {
		
		return new Flashing() {
			int _x, _y, _width;
			
			@Override
			public void onFlash(boolean flashing) {
				_x = x;
				_y = y;
				_width = width;
				if (canvas != null) {
					repaint(canvas);
				}
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				_repaint(canvas);
			}
			
			public void _repaint(ImageCanvas c) {
				int offset = 1;
				c.repaint(_x - _width/2, _y - _width/2, _width + offset, _width + offset);
			}
		};
	}
	
	public void setColor(Pixel c) { this.color = c; }
	
	public void moveCursor(int x, int y, ImageCanvas c) {
		canvas = c;
		if (this.x != x || this.y != y) {
//			repaint(c);
			if (flashing != null)
				flashing.stopRunning();
			
			flashing = createFlashing();
			this.x = x;
			this.y = y;
			flashing.start();
		}
	}
	
	public void drawCursor(Graphics g) {
		if (color == null || (flashing != null && flashing.flashing == false)) {
			return;
		}
		g.setColor(color.toColor());
		g.fillRect(x - width/2, y - width/2, width, width);
		g.setColor(color.invert().toColor());
		g.drawRect(x - width/2, y - width/2, width, width);
	}
	
	public void repaint(ImageCanvas c) {
		int offset = 1;
		c.repaint(this.x - width/2, this.y - width/2, width + offset, width + offset);
	}
	
	public void stop() {
		flashing.stopRunning();
		repaint(canvas);
	}
	
	private void saveLocation(Setting setting) {
		File location = new File("location_data/" + setting.getFileName() + "-generated.txt");
		
		try {
			FileWriter stream = new FileWriter(location);
			
			stream.write(x + " ");
			stream.write(y + " ");
			stream.write(1 + " ");
			stream.write(1 + " ");
			
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveImage(ImageCanvas c, Setting setting) {
		File image = new File("images/" + setting.getFileName() + "-generated.png");
		
		try {
			ImageIO.write(c.getImage().getBufferedImage().getSubimage(x, y, 1, 1), "png", image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(ImageCanvas c, Setting setting) {
		saveLocation(setting);
		saveImage(c, setting);
	}
}

public class PixelCallback extends SettingCallback {
	Selection selection = new Selection();
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
		selection.setColor(c.getImage().getPixel(x, y));
		selection.moveCursor(x, y, c);
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
