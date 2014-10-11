package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class RemoveOutOfRangeSystem extends IteratingSystem {

	private float removeDist = 500f;
	
	private Engine engine;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
	@SuppressWarnings("unchecked")
	public RemoveOutOfRangeSystem(Engine engine) {
		super(Family.getFor(PositionComponent.class));
		this.engine = engine;
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent p = pm.get(entity);
		
		if (p.x < -removeDist || p.x > 1280 + removeDist
				|| p.y < - removeDist || p.y > 720 + removeDist) {
			engine.removeEntity(entity);
		}
	}

}
