package setting_editor.image_canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import setting_editor.settings.Setting;

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
	
	public int getWidth() {
		return Math.abs(points[1].x - points[0].x);
	}
	
	public int getHeight() {
		return Math.abs(points[1].y - points[0].y);
	}
	
	public Point getTopLeftCorner() {
		int x = Math.min(points[0].x, points[1].x);
		int y = Math.min(points[0].y, points[1].y);
		
		return new Point(x, y);
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
				_repaint(canvas);
			}
			
			public void _repaint(ImageCanvas c) {
				if (!_box.hasAllPoints()) {
					return;
				}
				
				int offset = 1;
				
				c.repaint(_box.getTopLeftCorner().x, _box.getTopLeftCorner().y, _box.getWidth() + offset, _box.getHeight() + offset);
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
		
		g.setColor(c);
		
		g.drawRect(curBox.getTopLeftCorner().x, curBox.getTopLeftCorner().y, curBox.getWidth(), curBox.getHeight());
		
		g.setColor(c2);
		
		g.drawRect(curBox.getTopLeftCorner().x + 1, curBox.getTopLeftCorner().y + 1, curBox.getWidth() - 2, curBox.getHeight() - 2);
	}
	
	public void repaint(ImageCanvas c) {
		if (!curBox.hasAllPoints() || c == null) {
			return;
		}
		int offset = 1;
		c.repaint(curBox.getTopLeftCorner().x, curBox.getTopLeftCorner().y, curBox.getWidth() + offset, curBox.getHeight() + offset);
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
		
		File location = new File("location_data/" + setting.getFileName() + "-generated.txt");
		
		try {
			FileWriter stream = new FileWriter(location);
			
			stream.write(curBox.getTopLeftCorner().x + " ");
			stream.write(curBox.getTopLeftCorner().y + " ");
			stream.write(curBox.getWidth() + " ");
			stream.write(curBox.getHeight() + " ");
			
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
		
		File image = new File("images/" + setting.getFileName() + "-generated.png");
		
		try {
			ImageIO.write(c.getImage().getBufferedImage().getSubimage(curBox.getTopLeftCorner().x, curBox.getTopLeftCorner().y, curBox.getWidth(), curBox.getHeight()), "png", image);
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
