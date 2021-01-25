package bot.imageDataTypes;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LocationData {
	protected Rectangle location;
	
	public void setLocationData(Rectangle rect) {
		location = rect;
	}
	
	public Rectangle getLocationData() {
		return location;
	}
	
	public static Rectangle LoadLocationData(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
			
		Rectangle r = new Rectangle(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
		s.close();
		
		return r;
	}
}
