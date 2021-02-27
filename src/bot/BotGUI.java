package bot;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class BotGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblRacesFinished;
	private JLabel lblRacesReset;
	private JLabel lblTimeRan;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public BotGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 216, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{213, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblRacesFinished = new JLabel("RacesFinished");
		GridBagConstraints gbc_lblRacesFinished = new GridBagConstraints();
		gbc_lblRacesFinished.insets = new Insets(0, 0, 5, 0);
		gbc_lblRacesFinished.gridx = 0;
		gbc_lblRacesFinished.gridy = 0;
		contentPane.add(lblRacesFinished, gbc_lblRacesFinished);
		
		lblRacesReset = new JLabel("RacesReset");
		GridBagConstraints gbc_lblRacesReset = new GridBagConstraints();
		gbc_lblRacesReset.insets = new Insets(0, 0, 5, 0);
		gbc_lblRacesReset.gridx = 0;
		gbc_lblRacesReset.gridy = 1;
		contentPane.add(lblRacesReset, gbc_lblRacesReset);
		
		lblTimeRan = new JLabel("Time Ran");
		GridBagConstraints gbc_lblTimeRan = new GridBagConstraints();
		gbc_lblTimeRan.gridx = 0;
		gbc_lblTimeRan.gridy = 2;
		contentPane.add(lblTimeRan, gbc_lblTimeRan);
		
		pack();
		
		new Thread() {
			@Override
			public void run() {
				while (Bot.running) {
					try {
						if (BotStatistics.getBotStart() > 0) {
							lblTimeRan.setText(String.format("Has been running for %.2f minutes", BotStatistics.toMinutes(System.currentTimeMillis() - BotStatistics.getBotStart())));
						}
						
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void setRacesFinished(int races) {
		lblRacesFinished.setText(String.format("Finished %d races", races));
	}
	
	public void setRacesReset(int reset) {
		lblRacesReset.setText(String.format("Reset %d times", reset));
	}

}
