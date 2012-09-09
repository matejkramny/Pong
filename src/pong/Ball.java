/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

/**
 *
 * @author matejkramny
 */
public class Ball extends Object {
	float dirX = .3f, dirY; // Direction x,y *delta
	
	@Override
	public Ball init (int x, int y, int w, int h, float r, float g, float b) {
		super.init(x, y, w, h, r, g, b);
		super.collision = new Collision().init(x, y, w, h);
		
		return this;
	}
	
	public void updateDirY (Pad pad) {
		// hit upper pad or lower pad?
		// pad middle
		int yPadMiddle = (int)((float)pad.y + (float)(pad.h / 2));
		int yBallMiddle = (int)((float)y + (float)(h / 2));
		
		int diff = yBallMiddle - yPadMiddle;
		
		if (diff > 0) {
			float angle = (float)diff / (float)(pad.h / 2);
			dirY = angle / 1.5f;
		} else if (diff < 0) {
			float angle = (float)(diff * -1) / (float)(pad.h / 2);
			dirY = (angle / 1.5f) * -1;
		} else {
			// Straight - no angle
			dirY = 0f;
		}
	}
	
	@Override
	public void update (int delta) {
		Pad padL = main.padLeft, padR = main.padRight;
		if (x+w >= padR.x) {
			if (padR.y <= y+h && padR.y+padR.h >= y) {
				x = padR.x - 1 - w;
				dirX *= -1; // Invert direction
				
				updateDirY(padR);
			} else {
				// Pad didn't catch
				dirY = 0f;
				dirX = .3f;
				super.resetX();
				super.resetY();
				System.out.println ("Player on right lost!");
			}
		} else if (x <= padL.x+padL.w) {
			if (padL.y <= y+h && padL.y+padL.h >= y) {
				x = main.padLeft.x+main.padLeft.w+1;
				dirX *= -1;
				
				updateDirY(padL);
			} else {
				// Pad didn't catch
				dirY = 0f;
				dirX = .3f;
				super.resetX();
				super.resetY();
				System.out.println ("Player on left lost!");
			}
		}
		
		// Walls collision
		if (y <= main.walls[2].y)
			dirY *= -1;
		if (y+h >= main.walls[3].y)
			dirY *= -1;
		
		// Update movement
		// X
		if (dirX > 0f)
			x += dirX * delta;
		else
			x -= (dirX * -1) * delta;
		// Y
		if (dirY > 0f)
			y += dirY * delta;
		else 
			y -= (dirY * -1) * delta;
	}
}
