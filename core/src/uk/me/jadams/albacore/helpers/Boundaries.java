package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
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
	
	private static float t = 2; // Thickness
	
	public Boundaries(OrthographicCamera camera) {
		this.camera = camera;
		
		batch = new SpriteBatch();
		
		boundingRect = new Rectangle(10, 10, 1260, 700);
		boundaryTexture = new Texture(Gdx.files.internal("boundary.png"));
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
