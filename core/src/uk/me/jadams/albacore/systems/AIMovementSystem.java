package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AIMovementBackstabComponent;
import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.VelocityComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

public class AIMovementSystem extends EntitySystem {

	private ImmutableArray<Entity> enemiesFollow;
	private ImmutableArray<Entity> enemiesBackstab;
	private Entity player;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	
	public AIMovementSystem(Entity player) {
		this.player = player;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine) {
		enemiesFollow = engine.getEntitiesFor(Family.getFor(PositionComponent.class, VelocityComponent.class,
				AIMovementComponent.class));
		enemiesBackstab = engine.getEntitiesFor(Family.getFor(PositionComponent.class, VelocityComponent.class,
				AIMovementBackstabComponent.class));
		super.addedToEngine(engine);
	}
	
	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		VelocityComponent velocity;
		PositionComponent playerPosition = pm.get(player);;
		Vector2 direction = new Vector2();
		float relativeAngle = 0f;
		
		for (int i = 0; i < enemiesFollow.size(); i++) {
			Entity e = enemiesFollow.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
	
			// Simply accelerates the enemy towards the player 
			direction.set(playerPosition.x, playerPosition.y);
			direction.sub(position.x, position.y);
			if (direction.len() > 1f) {
				direction.nor();
			}
			velocity.x += direction.x * 10f;
			velocity.y += direction.y * 10f;
			position.angle = direction.angle();
		}
		
		// Having these two for-loops feels like I should have two separate systems.
		for (int i = 0; i < enemiesBackstab.size(); i++) {
			Entity e = enemiesBackstab.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			
			// If the player is looking, move very slowly. Otherwise move towards player
			direction.set(playerPosition.x, playerPosition.y);
			direction.sub(position.x, position.y);
			direction.nor();
			relativeAngle = Math.abs(playerPosition.angle - direction.angle()); 
			if (relativeAngle > 90f && relativeAngle < 270f) {
//				velocity.x = 0;
//				velocity.y = 0;
				velocity.x = direction.x * 25f;
				velocity.y = direction.y * 25f;
			} else {
				velocity.x = direction.x * 1000f;
				velocity.y = direction.y * 1000f;
			}
		}
		
	}
	
}
