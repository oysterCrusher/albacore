package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class BoundaryCollisionSystem extends IteratingSystem {
	
	private Boundaries boundary;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

	@SuppressWarnings("unchecked")
	public BoundaryCollisionSystem(Boundaries boundary) {
		super(Family.getFor(PositionComponent.class, VelocityComponent.class));
		this.boundary = boundary;
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = pm.get(entity);
		VelocityComponent velocity = vm.get(entity);
		SizeComponent size = sm.get(entity);
		float radius = size.size * 0.5f;

		if (velocity.x < 0) {
			if (position.x - radius + velocity.x * deltaTime <= boundary.getLeft()) {
				velocity.x = (boundary.getLeft() - position.x + radius) / deltaTime;
			}
		}
		
		if (velocity.x > 0) {
			if (position.x + radius + velocity.x * deltaTime >= boundary.getRight()) {
				velocity.x = (boundary.getRight() - position.x - radius) / deltaTime;
			}
		}
		
		if (velocity.y < 0) {
			if (position.y - radius + velocity.y * deltaTime <= boundary.getBottom()) {
				velocity.y = (boundary.getBottom() - position.y + radius) / deltaTime;
			}
		}
		
		if (velocity.y > 0) {
			if (position.y + radius + velocity.y * deltaTime >= boundary.getTop()) {
				velocity.y = (boundary.getTop() - position.y - radius) / deltaTime;
			}
		}		
	}

}
