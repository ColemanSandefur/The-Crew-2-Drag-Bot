package bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.KeyStroke;

import bot.imageDataTypes.ImageDataManager;

/*
 * Shift data will be arranged like this:
 * 
 *  #{number}      #{number}        #{number}     #{character}
 * Gear Number | Delay after gear |  Duration | Key to be pressed
 * 
 * Each line is a new event
 */

class Event {
	public int gearNumber;
	public int delayMS;
	public int durationMS;
	public int character;
	
	public Event(Scanner data) {
		this.gearNumber = data.nextInt();
		this.delayMS = data.nextInt();
		this.durationMS = data.nextInt();
		
		String character = data.nextLine();
		
		if (character.equalsIgnoreCase("shift")) {
			this.character = KeyEvent.VK_SHIFT;
		} else {
			KeyStroke ks = KeyStroke.getKeyStroke(character.charAt(0), 0);
			this.character = ks.getKeyCode();
		}
		
	}
	
	public void run(Robot robot) {
		robot.delay(this.delayMS);
		robot.keyPress(this.character);
		robot.delay(this.durationMS);
		robot.keyRelease(this.character);
	}
}

@SuppressWarnings("unchecked")
public class ShiftEvents {
	public static final ArrayList<Event>[] events;
	
	static {
		events = new ArrayList[10];
		
		String dir;
		try {
			dir = ImageDataManager.getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/"));
			dir = dir + "/shift_events.txt";
			
			File file = new File(dir);
			
			Scanner fileData = new Scanner(file);
			
			while (fileData.hasNextLine()) {
				Event e = new Event(fileData);
				events[e.gearNumber].add(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void runGear(int gear) {
		for (Event event : ShiftEvents.events[gear]) {
			try {
				event.run(new Robot());
			} catch (AWTException e) {
				System.out.println("Failed creating a robot for running an event");
				e.printStackTrace();
			}
		}
	}
}
