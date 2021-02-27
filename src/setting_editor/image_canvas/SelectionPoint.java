package setting_editor.image_canvas;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;

import setting_editor.settings.Setting;
import utils.Pixel;

@SuppressWarnings("rawtypes")
public class SelectionPoint {
	int width = 3;
	Point point, oldPoint;
	Pixel color;
	Flashing flashing;
	ImageCanvas canvas;
	
	public Flashing createFlashing() {
		
		return new Flashing() {
			Point _point;
			
			@Override
			public void onFlash(boolean flashing) {
				_point = point;
				
				if (canvas != null) {
					repaint(canvas, _point);
				}
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				repaint(canvas, _point);
			}
		};
	}
	
	public void setColor(Pixel c) { this.color = c; }
	
	public void moveCursor(int x, int y, ImageCanvas c) {
		canvas = c;
		if (point == null || point.x != x || point.y != y) {
			
			if (flashing != null)
				flashing.stopRunning();
			
			oldPoint = point;
			
			flashing = createFlashing();
			point = new Point(x, y);
			flashing.start();
		}
	}
	
	public void drawCursor(Graphics g) {
		if (color == null || (flashing != null && flashing.flashing == false) || point == null) {
			return;
		}
		
		int z = canvas.zoom;
		g.setColor(color.toColor());
		g.fillRect(point.x * z - width * z /2, point.y * z - width * z /2, width * z, width * z);
		g.setColor(color.invert().toColor());
		g.drawRect(point.x * z - width * z /2, point.y * z - width * z /2, width * z, width * z);
	}
	
	public void repaint(ImageCanvas c, Point p) {
		if (p == null || c == null) {
			return;
		}
		
		int offset = 1;
		int z = canvas.zoom;
		c.repaint(p.x * z - width * z /2, p.y * z - width * z /2, width * z + offset, width * z + offset);
	}
	
	public void repaint(ImageCanvas c) {
		repaint(c, point);
	}
	
	public void stop() {
		if (flashing != null)
			flashing.stopRunning();
		
		repaint(canvas);
		
		point = null;
	}
	
	private void saveLocation(Setting setting) {
		
		try {
			String dir = Setting.getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/")) + "/location_data/";
			
			File theDir = new File(dir);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			
			dir += setting.getFileName() + ".txt";
			
			File location = new File(dir);
			
			FileWriter stream = new FileWriter(location);
			
			stream.write(point.x + " ");
			stream.write(point.y + " ");
			stream.write(1 + " ");
			stream.write(1 + " ");
			
			stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveImage(ImageCanvas c, Setting setting) {
		try {
			
			String dir = Setting.getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/")) + "/images/";
			
			File theDir = new File(dir);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			
			dir += setting.getFileName() + ".png";
			
			File image = new File(dir);
			ImageIO.write(c.getImage().getBufferedImage().getSubimage(point.x, point.y, 1, 1), "png", image);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void save(ImageCanvas c, Setting setting) {
		if (c.getImage() == null || point == null) {
			return;
		}
		
		saveLocation(setting);
		saveImage(c, setting);
	}
	
	public void reset() {
	}
}
