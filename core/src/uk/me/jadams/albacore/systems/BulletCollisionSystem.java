package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.AnimationComponent;
import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.LifetimeComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Assets;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class BulletCollisionSystem extends EntitySystem {
	
	private Engine engine;
	
	private ImmutableArray<Entity> bullets;
	private ImmutableArray<Entity> enemies;
	
	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<SizeComponent> sm; 
	private ComponentMapper<VelocityComponent> vm;
	
	public BulletCollisionSystem() {
		pm = ComponentMapper.getFor(PositionComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
		vm = ComponentMapper.getFor(VelocityComponent.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		bullets = engine.getEntitiesFor(Family.getFor(BulletComponent.class));
		enemies = engine.getEntitiesFor(Family.getFor(AIMovementComponent.class));
	}
	
	@Override
	public void update(float deltaTime) {
		Entity e;
		Entity b;
		PositionComponent ep;
		PositionComponent bp;
		SizeComponent es;
		SizeComponent bs;
		VelocityComponent ev;
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
					ev = vm.get(e);
					engine.removeEntity(e);
					engine.removeEntity(b);
					Entity explosion = new Entity();
					explosion.add(new PositionComponent(ep.x, ep.y, ep.angle));
					explosion.add(new VelocityComponent(ev.x, ev.y, 250f));
					explosion.add(new AnimationComponent(Assets.enemyExplodeAnim));
					explosion.add(new TextureComponent());
					explosion.add(new SizeComponent(64f));
					explosion.add(new LifetimeComponent(1.5f));
					engine.addEntity(explosion);
					break;
				}
			}
		}
	}
}
