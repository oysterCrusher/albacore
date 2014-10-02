package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class MovementSystem extends IteratingSystem {
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Family.getFor(PositionComponent.class));
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = pm.get(entity);
		position.x += 20f * deltaTime;
	}
	
}
