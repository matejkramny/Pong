/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author matejkramny
 */
public class Object {
	
	public Main main;
	
	public int x, y, w, h; // coordinates, width and height
	public float r, g, b; // Color
	public Collision collision; // Defines collision area
	
	public void draw () {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(x, y, 0);
			GL11.glColor3f(r, g, b);
			
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(0, h);
				GL11.glVertex2f(w, h);
				GL11.glVertex2f(w, 0);
			}
			GL11.glEnd();
		}
		GL11.glPopMatrix();
	}
	
	public void update (int delta) {
		// Checks for collisions?
	}
	
	public Object init (int x, int y, int w, int h, float r, float g, float b) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.r = r;
		this.g = g;
		this.b = b;
		
		return this;
	}
	
	public void resetY () {
		this.y = (int)((float)main.displayMode.getHeight() / 2f - (float)h / 2f);
	}
	
	public void resetX () {
		this.x = (int)((float)main.displayMode.getWidth() / 2f - (float)w / 2f);
	}
	
}
