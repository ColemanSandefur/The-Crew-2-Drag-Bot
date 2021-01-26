package bot.stages;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import bot.CarState;
import bot.RaceState;
import bot.imageDataTypes.ImageDataManager;

public class DragStage implements Stage {
	// will be called each time during the bot's main loop
	public static void run(Robot robot, boolean hasStarted) {
		CarState.setAccelerating(hasStarted);
		
		if (RaceState.shouldStartNextRound(robot)) {
			RaceState.startNextRound(robot);
			robot.delay(500);
		}
		
	}
	
	private static boolean shouldShift(Robot robot) {
		if (ImageDataManager.shiftLocation.isOnScreen(robot)) {
			return true;
		}
		return ImageDataManager.shiftLocation.isOnScreen(robot);
	}
}
