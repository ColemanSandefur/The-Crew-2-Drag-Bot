package setting_editor.image_canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import setting_editor.SettingManager;
import setting_editor.settings.Setting;
import utils.CustomImage;

public class ImageCanvas extends JPanel {
	private CustomImage image;
	private Cursor cursor = new Cursor(-10, -10, 10, Color.BLACK);
	private ImageCanvas thisPointer;
	
	public ImageCanvas() {
		thisPointer = this;
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (image != null) {
					Color c = image.getPixel(e.getX(), e.getY()).toColor();
					cursor.setColor(c);
					cursor.moveCursor(e.getX(), e.getY(), thisPointer);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
				if (image != null) {
					Color c = image.getPixel(e.getX(), e.getY()).toColor();
					cursor.setColor(c);
					cursor.moveCursor(e.getX(), e.getY(), thisPointer);
				}
			}
			
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Setting setting = SettingManager.getSelectedSetting();
				
				setting.getCallback().addPoint(e.getX(), e.getY(), thisPointer);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {
				Setting setting = SettingManager.getSelectedSetting();
				
				setting.getCallback().addPoint(e.getX(), e.getY(), thisPointer);
			}
			
		});
	}
	
	public void setImage(CustomImage image) {
		this.image = image;
		BufferedImage i = image.getBufferedImage();
		
		this.setPreferredSize(new Dimension(i.getWidth(), i.getHeight()));
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (image != null) {
			//needs to be painted first!
			g.drawImage((Image) image.getBufferedImage(), 0, 0, this);
			
			if (SettingManager.getSelectedSetting() != null)
				SettingManager.getSelectedSetting().getCallback().paint(this, g);
			
			//should be on top of everything
			cursor.drawCursor(g);
		}
	}
	
	public CustomImage getImage() { return image; }
}
