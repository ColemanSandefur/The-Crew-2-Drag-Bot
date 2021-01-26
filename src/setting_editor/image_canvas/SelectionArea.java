package setting_editor.image_canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import setting_editor.settings.Setting;
import utils.CustomImage;

class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Box {
	Point[] points = new Point[2];
	int index = -1;
	
	public boolean hasAllPoints() {
		for (int i = 0; i < points.length; i++) {
			if  (points[i] == null) {
				return false;
			}
		}
		
		return true;
	}
	
	public void addPoint(int x, int y, ImageCanvas c) {
		index++;
		
		if (index == points.length) {
			for (int i = 0; i < points.length; i++) {
				points[i] = null;
			}
			index = 0;
		}
		
		points[index] = new Point(x, y);
	}
	
	public int getWidth(int zoom) {
		return round(Math.abs(points[1].x - points[0].x), zoom);
	}
	
	public int getHeight(int zoom) {
		return round(Math.abs(points[1].y - points[0].y), zoom);
	}
	
	public Point getTopLeftCorner(int zoom) {
		int x = round(Math.min(points[0].x, points[1].x), zoom);
		int y = round(Math.min(points[0].y, points[1].y), zoom);
		
		return new Point(x, y);
	}
	
	private int round(int num, int zoom) {
		return num * zoom;
	}
}

public class SelectionArea {
	Box curBox = new Box();
	Box oldBox = null;
	Flashing flashing;
	ImageCanvas canvas;
	
	public Flashing createFlashing() {
		
		return new Flashing() {
			Box _box;
			
			@Override
			public void onFlash(boolean flashing) {
				_box = curBox;
				if (canvas != null) {
					repaint(canvas);
				}
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				repaint(canvas, _box);
			}
		};
	}
	
	public void addPoint(int x, int y, ImageCanvas c) {
		canvas = c;
		if (flashing != null) {
			flashing.stopRunning();
		}
		
		if (curBox.hasAllPoints()) {
			repaint(c);
			oldBox = curBox;
		}
		
		flashing = createFlashing();
		
		curBox.addPoint(x, y, c);
		
		flashing.start();
	}
	
	public void paint(Graphics g) {
		if (flashing == null || !curBox.hasAllPoints()) {
			return;
		}
		
		Color c = (flashing.flashing? Color.BLACK : Color.WHITE); //flashes between black and white
		Color c2 = flashing.flashing? Color.WHITE : Color.BLACK;
		
		int z = canvas.getZoom();
		
		g.setColor(c);
		
		g.drawRect(curBox.getTopLeftCorner(z).x, curBox.getTopLeftCorner(z).y, curBox.getWidth(z), curBox.getHeight(z));
		
		g.setColor(c2);
		
		g.drawRect(curBox.getTopLeftCorner(z).x + 1, curBox.getTopLeftCorner(z).y + 1, curBox.getWidth(z) - 2, curBox.getHeight(z) - 2);
	}
	
	public void repaint(ImageCanvas c, Box curBox) {
		if (!curBox.hasAllPoints() || c == null) {
			return;
		}
		int offset = 1;
		int z = c.getZoom();
		c.repaint(curBox.getTopLeftCorner(z).x, curBox.getTopLeftCorner(z).y, curBox.getWidth(z) + offset, curBox.getHeight(z) + offset);
	}
	
	public void repaint(ImageCanvas c) {
		repaint(c, curBox);
	}
	
	public void stop() {
		if (flashing != null)
			flashing.stopRunning();
		repaint(canvas);
	}
	
	private void saveLocation(Setting setting) {
		if (!curBox.hasAllPoints()) {
			return;
		}
		
		File location = new File("location_data/" + setting.getFileName() + ".txt");
		
		try {
			FileWriter stream = new FileWriter(location);
			int z = canvas.getZoom();
			stream.write(curBox.getTopLeftCorner(z).x / z + " ");
			stream.write(curBox.getTopLeftCorner(z).y / z + " ");
			stream.write(curBox.getWidth(z) / z + " ");
			stream.write(curBox.getHeight(z) / z + " ");
			
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveImage(ImageCanvas c, Setting setting) {
		if (!curBox.hasAllPoints()) {
			return;
		}
		
		File image = new File("images/" + setting.getFileName() + ".png");
		
		try {
			int z = canvas.getZoom();
			ImageIO.write(
				canvas.getImage().getBufferedImage().getSubimage(curBox.getTopLeftCorner(z).x / z, curBox.getTopLeftCorner(z).y / z, curBox.getWidth(z) / z, curBox.getHeight(z) / z), 
				"png", 
				image
			);
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
