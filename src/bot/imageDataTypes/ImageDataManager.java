package bot.imageDataTypes;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import bot.Bot;
import utils.CustomImage;

public class ImageDataManager {
	// will hold all the references to ImageDataTypes
	public static final ImageDataPoint burnLocation, perfectBurn, shiftLocation;
	public static final ImageDataArea restartRef, skipRef, startRef;
	
	static {
		//ImageDataPoint
		burnLocation = loadImageDataPoint("Burn_Location");
		perfectBurn = loadImageDataPoint("Perfect_Burn");
		shiftLocation = loadImageDataPoint("Shift_Location");
		
		//ImageDataArea
//		eRestart = loadImageDataArea("E-Restart");
		startRef = loadImageDataArea("Start_Ref");
		restartRef = loadImageDataArea("Restart_Ref");
		skipRef = loadImageDataArea("Skip_Ref");
	}
	
	private static ImageDataPoint loadImageDataPoint(String path) {
		try {
			return new ImageDataPoint(
				loadCustomImage(getImagePath(path)).getPixel(0, 0),
				LocationData.LoadLocationData(new File(getLocationPath(path)))
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static ImageDataArea loadImageDataArea(String path) {
		try {
			return new ImageDataArea(
				loadCustomImage(getImagePath(path)), 
				LocationData.LoadLocationData(new File(getLocationPath(path)))
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static CustomImage loadCustomImage(String path) throws IOException {
		System.out.println("Loading " + path);
		return new CustomImage(ImageIO.read(new File(path)));
	}
	
	public static String getDirectory() throws URISyntaxException {
		return new File(Bot.class.getProtectionDomain().getCodeSource().getLocation()
			    .toURI()).getPath().replaceAll("\\\\", "/");
	}
	
	private static String getImagePath(String str) {
		try {
			String dir = getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/"));
			return dir + "/images/" + str + ".png";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "images/" + str + ".png";
	}
	
	private static String getLocationPath(String str) {
		try {
			String dir = getDirectory();
			dir = dir.substring(0, dir.lastIndexOf("/"));
			return dir + "/location_data/" + str + ".txt";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "location_data/" + str + ".txt";
	}
}
