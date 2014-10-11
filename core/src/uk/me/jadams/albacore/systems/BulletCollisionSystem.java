package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.helpers.Particles;
import uk.me.jadams.albacore.helpers.Scoring;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class BulletCollisionSystem extends EntitySystem {

	private Engine engine;
	private Particles particleEffect;
	private Scoring score;

	private ImmutableArray<Entity> bullets;
	private ImmutableArray<Entity> enemies;

	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<SizeComponent> sm; 

	public BulletCollisionSystem(Particles largeBlue, Scoring score) {
		this.particleEffect = largeBlue;
		this.score = score;
		pm = ComponentMapper.getFor(PositionComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		bullets = engine.getEntitiesFor(Family.getFor(BulletComponent.class));
		enemies = engine.getEntitiesFor(Family.getFor(
				ComponentType.getBitsFor(PositionComponent.class, SizeComponent.class),
				ComponentType.getBitsFor(),
				ComponentType.getBitsFor(PlayerInputComponent.class, BulletComponent.class)));
	}

	@Override
	public void update(float deltaTime) {
		Entity e;
		Entity b;
		PositionComponent ep;
		PositionComponent bp;
		SizeComponent es;
		SizeComponent bs;
		float distsq;

		for (int i = 0; i < enemies.size(); i++) {
			e = enemies.get(i);
			ep = pm.get(e);
			es = sm.get(e);
			for (int j = 0; j < bullets.size(); j++) {
				b = bullets.get(j);
				bp = pm.get(b);
				bs = sm.get(b);

				distsq = (ep.x - bp.x) * (ep.x - bp.x) + (ep.y - bp.y) * (ep.y - bp.y);

				if (distsq < (es.size + bs.size) * (es.size + bs.size) / 4f) {
					engine.removeEntity(e);
					engine.removeEntity(b);

					particleEffect.start(ep.x, ep.y);
					
					score.add(50);

					break;
				}
			}
		}
	}
}
