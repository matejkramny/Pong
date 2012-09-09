/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import org.lwjgl.input.Keyboard;

/**
 *
 * @author matejkramny
 */
public class Pad extends Object {
	@Override
	public Pad init (int x, int y, int w, int h, float r, float g, float b) {
		super.init(x, y, w, h, r, g, b);
		super.collision = new Collision().init(x, y, w, h);
		
		return this;
	}
	
	public int up_key, down_key;
	
	@Override
	public void update (int delta) {
		super.update(delta);
		
		if (Keyboard.isKeyDown(up_key)) {
			y += 0.5f * delta;
		}
		
		Wall w = main.walls[3];
		if (y+h >= w.y)
			y = w.y - h;
		
		if (Keyboard.isKeyDown(down_key)) {
			y -= 0.5f * delta;
		}
		
		w = main.walls[2];
		if (y <= w.y)
			y = w.y+1;
	}
	
}
