package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.VelocityComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

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
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		
		// Reset the velocity back to zero?
		velocity.x = 0;
		velocity.y = 0;
	}
	
}
