package bot;

import java.awt.Robot;

public abstract class KeyPress extends Thread {
	
	@Override
	public void run() {
		try {
			PressKey(new Robot());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void PressKey(Robot robot);
}
