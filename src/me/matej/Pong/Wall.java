/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.matej.Pong;

/**
 *
 * @author matejkramny
 */
public class Wall extends Object {
	
	public Wall init (int x, int y, int w, int h) {
		super.init(x, y, w, h, 1f, 1f, 1f); // White color
		
		return this;
	}
	
	public Wall setCollision () {
		super.collision = new Collision().init(x, y, w, h);
		
		return this;
	}
	
}
