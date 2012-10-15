/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.matej.Pong;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author 12039762
 */
public class SplashScreen {
	
	public Texture splash;
	public long splashTimeLeft;
	public boolean done = false;
	
	public Main main;
	
	public SplashScreen () {
	}
	
	public void init () {
		try {
			splash = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("me/matej/Pong/PongBG.png"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		splashTimeLeft = 2000;
	}
	
	public void update (int delta) {
		splashTimeLeft -= delta;
		if (splashTimeLeft <= 0) {
			done = true;
			splashTimeLeft = 0;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			//main.gamePaused = false;
		}
	}
	
	public void render () {
		GL11.glLoadIdentity();
		
		Color.white.bind();
		splash.bind(); // or GL11.glBind(texture.getTextureID());
		
		int texW = splash.getTextureWidth(), texH = splash.getTextureHeight();
		
		DisplayMode dp = main.displayMode;
		GL11.glTranslatef(dp.getWidth()/2 - 384/2, dp.getHeight()/2 - texH/2, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f,0f);
			GL11.glVertex2f(0f,0f);
			GL11.glTexCoord2f(1f,0f);
			GL11.glVertex2f(texW,0f);
			GL11.glTexCoord2f(1f,1f);
			GL11.glVertex2f(texW,texH);
			GL11.glTexCoord2f(0f,1f);
			GL11.glVertex2f(0f,texH);
		GL11.glEnd();
	}
	
}
