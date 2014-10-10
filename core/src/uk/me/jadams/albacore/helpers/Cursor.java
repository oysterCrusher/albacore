package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cursor {

	private float x = 0f;
	private float y = 0f;
	private float size = 12f;
	
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
		batch.draw(Assets.cursor, x - size, 720 - (y + size), size * 2, size * 2);
	}
	
}
