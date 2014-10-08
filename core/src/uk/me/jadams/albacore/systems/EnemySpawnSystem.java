package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemySpawnSystem extends EntitySystem {

	private Engine engine;
	private Boundaries bounds;
	private Texture enemyTexture;

	private float timer = 0f;
	private float spawnTime = 3f;

	public EnemySpawnSystem(Boundaries bounds, Texture enemyTexture) {
		this.bounds = bounds;
		this.enemyTexture = enemyTexture;
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
		float s = 24f;

		Entity e = new Entity();

		PositionComponent epc = new PositionComponent(
				bounds.getLeft() + s + (float) Math.random() * (bounds.getWidth() - 2 * s),
				bounds.getBottom() + s + (float) Math.random() * (bounds.getHeight() - 2 * s),
				0f);
		e.add(epc);

		e.add(new VelocityComponent());
		e.add(new TextureComponent(new TextureRegion(enemyTexture)));
		e.add(new SizeComponent(s));
		e.add(new AIMovementComponent());

		engine.addEntity(e);
	}

}
