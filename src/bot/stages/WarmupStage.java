package bot.stages;

import java.awt.Robot;

import bot.CarState;
import bot.imageDataTypes.ImageDataManager;

public class WarmupStage implements Stage {
	
	// will be called each time during the bot's main loop
	public static void run(Robot robot) {
//		if (ImageDataManager.burnLocation.isOnScreen(robot)) {
//			System.out.println("Is on screen");
//		} else {
//			System.out.println("looking for: " + ImageDataManager.burnLocation.getPixel().toString());
//			System.out.println("got: " + ImageDataManager.burnLocation.createScreenCapture(robot).toString());
//		}
//		System.out.println(ImageDataManager.shiftLocation.isOnScreen(robot) ? "Is on screen" : "");
		CarState.setAccelerating(!ImageDataManager.burnLocation.isOnScreen(robot));
//		System.out.println("warming tires");
	}
}
