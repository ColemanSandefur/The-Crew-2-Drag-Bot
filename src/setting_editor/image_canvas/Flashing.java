package setting_editor.image_canvas;

public abstract class Flashing extends Thread {
	private volatile boolean running;
	public boolean flashing;
	
	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				flashing = !flashing;
				
				onFlash(flashing);
				
				Flashing.sleep(500);
			}
			
			onStop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void onFlash(boolean flashing);
	
	public void stopRunning() {
		running = false;
		onStop();
	}
	
	public abstract void onStop();
}