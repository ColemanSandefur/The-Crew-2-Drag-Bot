package setting_editor.image_canvas;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import setting_editor.SettingManager;
import utils.CustomImage;

public class PictureGUI extends JFrame {
	private CustomImage image;
	
	private JPanel contentPane;
	private ImageCanvas panel;
	
	public PictureGUI() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new ImageCanvas();
		contentPane.add(panel, BorderLayout.CENTER);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("hidden");
				SettingManager.getSelectedSetting().getCallback().onWindowClosed();
			}
		});
	}
	
	public ImageCanvas getImageCanvas() { return panel; }
	
	public void setImage(CustomImage image) {
		this.image = image;
		this.repaint();
	}
}
