package pong;

/**
 *
 * @author matejkramny
 */
public class Collision {
	int x, y, w, h; // Coordinates
	
	public boolean collidesWithCollision (Collision c) {
		Pixel pixels[] = this.buildPixels (this);
		Pixel cPixels[] = this.buildPixels (c);
		
		for (int i = 0; i < pixels.length; i++) {
			for (int u = 0; u < cPixels.length; u++) {
				if (pixels[i].x == cPixels[u].x && pixels[i].y == cPixels[u].y)
					return true;
			}
		}
		
		return false;
	}
	
	public Pixel[] buildPixels (Collision c) {
		// Calculates pixel area
		int num = c.w * c.h;
		Pixel pixels[] = new Pixel[num]; // Array of pixels..
		
		int i = 0;
		for (int y = 0; y < c.h; y++) {
			for (int x = 0; x < c.w; x++) {
				pixels[i] = new Pixel(x+c.x, y+c.y);
				i++;
			}
		}
		
		return pixels;
	}
	
	// Basic init..
	public Collision init (int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		return this;
	}
	
	// The pixel class
	public class Pixel {
		int x, y;
		
		public Pixel(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
