package bot.imageDataTypes;

import java.awt.Rectangle;
import java.awt.Robot;

import utils.CustomImage;

public class ImageDataArea extends LocationData implements ImageDataType{
	CustomImage image;
	
	ImageDataArea(CustomImage image, Rectangle locationData) {
		this.image = image;
		location = locationData;
	}
	
	public CustomImage createScreenCaptrue(Robot robot) {
		return CustomImage.createScreenCapture(robot, location);
	}
	
	public boolean isOnScreen(Robot robot) {
		return createScreenCaptrue(robot).imgEquals(image);
	}
	
	public CustomImage getCustomImage() { return image; }
}
