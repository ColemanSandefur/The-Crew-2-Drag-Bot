package bot;

import java.io.File;
import java.io.FileWriter;

public class BotStatistics {
	public static int racesCompleted, timesReset;
	public static long botStart;
	public static long lastFinishedRace;
	
	public static void save() {
		new Thread() {
			public double toMinutes(long millis) {
				return millis/1000.0/60.0;
			}
			@Override
			public void run() {
				try {
					File file = new File("BotStatistics.txt");
					
					FileWriter out = new FileWriter(file);
					out.write(String.format("finished %d races\n", racesCompleted));
					out.write(String.format("reset %d times\n", timesReset));
					if (botStart != 0 && lastFinishedRace != 0) {
						out.write(String.format("ran for %.2f minutes\n", toMinutes(lastFinishedRace - botStart)));
					}
					out.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
