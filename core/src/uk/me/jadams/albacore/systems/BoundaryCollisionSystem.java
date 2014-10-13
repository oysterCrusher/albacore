package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementBouncyComponent;
import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundary;
import uk.me.jadams.albacore.helpers.ParticleExplosions;
import uk.me.jadams.albacore.helpers.ParticleExplosions.ExplosionType;
import uk.me.jadams.albacore.helpers.ParticleExplosions.SprayType;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

public class BoundaryCollisionSystem extends EntitySystem {

	private Boundary boundary;
	private Engine engine;
	private ParticleExplosions particleExplosions;

	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<VelocityComponent> vm;
	private ComponentMapper<SizeComponent> sm;

	private ImmutableArray<Entity> enemies;
	private ImmutableArray<Entity> enemiesBounce;
	private ImmutableArray<Entity> players;
	private ImmutableArray<Entity> bullets;

	public BoundaryCollisionSystem(Boundary boundary, ParticleExplosions particleExplosions) {
		this.boundary = boundary;
		this.particleExplosions = particleExplosions;

		pm = ComponentMapper.getFor(PositionComponent.class);
		vm = ComponentMapper.getFor(VelocityComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		// Get all the enemies that ignore the boundaries
		enemies = engine.getEntitiesFor(Family.getFor(
				ComponentType.getBitsFor(PositionComponent.class, SizeComponent.class),
				ComponentType.getBitsFor(),
				ComponentType.getBitsFor(PlayerInputComponent.class, BulletComponent.class)));

		// Enemies that bounce off the boundaries
		enemiesBounce = engine.getEntitiesFor(Family.getFor(
				ComponentType.getBitsFor(PositionComponent.class, SizeComponent.class,
						AIMovementBouncyComponent.class),
						ComponentType.getBitsFor(),
						ComponentType.getBitsFor(PlayerInputComponent.class, BulletComponent.class)));

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

		// If the player is about to hit the boundary, limit the velocity to keep
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

			p0x = position.x;
			p1x = position.x + velocity.x * deltaTime;
			p0y = position.y;
			p1y = position.y + velocity.y * deltaTime;

			// Right boundary
			if (position.y < boundary.getTop() && position.y > boundary.getBottom()) {
				if (p0x > boundary.getRight() && p1x <= boundary.getRight()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							boundary.getRight(), position.y);
				} else if (p0x <= boundary.getRight() && p1x > boundary.getRight()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							boundary.getRight(), position.y);
				}
			}

			// Left boundary
			if (position.y < boundary.getTop() && position.y > boundary.getBottom()) {
				if (p0x > boundary.getLeft() && p1x <= boundary.getLeft()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							boundary.getLeft(), position.y);
				} else if (p0x <= boundary.getLeft() && p1x > boundary.getLeft()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							boundary.getLeft(), position.y);
				}
			}

			// Top boundary
			if (position.x < boundary.getRight() && position.x > boundary.getLeft()) {
				if (p0y > boundary.getTop() && p1y <= boundary.getTop()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							position.x, boundary.getTop());
				} else if (p0y <= boundary.getTop() && p1y > boundary.getTop()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							position.x, boundary.getTop());
				}
			}

			// Bottom boundary
			if (position.x < boundary.getRight() && position.x > boundary.getLeft()) {
				if (p0y > boundary.getBottom() && p1y <= boundary.getBottom()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							position.x, boundary.getBottom());
				} else if (p0y <= boundary.getBottom() && p1y > boundary.getBottom()) {
					particleExplosions.start(ExplosionType.WHITE_SQUARE,
							position.x, boundary.getBottom());
				}
			}

		}

		// Bounce the enemies with AIMovementBounceComponent off the boundaries if they try to leave
		// This routine reflects the velocities but not the positions, i.e. isn't physically
		// accurate reflection. To achieve that, this routine should be incorporated in the
		// movementSystem or as a post-movement update of position and velocity.
		for (int i = 0; i < enemiesBounce.size(); i++) {
			Entity e = enemiesBounce.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			size = sm.get(e);
			radius = size.size * 0.5f;

			if (velocity.x < 0) {
				if (position.x - radius + velocity.x * deltaTime <= boundary.getLeft()) {
					velocity.x = -velocity.x;
				}
			} else if (velocity.x > 0) {
				if (position.x + radius + velocity.x * deltaTime >= boundary.getRight()) {
					velocity.x = -velocity.x;
				}
			}

			if (velocity.y < 0) {
				if (position.y - radius + velocity.y * deltaTime <= boundary.getBottom()) {
					velocity.y = -velocity.y;
				}
			} else if (velocity.y > 0) {
				if (position.y + radius + velocity.y * deltaTime >= boundary.getTop()) {
					velocity.y = -velocity.y;
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
			float angle = new Vector2(velocity.x, velocity.y).angle();
			float angleRange = 30f;
			

			if (velocity.x < 0) {
				if (position.x - radius + velocity.x * deltaTime <= boundary.getLeft()) {
					engine.removeEntity(b);
					particleExplosions.start(SprayType.WHITE_SQUARE,
							boundary.getLeft(), position.y,
							angle - angleRange, angle + angleRange);
				}
			} else if (velocity.x > 0) {
				if (position.x + radius + velocity.x * deltaTime >= boundary.getRight()) {
					engine.removeEntity(b);
					particleExplosions.start(SprayType.WHITE_SQUARE,
							boundary.getRight(), position.y,
							angle - angleRange, angle + angleRange);
				}
			}

			if (velocity.y < 0) {
				if (position.y - radius + velocity.y * deltaTime <= boundary.getBottom()) {
					engine.removeEntity(b);
					particleExplosions.start(SprayType.WHITE_SQUARE,
							position.x, boundary.getBottom(),
							angle - angleRange, angle + angleRange);
				}
			} else if (velocity.y > 0) {
				if (position.y + radius + velocity.y * deltaTime >= boundary.getTop()) {
					engine.removeEntity(b);
					particleExplosions.start(SprayType.WHITE_SQUARE,
							position.x, boundary.getTop(),
							angle - angleRange, angle + angleRange);
				}
			}			
		}
	}

}
