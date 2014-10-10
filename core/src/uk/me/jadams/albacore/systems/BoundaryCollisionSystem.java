package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;
import uk.me.jadams.albacore.helpers.Particles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class BoundaryCollisionSystem extends EntitySystem {

	private Boundaries boundary;
	private Engine engine;
	private Particles effects;

	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<VelocityComponent> vm;
	private ComponentMapper<SizeComponent> sm;

	private ImmutableArray<Entity> enemies;
	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> bullets;

	public BoundaryCollisionSystem(Boundaries boundary, Particles effects) {
		this.boundary = boundary;
		this.effects = effects;

		pm = ComponentMapper.getFor(PositionComponent.class);
		vm = ComponentMapper.getFor(VelocityComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		// Get all the enemies
		enemies = engine.getEntitiesFor(Family.getFor(PositionComponent.class,
				VelocityComponent.class, SizeComponent.class, AIMovementComponent.class));
		players = engine.getEntitiesFor(Family.getFor(PositionComponent.class,
				VelocityComponent.class, SizeComponent.class, PlayerInputComponent.class));
		bullets =  engine.getEntitiesFor(Family.getFor(BulletComponent.class,
				PositionComponent.class, VelocityComponent.class, SizeComponent.class));
	}

	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		VelocityComponent velocity;
		SizeComponent size;
		float radius;

		// If a player or enemy is about to hit the boundary, limit the velocity to keep
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

		// As enemies pass through the boundary, fire off some particles
		// There must be a nicer way of doing this.
		for (int i = 0; i < enemies.size(); i++) {
			Entity e = enemies.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			size = sm.get(e);
			radius = size.size * 0.5f;
			float p0x;
			float p1x;
			float p0y;
			float p1y;

			p0x = position.x - radius;
			p1x = position.x - radius + velocity.x * deltaTime;
			p0y = position.y - radius;
			p1y = position.y - radius + velocity.y * deltaTime;

			// Right boundary
			if (position.y < boundary.getTop() && position.y > boundary.getBottom()) {
				if (p0x > boundary.getRight() && p1x <= boundary.getRight()) {
					effects.start(boundary.getRight(), position.y);
				} else if (p0x <= boundary.getRight() && p1x > boundary.getRight()) {
					effects.start(boundary.getRight(), position.y);
				}
			}

			// Left boundary
			if (position.y < boundary.getTop() && position.y > boundary.getBottom()) {
				if (p0x > boundary.getLeft() && p1x <= boundary.getLeft()) {
					effects.start(boundary.getLeft(), position.y);
				} else if (p0x <= boundary.getLeft() && p1x > boundary.getLeft()) {
					effects.start(boundary.getLeft(), position.y);
				}
			}
			
			// Top boundary
			if (position.x < boundary.getRight() && position.x > boundary.getLeft()) {
				if (p0y > boundary.getTop() && p1y <= boundary.getTop()) {
					effects.start(position.x, boundary.getTop());
				} else if (p0y <= boundary.getTop() && p1y > boundary.getTop()) {
					effects.start(position.x, boundary.getTop());
				}
			}
			
			// Bottom boundary
			if (position.x < boundary.getRight() && position.x > boundary.getLeft()) {
				if (p0y > boundary.getBottom() && p1y <= boundary.getBottom()) {
					effects.start(position.x, boundary.getBottom());
				} else if (p0y <= boundary.getBottom() && p1y > boundary.getBottom()) {
					effects.start(position.x, boundary.getBottom());
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
