package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.LifetimeComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class LifetimeSystem extends IteratingSystem {
	
	private Engine engine;
	
	private ComponentMapper<LifetimeComponent> lm;

	@SuppressWarnings("unchecked")
	public LifetimeSystem() {
		super(Family.getFor(LifetimeComponent.class));
		lm = ComponentMapper.getFor(LifetimeComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		super.addedToEngine(engine);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		LifetimeComponent lifetime = lm.get(entity);
		lifetime.timeLeft -= deltaTime;
		if (lifetime.timeLeft <= 0f) {
			engine.removeEntity(entity);
		}
	}

}
