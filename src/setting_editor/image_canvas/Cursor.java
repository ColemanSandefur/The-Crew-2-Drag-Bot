package setting_editor.image_canvas;

import java.awt.Color;
import java.awt.Graphics;

public class Cursor {
	int x, y, width;
	Color color;
	
	public Cursor(int x, int y, int width, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		color = c;
	}
	
	public void moveCursor(int x, int y, ImageCanvas c) {
		int offset = 1;
		if (this.x != x || this.y != y) {
			c.repaint(this.x, this.y, width + offset, width + offset);
			this.x = x;
			this.y = y;
			c.repaint(this.x, this.y, width + offset, width + offset);
		}
	}
	
	private Color invertColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
	
	public void setColor(Color c) { color = c; }
	
	public void drawCursor(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, width);
		g.setColor(invertColor(color));
		g.drawRect(x, y, width, width);
	}
}
