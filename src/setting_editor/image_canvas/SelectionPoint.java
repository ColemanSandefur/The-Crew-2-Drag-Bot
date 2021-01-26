package setting_editor.image_canvas;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import setting_editor.settings.Setting;
import utils.Pixel;

public class SelectionPoint {
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
		
		int z = canvas.zoom;
		g.setColor(color.toColor());
		g.fillRect(x * z - width * z /2, y * z - width * z /2, width * z, width * z);
		g.setColor(color.invert().toColor());
		g.drawRect(x * z - width * z /2, y * z - width * z /2, width * z, width * z);
	}
	
	public void repaint(ImageCanvas c) {
		int offset = 1;
		int z = canvas.zoom;
		c.repaint(x * z - width * z /2, y * z - width * z /2, width * z + offset, width * z + offset);
	}
	
	public void stop() {
		flashing.stopRunning();
		repaint(canvas);
	}
	
	private void saveLocation(Setting setting) {
		File location = new File("location_data/" + setting.getFileName() + ".txt");
		
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
		File image = new File("images/" + setting.getFileName() + ".png");
		
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
