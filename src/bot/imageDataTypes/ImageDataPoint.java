package bot.imageDataTypes;

import java.awt.Rectangle;
import java.awt.Robot;

import utils.CustomImage;
import utils.Pixel;

public class ImageDataPoint extends LocationData implements ImageDataType{
	Pixel pixel;
	
	ImageDataPoint(Pixel p, Rectangle locationData) {
		pixel = p;
		location = locationData;
	}
	
	public Pixel createScreenCapture(Robot robot) {
		return CustomImage.createScreenCapture(robot, location).getPixel(0, 0);
	}
	
	public boolean isOnScreen(Robot robot) {
		return createScreenCapture(robot).inRange(pixel, 5);
	}
	
	public Pixel getPixel() { return pixel; }
}
