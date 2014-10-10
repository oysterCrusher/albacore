package uk.me.jadams.albacore.systems;

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

	private float timer = 0f;
	private float spawnTime = 2f;

	public EnemySpawnSystem(Boundary bounds) {
		this.bounds = bounds;
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		timer = spawnTime;
	}

	@Override
	public void update(float deltaTime) {
		timer -= deltaTime;

		if (timer <= 0f) {
			spawnTime *= 0.98f;
			timer = spawnTime;
			spawnEnemy();
			spawnEnemy();
			spawnEnemy();
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

}
