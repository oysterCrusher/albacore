package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.screens.GameScreen;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

// This should probably be made an EntitySystem rather than an EteratingSystem
public class PlayerEnemyCollisionSystem extends IteratingSystem {
	
	private GameScreen game;
	private Entity player;
	
	private ComponentMapper<PositionComponent> pm;
	private ComponentMapper<SizeComponent> sm;
	
	@SuppressWarnings("unchecked")
	public PlayerEnemyCollisionSystem(GameScreen game, Entity player) {
		super(Family.getFor(PositionComponent.class, SizeComponent.class, AIMovementComponent.class));
		pm = ComponentMapper.getFor(PositionComponent.class);
		sm = ComponentMapper.getFor(SizeComponent.class);
		this.player = player;
		this.game = game;
	}
	 
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent ep = pm.get(entity);
		SizeComponent es = sm.get(entity);
		
		PositionComponent pp = pm.get(player);
		SizeComponent ps = sm.get(player);
		
		float separationSq = (ep.x - pp.x) * (ep.x - pp.x) + (ep.y - pp.y) * (ep.y - pp.y);
		float sizesSq = (es.size + ps.size) * (es.size + ps.size) / 4f;
		
		if (separationSq < sizesSq) {
			game.isRunning = false;
		}
		
		
	}

}
