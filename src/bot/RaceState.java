package bot;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import bot.imageDataTypes.ImageDataManager;

public class RaceState {
	public static boolean midRace;
	public static boolean startedRace;
	public static int roundNumber = 1;
	public static int gamesPlayed = 0;
	public static long timeRound;
	
	public static void resetRaceVars() {
		resetRoundVars();
		roundNumber = 1;
	}
	
	public static boolean shouldResetRound(Robot robot) {
		
		if (timeRound == 0) {
			return false;
		}
		long time = System.currentTimeMillis() - (timeRound);
		return roundNumber < 4 && time > 28*1000;
	}
	
	public static void resetRound(Robot robot) {
		System.out.println("Attempting Reset");
		robot.keyPress(KeyEvent.VK_B);
		robot.delay(30);
		robot.keyRelease(KeyEvent.VK_B);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		BotStatistics.increaseRacesReset(1);;
		BotStatistics.save();
		
		resetRoundVars();
	}
	
	public static void resetRoundVars() {
		midRace = false;
		startedRace = false;
		timeRound = 0;
		
		CarState.resetState();
	}
	
	public static boolean shouldStartNextGame(Robot robot) {
		return ImageDataManager.restartRef.isOnScreen(robot);
	}
	
	public static void startNextGame(Robot robot) {
		BotStatistics.increaseRacesCompleted(1);
		BotStatistics.setLastFinishedRace(System.currentTimeMillis());
		BotStatistics.save();
		
		System.out.printf("Game #%d finished\n", BotStatistics.getRacesCompleted());
		System.out.printf("Has been running for %.2f minutes\n", BotStatistics.toMinutes(System.currentTimeMillis() - BotStatistics.getBotStart()));
		System.out.println("\n\tStarting New Game \n");
		
		robot.keyPress(KeyEvent.VK_N);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_N);
		resetRaceVars();
		gamesPlayed++;
	}
	
	public static boolean shouldStartNextRound(Robot robot) {
		return ImageDataManager.skipRef.isOnScreen(robot);
	}
	
	public static void startNextRound(Robot robot) {
		System.out.println("Starting next round");
		
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		
		resetRoundVars();
		roundNumber++;
	}
}
