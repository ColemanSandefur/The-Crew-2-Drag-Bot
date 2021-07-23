package bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
		
		String character = data.next();
		
		if (character.equalsIgnoreCase("shift")) {
			this.character = KeyEvent.VK_SHIFT;
		} else {
			this.character = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(character.charAt(0));
		}
		
	}
	
	public void run(Robot robot) {
		// For debug
		// System.out.printf("pushing %c for %d milliseconds with a %d millisecond delay after hitting %d gear\n", character, durationMS, delayMS, gearNumber);
		robot.delay(this.delayMS);
		robot.keyPress(this.character);
		robot.delay(this.durationMS);
		robot.keyRelease(this.character);
	}
}

@SuppressWarnings("unchecked")
public class ShiftEvents {
	public static final ArrayList<Event>[] events;
	public static final int NUM_GEARS = 10;
	
	static {
		events = new ArrayList[NUM_GEARS];
		
		for (int i = 0; i < NUM_GEARS; i++) {
			events[i] = new ArrayList<Event>();
		}
		
		String dir;
		try {
			dir = ImageDataManager.getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/"));
			dir = dir + "/shift_events.txt";
			
			File file = new File(dir);
			
			Scanner fileData = new Scanner(file);
			
			while (fileData.hasNext()) {
				Event e = new Event(fileData);
				events[e.gearNumber].add(e);
			}
		} catch (Exception e) {
			System.out.println("Failed to load shift events");
			e.printStackTrace();
		}
	}
	
	static void runGear(int gear) {
		for (Event event : ShiftEvents.events[gear]) {
			new Thread() {
				@Override
				public void run() {
					try {
						event.run(new Robot());
					} catch (AWTException e) {
						System.out.println("Failed creating a robot for running an event for gear number " + gear);
						e.printStackTrace();
					}
				}
			}.start();
			
		}
	}
}
