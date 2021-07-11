package bot;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import bot.imageDataTypes.ImageDataManager;
import bot.stages.DragStage;
import bot.stages.WarmupStage;

public class Bot extends Thread {
	static boolean running;
//	public static BotGUI botGUI;
	
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
				CarState.setAccelerating(false);
			} else {
				return;
			}
		}
		
//		Will trigger after the car hasn't finished for a while
		if (RaceState.shouldResetRound(robot)) {
			RaceState.resetRound(robot);
			return;
		}
		
		if (!RaceState.startedRace) {
			if (ImageDataManager.startRef.isOnScreen(robot)) {
				RaceState.startedRace = true;
				CarState.setAccelerating(true);
				RaceState.timeRound = System.currentTimeMillis();
				
				ShiftEvents.runGear(0);
				
//				new KeyPress() {
//
//					@Override
//					public void PressKey(Robot robot) {
//						robot.delay(100);
//						robot.keyPress(KeyEvent.VK_SHIFT);
//						robot.delay(3000);
//						robot.keyRelease(KeyEvent.VK_SHIFT);
//					}
//					
//				}.start();
			}
		}
		
		DragStage.run(robot, RaceState.startedRace);
	}
	
	public void run() {
		// Start of bot
		
		try {
			BotStatistics.setBotStart(System.currentTimeMillis());
			robot = new Robot();
			running = true;
			CarState.initialize(robot);
			
			Bot.sleep(1000);
			
//			botGUI = new BotGUI();
//			botGUI.setVisible(true);
			
			while (running) {
				runLoop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
