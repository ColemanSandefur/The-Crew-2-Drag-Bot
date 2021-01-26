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
import utils.Pixel;

@SuppressWarnings({"serial", "rawtypes"})
public class ImageCanvas extends JPanel {
	private CustomImage image;
	private Cursor cursor = new Cursor(-10, -10, 10, Color.BLACK);
	private ImageCanvas thisPointer;
	private Image scaledImage;
	int zoom = 1;
	
	public ImageCanvas() {
		
		thisPointer = this;
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (image != null) {
					Color c = image.getPixel(e.getX() / zoom, e.getY() / zoom).toColor();
					cursor.setColor(c);
					cursor.moveCursor(e.getX(), e.getY(), thisPointer);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
				if (image != null) {
					Pixel p = image.getPixel(e.getX() / zoom, e.getY() / zoom);
					if (p != null) {
						Color c = p.toColor();
						cursor.setColor(c);
						cursor.moveCursor(e.getX(), e.getY(), thisPointer);
					}
					
				}
			}
			
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
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
		
		setZoom(zoom);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (image != null) {
			//needs to be painted first!
			if (scaledImage == null) {
				setZoom(zoom);
			}
			g.drawImage(scaledImage, 0, 0, this);
			
			if (SettingManager.getSelectedSetting() != null)
				SettingManager.getSelectedSetting().getCallback().paint(this, g);
			
			//should be on top of everything
			cursor.drawCursor(g);
		}
	}
	
	public void setZoom(int x) {
		this.zoom = x;
		if (image == null) {
			return;
		}
		BufferedImage bi = image.getBufferedImage();
		scaledImage = bi.getScaledInstance(bi.getWidth() * zoom, bi.getHeight() * zoom, Image.SCALE_REPLICATE);
		this.repaint();
	}
	
	public int getZoom() { return zoom; }
	
	public Image getScaledImage() { return scaledImage; }
	
	public CustomImage getImage() { return image; }
	
	@Override
	public Dimension getPreferredSize() {
		if (scaledImage == null) {
			setZoom(1);
		}
		
		return new Dimension(scaledImage.getWidth(null), scaledImage.getHeight(null));
	}
}
