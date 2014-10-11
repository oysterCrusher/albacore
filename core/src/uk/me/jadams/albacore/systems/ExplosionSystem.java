package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.ExplodesComponent;
import uk.me.jadams.albacore.components.HealthComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.helpers.ParticleExplosions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ExplosionSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<ExplodesComponent> em;
	private ComponentMapper<HealthComponent> hm;
	
	private ParticleExplosions particleExplosions;
	
	@SuppressWarnings("unchecked")
	public ExplosionSystem(ParticleExplosions particleExplosions) {
		super(Family.getFor(PositionComponent.class, ExplodesComponent.class, HealthComponent.class));
		this.particleExplosions = particleExplosions;
		
		pm = ComponentMapper.getFor(PositionComponent.class);
		em = ComponentMapper.getFor(ExplodesComponent.class);
		hm = ComponentMapper.getFor(HealthComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = pm.get(entity);
		ExplodesComponent explodes = em.get(entity);
		HealthComponent health = hm.get(entity);
		
		if (health.hp <= 0) {
			particleExplosions.start(explodes.type, position.x, position.y);
		}
	}

}
