package setting_editor.image_canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import setting_editor.SettingManager;
import utils.CustomImage;
import javax.swing.JScrollPane;

public class PictureGUI extends JFrame {
	private CustomImage image;
	
	private JPanel contentPane;
	private ImageCanvas panel;
	private JScrollPane scrollPane;
	
	public PictureGUI() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new ImageCanvas();
//		contentPane.add(panel, BorderLayout.NORTH);
		scrollPane.setViewportView(panel);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("hidden");
				SettingManager.getSelectedSetting().getCallback().onWindowClosed();
				scrollPane = new JScrollPane();
				scrollPane.setViewportView(scrollPane);
			}
		});
	}
	
	public ImageCanvas getImageCanvas() { return panel; }
	public JScrollPane getScrollPane() { return scrollPane; }
	
	public void setImage(CustomImage image) {
		this.image = image;
		this.repaint();
	}
}
