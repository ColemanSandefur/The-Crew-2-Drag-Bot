package bot;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class CarState {
	private static int gear = 1;
	private static boolean accelerating;
	private static Robot robot;
	
	public static void initialize(Robot robot) {
		CarState.robot = robot;
	}
	
	public static void resetState() {
		gear = 1;
		setAccelerating(false);
	}
	
	public static void shiftUp() {
		gear++;
		robot.keyPress(KeyEvent.VK_E);
		robot.delay(25);
		robot.keyRelease(KeyEvent.VK_E);
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
