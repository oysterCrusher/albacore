package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.HealthComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class CorpseRemovalSystem extends IteratingSystem {

	private Engine engine;
	
	private ComponentMapper<HealthComponent> hm;
	
	@SuppressWarnings("unchecked")
	public CorpseRemovalSystem(Engine engine) {
		super(Family.getFor(HealthComponent.class));
		this.engine = engine;
		hm = ComponentMapper.getFor(HealthComponent.class);

	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HealthComponent eh = hm.get(entity);
		
		// If it's dead, remove the corpse
		if (eh.hp <= 0) {
			engine.removeEntity(entity);
		}
	}

}
