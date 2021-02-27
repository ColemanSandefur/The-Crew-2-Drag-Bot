package bot;

import java.io.File;
import java.io.FileWriter;

public class BotStatistics {
	private static int racesCompleted, timesReset;
	private static long botStart;
	private static long lastFinishedRace;
	
	public static double toMinutes(long millis) {
		return millis/1000.0/60.0;
	}
	
	public static void save() {
		new Thread() {
			
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
	
	public static void increaseRacesCompleted(int x) { 
		racesCompleted += x;
//		Bot.botGUI.setRacesFinished(racesCompleted);
	}
	
	public static void increaseRacesReset(int x) { 
		timesReset += x; 
//		Bot.botGUI.setRacesReset(timesReset);
	}
	
	public static void setLastFinishedRace(long x) {
		lastFinishedRace = x;
	}
	
	public static void setBotStart(long s) {
		botStart = s;
	}
	
	public static int getRacesCompleted() { return racesCompleted; }
	public static int getRacesReset() { return timesReset; }
	public static long getLastFinishedRace() { return lastFinishedRace; }
	public static long getBotStart() { return botStart; }
}
