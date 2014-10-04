package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cursor {

	private float x;
	private float y;
	private float radius;
	private Texture texture;
	
	public Cursor(Texture texture) {
		x = 0;
		y = 0;
		radius = 12;
		this.texture = texture;
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
		batch.draw(texture, x - radius, 720 - (y + radius), radius * 2, radius * 2);
	}
	
}
