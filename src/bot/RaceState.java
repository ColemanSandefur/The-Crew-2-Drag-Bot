package bot;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import bot.imageDataTypes.ImageDataManager;

public class RaceState {
	public static boolean midRace;
	public static int roundNumber = 1;
	public static int gamesPlayed = 0;
	
	public static void resetRaceVars() {
		resetRoundVars();
		roundNumber = 1;
	}
	
	public static boolean shouldResetRound(Robot robot) {
		return roundNumber < 4 && ImageDataManager.eRestart.isOnScreen(robot);
	}
	
	public static void resetRound(Robot robot) {
		System.out.println("Attempting Reset");
		robot.keyPress(KeyEvent.VK_B);
		robot.delay(30);
		robot.keyRelease(KeyEvent.VK_B);
		robot.delay(30);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		resetRoundVars();
	}
	
	public static void resetRoundVars() {
		midRace = false;
		
		CarState.resetState();
	}
	
	public static boolean shouldStartNextGame(Robot robot) {
		return ImageDataManager.restartRef.isOnScreen(robot);
	}
	
	public static void startNextGame(Robot robot) {
		System.out.println("Game Finished!\n\n Starting New Game \n");
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
