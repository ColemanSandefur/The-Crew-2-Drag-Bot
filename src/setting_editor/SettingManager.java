package setting_editor;

import setting_editor.settings.AreaCallback;
import setting_editor.settings.PixelCallback;
import setting_editor.settings.Setting;

public class SettingManager {
	public static final Setting<AreaCallback> eRestart, restartRef, skipRef;
	public static final Setting<PixelCallback> burnLocation, perfectBurn, shiftLocation;
	private static Setting selectedSetting = null;
	
	static {
		eRestart = loadArea("E-Restart", "E-Restart");
		restartRef = loadArea("Restart Ref", "Restart_Ref");
		skipRef = loadArea("Skip Ref", "Skip_Ref");
		
		burnLocation = loadPixel("Burn Location", "Burn_Location");
		perfectBurn = loadPixel("Perfect Burn", "Perfect_Burn");
		shiftLocation = loadPixel("Shift Location", "Shift_Location");
	}
	
	private static Setting<PixelCallback> loadPixel(String name, String fileName) {
		return new Setting<PixelCallback>(new PixelCallback(), name, fileName);
	}
	
	private static Setting<AreaCallback> loadArea(String name, String fileName) {
		return new Setting<AreaCallback>(new AreaCallback(), name, fileName);
	}
	
	@SuppressWarnings("rawtypes")
	public static Setting[] getAllSettings() {
		return new Setting[] {
			eRestart, restartRef, skipRef,
			burnLocation, perfectBurn, shiftLocation
		};
	}
	
	public static void setSelectedSetting(Setting setting) {
		if (SettingManager.selectedSetting != null) {
			selectedSetting.selected(false);
		}
		
		selectedSetting = setting;
		selectedSetting.selected(true);
	}
	
	public static Setting getSelectedSetting() { return selectedSetting; }
}