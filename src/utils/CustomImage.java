package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class CustomImage {
	BufferedImage image;
	int width, height;
	
	public CustomImage(BufferedImage image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public BufferedImage getBufferedImage() { return image; }
	
	public Pixel getPixel(int x, int y) {
		if (x > width || y > height) {
			return null;
		}
		return new Pixel(image.getRGB(x, y));
	}
	
	public static CustomImage toCustomImage(Image img) {
		return new CustomImage(toBufferedImage(img));
	}
	
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return ((BufferedImage) img);
		}
		
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img,  0,  0,  null);
		bGr.dispose();
		
		return ((BufferedImage) bimage);
	}
	
	public static CustomImage createScreenCapture(Robot robot, Rectangle rect) {
		return new CustomImage((BufferedImage) robot.createScreenCapture(rect));
	}
	
	public boolean imgEquals(CustomImage o) {
		int minWidth = Math.min(image.getWidth(), o.image.getWidth());
		int minHeight = Math.min(image.getHeight(), o.image.getHeight());
		
		for (int j = 0; j < minHeight; j++) {
			for (int i = 0; i < minWidth; i++) {
				if (!getPixel(i, j).inRange(o.getPixel(i, j), 5)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
