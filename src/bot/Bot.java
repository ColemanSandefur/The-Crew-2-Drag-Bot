package bot;

import java.awt.Robot;

import bot.imageDataTypes.ImageDataManager;
import bot.stages.DragStage;
import bot.stages.WarmupStage;

public class Bot extends Thread {
	boolean running;
	Robot robot;
	
	private void runLoop() {

		if (RaceState.shouldStartNextGame(robot)) {
			RaceState.startNextGame(robot);
		}
		
		if (!RaceState.midRace) {
			WarmupStage.run(robot);
			
			if (ImageDataManager.perfectBurn.isOnScreen(robot)) {
				System.out.println("Perfect Burn");
				RaceState.midRace = true;
			} else {
				return;
			}
		}
		
		//Will trigger after the car hasn't finished for a while
		if (RaceState.shouldResetRound(robot)) {
			RaceState.resetRound(robot);
			return;
		}
		
		DragStage.run(robot);
	}
	
	public void run() {
		// Start of bot
		
		try {
			robot = new Robot();
			running = true;
			CarState.initialize(robot);
			
			Bot.sleep(1000);
			
			while (running) {
				runLoop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
