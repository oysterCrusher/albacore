package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// The boundary enclosing the world that contains the player and all enemies. A rectangle.
public class Boundaries {
	
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Rectangle boundingRect;
	private Texture boundaryTexture;
	
	private static float t = 4f; // Thickness
	
	public Boundaries(OrthographicCamera camera) {
		this.camera = camera;
		
		batch = new SpriteBatch();
		
		boundingRect = new Rectangle(120, 120, 1280 - 120 * 2, 720 - 120 * 2);
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
	
	public void render() {
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
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
		batch.end();
	}
	
}
