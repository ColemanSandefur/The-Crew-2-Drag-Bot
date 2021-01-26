package setting_editor.settings;

import setting_editor.image_canvas.ImageCanvas;

public class Setting<T extends SettingCallback> {
	private String name, fileName;
	private T callback;
	
	public Setting(T cb, String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
		cb.initialize(this);
		
		callback = cb;
	}
	
	public String getFileName() { return fileName; }
	
	public String getName() { return name; }
	
	public String toString() {
		return name;
	}
	
	public void selected(boolean selected) {
		if (selected) {
			callback.onSelected();
		} else {
			callback.onDeselected();
		}
	}
	
	public T getCallback() { return callback; }
	
	public void save(ImageCanvas canvas) {
		
	}
}
