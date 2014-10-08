package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cursor {

	private float x;
	private float y;
	private float radius;
	
	public Cursor() {
		x = 0;
		y = 0;
		radius = 12;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(Assets.cursor, x - radius, 720 - (y + radius), radius * 2, radius * 2);
	}
	
}
