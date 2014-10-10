package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Boundary {

	private Rectangle boundingRect;
	private Texture boundaryTexture;
	
	private static float t = 4f; // Draw thickness
	
	public Boundary(int x, int y, int w, int h) {
		boundingRect = new Rectangle(x, y, w, h);
		boundaryTexture = Assets.boundary;
	}
	
	public float getLeft() {
		return boundingRect.x;
	}
	
	public float getRight() {
		return boundingRect.x + boundingRect.width;
	}
	
	public float getTop() {
		return boundingRect.y + boundingRect.height;
	}
	
	public float getBottom() {
		return boundingRect.y;
	}
	
	public float getWidth() {
		return boundingRect.width;
	}
	
	public float getHeight() {
		return boundingRect.height;
	}
	
	public void render(SpriteBatch batch) {
		// Left edge
		batch.draw(boundaryTexture,
				boundingRect.x - t, boundingRect.y - t,
				t, boundingRect.height + 2 * t,
				0, 0,
				1, 1);
		// Right edge
		batch.draw(boundaryTexture,
				boundingRect.x + boundingRect.width, boundingRect.y - t,
				t, boundingRect.height + 2 * t,
				0, 0,
				1, 1);
		// Bottom edge
		batch.draw(boundaryTexture,
				boundingRect.x, boundingRect.y - t,
				boundingRect.width, t,
				0, 0,
				1, 1);
		// Top edge
		batch.draw(boundaryTexture,
				boundingRect.x, boundingRect.y + boundingRect.height,
				boundingRect.width, t,
				0, 0,
				1, 1);
	}
	
}
