package pong;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author matejkramny
 */
public class Main {
	
	public long lastFrame; // UNIX Time at last frame
	public int fps; // Frames per Second
	public long lastFPS; // Last UNIX Time FPS was refreshed
	
	public boolean vsync = true; // VSync enabled/disabled
	public boolean fullscreen = false; // Fullscreen switch
	
	public boolean running = true; // App shuts down if false
	
	public DisplayMode displayMode; // Current display mode
	
	public Pad padLeft = new Pad().init(12, 10, 30, 150, 0f, 1f, 0f);
	public Pad padRight = new Pad().init(760, 10, 30, 150, 0f, 1f, 0f);
	public Ball ball = new Ball().init(10, 10, 20, 20, 1f, 0f, 0f);
	public Wall[] walls = new Wall[4];
	
	private void run () {
		try {
			this.setDisplayMode(new DisplayMode(800, 600), fullscreen);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace(System.err);
			System.exit(0);
		}
		
		// Walls
		// we have 4 walls, surrounding the game area.
		walls[0] = new Wall().init(10, 10, 2, displayMode.getHeight() - 20).setCollision();
		walls[1] = new Wall().init(displayMode.getWidth() - 10, 10, 2, displayMode.getHeight() - 18).setCollision();
		walls[2] = new Wall().init(10, 10, displayMode.getWidth() - 20, 2).setCollision();
		walls[3] = new Wall().init(10, displayMode.getHeight() - 10, displayMode.getWidth() - 20, 2).setCollision();
		
		// Pads & balls
		padLeft.main = this;
		padRight.main = this;
		ball.main = this;
		
		// Vertical position
		padLeft.resetY();
		padRight.resetY();
		ball.resetX();
		ball.resetY();
		
		// key mapping
		padLeft.up_key = Keyboard.KEY_W;
		padLeft.down_key = Keyboard.KEY_S;
		padRight.up_key = Keyboard.KEY_UP;
		padRight.down_key = Keyboard.KEY_DOWN;
		
		this.initGL();
		this.getDelta();
		lastFPS = this.getTime();
		
		// Run loop
		while (!Display.isCloseRequested() && running) {
			int delta = this.getDelta();
			
			this.update(delta);
			this.drawGL();
			
			this.updateFPS();
			Display.update();
			Display.sync(100);
		}
		
		Display.destroy();
	}
	
	private void update (int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			running = false;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			vsync = !vsync;
			
			Display.setVSyncEnabled(vsync);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			fullscreen = !fullscreen;
			
			try {
				// Toggle fullscreen mode
				if (fullscreen)
					this.setDisplayMode(Display.getDesktopDisplayMode(), fullscreen);
				else
					this.setDisplayMode(new DisplayMode(800, 600), fullscreen);
			} catch (LWJGLException e) {
				e.printStackTrace(System.err);
			}
		}
		
		padLeft.update(delta);
		padRight.update(delta);
		ball.update(delta);
	}
	
	private void drawGL () {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		// Draw walls
		for (int i = 0; i < walls.length; i++)
			walls[i].draw();
		
		// Draw other objects
		padLeft.draw ();
		padRight.draw ();
		ball.draw ();
	}
	
	private void initGL () {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, displayMode.getWidth(), 0, displayMode.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Keyboard.enableRepeatEvents(true);
		
		Display.setVSyncEnabled(vsync);
	}
	
	private void setDisplayMode (DisplayMode target, boolean fs) throws LWJGLException {
		// DisplayModes are equal - no work necessary
		if (Display.isFullscreen() == fs && this.compareDisplayModes(target, displayMode))
			return;
		
		fullscreen = fs;
		
		Display.setDisplayMode(target);
		Display.setFullscreen(fs);
		
		displayMode = target;
	}
	
	// Returns true when display modes are the same
	public boolean compareDisplayModes (DisplayMode dp1, DisplayMode dp2) {
		if (dp1 == null || dp2 == null)
			return false;
		
		if (dp1.getWidth() == dp2.getWidth() && dp1.getHeight() == dp2.getHeight() && dp1.getBitsPerPixel() == dp2.getBitsPerPixel() && dp1.getFrequency() == dp2.getFrequency())
			return true;
		
		return false;
	}
	
	// Returns time in milliseconds
	public long getTime () {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	// Calculates fps and updates the fps variable
	public void updateFPS () {
		if (getTime() - lastFPS > 1000) {
			fps = 0;
			lastFPS += 1000;
		}
		
		fps++;
	}
	
	// Returns number of milliseconds since last update
	public int getDelta () {
		long time = getTime();
		int delta = (int)(time-lastFrame);
		
		lastFrame = time;
		
		return delta;
	}
	
	public static void main (String[] args) {
		new Main().run();
	}
}
