package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.VelocityComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MovementSystem extends IteratingSystem {
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Family.getFor(PositionComponent.class, VelocityComponent.class));
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = pm.get(entity);
		VelocityComponent velocity = vm.get(entity);
		
		// Cap the velocities. Velocity component probably needs a max velocity value.
		Vector2 v = new Vector2(velocity.x, velocity.y);
		v.clamp(0, velocity.max);
		velocity.x = v.x;
		velocity.y = v.y;
		
		position.x += v.x * deltaTime;
		position.y += v.y * deltaTime;
		
		// Cap the angular velocity.
		velocity.omega = MathUtils.clamp(velocity.omega, -velocity.omegaMax, velocity.omegaMax);
		position.angle += velocity.omega * deltaTime;
		if (position.angle > 360f) {
			position.angle -= 360f;
		} else if (position.angle < 0f) {
			position.angle += 360f;
		}
	}
	
}
