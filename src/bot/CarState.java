package bot;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import bot.imageDataTypes.ImageDataManager;

abstract class CarShifting extends Thread {
	private Robot screenRobot;
	private Robot shiftRobot;
	
	@Override
	public void run() {
		try {
			screenRobot = new Robot();
			shiftRobot = new Robot();
			while (Bot.running) {
				if (ImageDataManager.shiftLocation.isOnScreen(screenRobot)) {
					shift();
				};
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shift() {
		shiftRobot.keyPress(KeyEvent.VK_E);
		shiftRobot.delay(25);
		shiftRobot.keyRelease(KeyEvent.VK_E);
		didShift();
	}
	
	public abstract void didShift();
}

public class CarState {
	private static int gear = 1;
	private static boolean accelerating;
	private static Robot robot;
	private static CarShifting carShifting;
	
	
	public static void initialize(Robot robot) {
		CarState.robot = robot;
		carShifting = new CarShifting() {

			@Override
			public void didShift() {
				
				//don't trust gear number, it is usually off
				gear++;
				
				if (CarState.getGear() == 5) {
					new KeyPress() {

						@Override
						public void PressKey(Robot robot) {
							System.out.println("Adjusting");
							robot.delay(1500);
							robot.keyPress(KeyEvent.VK_D);
							robot.delay(100);
							robot.keyRelease(KeyEvent.VK_D);
						}
						
					}.start();
				}
			}
			
		};
		carShifting.start();
	}
	
	public static void resetState() {
		gear = 1;
		setAccelerating(false);
	}
	
	public static int getGear() { return gear; }
	
	public static void setAccelerating(boolean a) {
		if (a == accelerating) {
			return;
		}
		
		if (a) {
			robot.keyPress(KeyEvent.VK_W);
		} else {
			robot.keyRelease(KeyEvent.VK_W);
		}
		
		accelerating = a;
	}
}
