package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class BoundaryCollisionSystem extends EntitySystem {

	private Boundaries boundary;
	private Engine engine;

	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<VelocityComponent> vm;
	private ComponentMapper<SizeComponent> sm;

	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> bullets;

	public BoundaryCollisionSystem(Boundaries boundary) {
		this.boundary = boundary;

		pm = ComponentMapper.getFor(PositionComponent.class);
		vm = ComponentMapper.getFor(VelocityComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		players = engine.getEntitiesFor(Family.getFor(PlayerInputComponent.class,
				PositionComponent.class, VelocityComponent.class, SizeComponent.class));
		bullets =  engine.getEntitiesFor(Family.getFor(BulletComponent.class,
				PositionComponent.class, VelocityComponent.class, SizeComponent.class));
	}

	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		VelocityComponent velocity;
		SizeComponent size;
		float radius;

		// If a player is about to hit the boundary, limit the velocity to keep
		// them inside the bounds.
		for (int i = 0; i < players.size(); i++) {
			Entity p = players.get(i);
			position = pm.get(p);
			velocity = vm.get(p);
			size = sm.get(p);
			radius = size.size * 0.5f;

			if (velocity.x < 0) {
				if (position.x - radius + velocity.x * deltaTime <= boundary.getLeft()) {
					velocity.x = (boundary.getLeft() - position.x + radius) / deltaTime;
				}
			} else if (velocity.x > 0) {
				if (position.x + radius + velocity.x * deltaTime >= boundary.getRight()) {
					velocity.x = (boundary.getRight() - position.x - radius) / deltaTime;
				}
			}
			
			if (velocity.y < 0) {
				if (position.y - radius + velocity.y * deltaTime <= boundary.getBottom()) {
					velocity.y = (boundary.getBottom() - position.y + radius) / deltaTime;
				}
			} else if (velocity.y > 0) {
				if (position.y + radius + velocity.y * deltaTime >= boundary.getTop()) {
					velocity.y = (boundary.getTop() - position.y - radius) / deltaTime;
				}
			}
		}

		// For bullets, remove them if they collide with the boundary.
		for (int i = 0; i < bullets.size(); i++) {
			Entity b = bullets.get(i);
			position = pm.get(b);
			velocity = vm.get(b);
			size = sm.get(b);
			radius = size.size * 0.5f;

			if (velocity.x < 0) {
				if (position.x - radius + velocity.x * deltaTime <= boundary.getLeft()) {
					engine.removeEntity(b);
				}
			} else if (velocity.x > 0) {
				if (position.x + radius + velocity.x * deltaTime >= boundary.getRight()) {
					engine.removeEntity(b);
				}
			}
			
			if (velocity.y < 0) {
				if (position.y - radius + velocity.y * deltaTime <= boundary.getBottom()) {
					engine.removeEntity(b);
				}
			} else if (velocity.y > 0) {
				if (position.y + radius + velocity.y * deltaTime >= boundary.getTop()) {
					engine.removeEntity(b);
				}
			}			
		}
	}

}
