package setting_editor;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import setting_editor.image_canvas.PictureGUI;
import setting_editor.settings.Setting;
import utils.CustomImage;

public class EditorGUI extends JFrame {

	private JPanel contentPane;
	private JPanel fcJPanel = new JPanel();
	private JComboBox<Setting[]> settingSelection;
	private EditorGUI test = this;
	private ActionListener settingSelectionListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			SettingManager.setSelectedSetting((Setting) settingSelection.getSelectedItem());
			
			test.pack();
		}
	};
	private JButton btnLoadFile;
	private JFileChooser fc = new JFileChooser();
	private PictureGUI pictureGUI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					EditorGUI frame = new EditorGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditorGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 268, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{240, 0};
		gbl_contentPane.rowHeights = new int[]{22, 25, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		settingSelection = new JComboBox(SettingManager.getAllSettings());
		GridBagConstraints gbc_settingSelection = new GridBagConstraints();
		gbc_settingSelection.insets = new Insets(0, 0, 5, 0);
		gbc_settingSelection.fill = GridBagConstraints.HORIZONTAL;
		gbc_settingSelection.gridx = 0;
		gbc_settingSelection.gridy = 0;
		contentPane.add(settingSelection, gbc_settingSelection);
		settingSelection.addActionListener(settingSelectionListener);
		if (SettingManager.getAllSettings().length > 0)
			settingSelection.setSelectedIndex(0);
		
		btnLoadFile = new JButton("Load File");
		GridBagConstraints gbc_btnLoadFile = new GridBagConstraints();
		gbc_btnLoadFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadFile.gridx = 0;
		gbc_btnLoadFile.gridy = 1;
		contentPane.add(btnLoadFile, gbc_btnLoadFile);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingManager.getSelectedSetting().getCallback().onSaved(pictureGUI.getImageCanvas());
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 2;
		contentPane.add(btnSave, gbc_btnSave);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "png", "jpg", "jpeg");
				
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(fcJPanel);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						Image i = ImageIO.read(file);
						pictureGUI.getImageCanvas().setImage(CustomImage.toCustomImage(i));
						pictureGUI.pack();
						pictureGUI.setVisible(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		pictureGUI = new PictureGUI();
		
		pack();
	}

}
