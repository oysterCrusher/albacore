package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementBouncyComponent;
import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.helpers.Boundary;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class EnemySpawnSystem extends EntitySystem {

	private Engine engine;
	private Boundary bounds;

	private float blueTimer = 0f;
	private float blueSpawnTime = 3f;
	
	private float greenTimer = 0f;
	private float greenSpawnTime = 4f;
	
	private float purpleTimer = 0f;
	private float purpleSpawnTime = 4f;

	public EnemySpawnSystem(Boundary bounds) {
		this.bounds = bounds;
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		blueTimer = blueSpawnTime;
		greenTimer = greenSpawnTime; 
	}

	@Override
	public void update(float deltaTime) {
		blueTimer -= deltaTime;
		if (blueTimer <= 0f) {
			blueSpawnTime *= 0.98f;
			blueTimer = blueSpawnTime;
			spawnEnemy();
			spawnEnemy();
			spawnEnemy();
		}
		
		greenTimer -= deltaTime;
		if (greenTimer <= 0f) {
			greenSpawnTime *= 0.98f;
			greenTimer = greenSpawnTime;
			spawnGreenWave(MathUtils.floor(MathUtils.random() * 4f));
		}
		
		purpleTimer -= deltaTime;
		if (purpleTimer <= 0f) {
			purpleSpawnTime *= 0.98f;
			purpleTimer = purpleSpawnTime;
			spawnPurpleWave();
		}
	}

	private void spawnEnemy() {
		float s = 36f;

		Entity e = new Entity();

		int edge = MathUtils.floor((float) (Math.random() * 4f));
		float x = 0;
		float y = 0;
		if (edge == 0) {
			x = -10f - s;
			y = bounds.getBottom() + s + (float) Math.random() * (bounds.getHeight() - 2 * s);
		} else if (edge == 1) {
			x = 1280f + 10f + s;
			y = bounds.getBottom() + s + (float) Math.random() * (bounds.getHeight() - 2 * s);
		} else if (edge == 2) {
			x = bounds.getLeft() + s + (float) Math.random() * (bounds.getWidth() - 2 * s);
			y = -10f - s;
		} else { 
			x = bounds.getLeft() + s + (float) Math.random() * (bounds.getWidth() - 2 * s);
			y = 720f + 10f + s;
		}
		
		PositionComponent epc = new PositionComponent(x, y, 0f);
		e.add(epc);

		e.add(new VelocityComponent(200f));
		e.add(new TextureComponent(new TextureRegion(Assets.enemy)));
		e.add(new SizeComponent(s));
		e.add(new AIMovementComponent());

		engine.addEntity(e);
	}
	
	private void spawnGreenWave(int dir) {
		float size = 36f;
		float speed = 130f;
		int n = 0;
		float x, y, dx, dy, vx, vy;
		
		if (dir == 0) {
			n = 10;
			x = bounds.getLeft() + bounds.getWidth() / (n + 1);
			y = 720f + size + 10f;
			dx = bounds.getWidth() / (n + 1);
			dy = 0;
			vx = 0;
			vy = -speed;
		} else if (dir == 1){
			n = 5; 
			x = 1280f + size + 10f;
			y = bounds.getBottom() + bounds.getHeight() / (n + 1);
			dx = 0;
			dy = bounds.getHeight() / (n + 1);
			vx = -speed;
			vy = 0;
		} else if (dir == 2) {
			n = 10;
			x = bounds.getLeft() + bounds.getWidth() / (n + 1);
			y = - size - 10f;
			dx = bounds.getWidth() / (n + 1);
			dy = 0;
			vx = 0;
			vy = speed;
		} else if (dir == 3) {
			n = 5;
			x = - size - 10f;
			y = bounds.getBottom() + bounds.getHeight() / (n + 1);
			dx = 0;
			dy = bounds.getHeight() / (n + 1);
			vx = speed;
			vy = 0;
		} else {
			return;
		}
		
		for (int i = 0; i < n; i++) {
			Entity e = new Entity();
			e.add(new PositionComponent(x + i * dx, y + i * dy, 0));
			e.add(new VelocityComponent(vx, vy, speed));
			e.add(new SizeComponent(size));
			e.add(new TextureComponent(new TextureRegion(Assets.enemyGreen)));
			
			engine.addEntity(e);
		}
	}
	
	private void spawnPurpleWave() {
		float size = 36f;
		float speed = 130f;
		
		// From somewhere near the lower left corner
		spawnPurple(-30 - size + MathUtils.random(-30f, 30f),
				-30 - size + MathUtils.random(-30f, 30f),
				speed, speed);
		// Lower right
		spawnPurple(1280 + 30 + size + MathUtils.random(-30f, 30f),
				-30 - size + MathUtils.random(-30f, 30f),
				-speed, speed);
		// Upper left
		spawnPurple(-30 - size + MathUtils.random(-30f, 30f),
				720 + 30 + size + MathUtils.random(-30f, 30f),
				speed, speed);
		// Upper right
		spawnPurple(1280 + 30 + size + MathUtils.random(-30f, 30f),
				720 + 30 + size + MathUtils.random(-30f, 30f),
				-speed, -speed);
		

	}
	
	// Need to decide what to do about entity generation like this.
	// Maybe some entity factories and data classes with the fixed data for each enemy?
	private void spawnPurple(float x, float y, float vx, float vy) {
		Entity e = new Entity();
		e.add(new PositionComponent(x, y, 0));
		e.add(new VelocityComponent(vx, vy, 250f));
		e.add(new SizeComponent(36f));
		e.add(new TextureComponent(new TextureRegion(Assets.enemyPurple)));
		e.add(new AIMovementBouncyComponent());
		engine.addEntity(e);
	}

}
