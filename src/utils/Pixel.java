package utils;

public class Pixel {
	public final int a, r, g, b;
	private int rgb;
	
	public Pixel(int r, int g, int b) {
		this.a = 0;
		this.r = r;
		this.g = g;
		this.b = b;
		
		this.rgb = toRGB();
	}
	
	public Pixel(int rgb) {
		//get alpha
	    a = (rgb>>24) & 0xff;

	    //get red
	    r = (rgb>>16) & 0xff;

	    //get green
	    g = (rgb>>8) & 0xff;

	    //get blue
	    b = rgb & 0xff;
	    
	    this.rgb = rgb;
	}
	
	private boolean isInRange(int a, int b, int range) {
		return (Math.abs(a - b) <= range);
	}
	
	public boolean inRange(Pixel p, int range) {
		for (int x = 0; x < 4; x++) {
			if (!isInRange(rgb >> (8 * x), p.rgb >> (8 * x), range)) {
				return false;
			}
		}
		
		return true;
	}
	
	public int toRGB() {
		int rgb = a;
		rgb = rgb << 8 + r;
		rgb = rgb << 8 + g;
		rgb = rgb << 8 + b;
		
		return rgb;
	}
	
	public String toString() {
		return "r: " + r + ", g: " + g + ", b: " + b;
	}
}
