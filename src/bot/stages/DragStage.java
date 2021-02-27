package bot.stages;

import java.awt.Robot;

import bot.CarState;
import bot.RaceState;

public class DragStage implements Stage {
	// will be called each time during the bot's main loop
	public static void run(Robot robot, boolean hasStarted) {
		CarState.setAccelerating(hasStarted);
		
		if (RaceState.shouldStartNextRound(robot)) {
			RaceState.startNextRound(robot);
			robot.delay(500);
		}
		
	}
}
