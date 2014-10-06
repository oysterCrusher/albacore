package uk.me.jadams.albacore.systems;

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

	private ImmutableArray<Entity> entities;
	private Entity player;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
//	private ComponentMapper<AIMovementComponent> aimm = ComponentMapper.getFor(AIMovementComponent.class);
	
	public AIMovementSystem(Entity player) {
		this.player = player;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class, VelocityComponent.class,
				AIMovementComponent.class));
		super.addedToEngine(engine);
	}
	
	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		VelocityComponent velocity;
		PositionComponent playerPosition;
		Vector2 direction = new Vector2();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			playerPosition = pm.get(player);
			
			direction.set(playerPosition.x, playerPosition.y);
			direction.sub(position.x, position.y);
			if (direction.len() > 1f) {
				direction.nor();
			}
//			System.out.println(direction.x + ", " + direction.y);
//			System.out.println("v = " + velocity.x + ", " + velocity.y);
			velocity.x += direction.x * 10;
			velocity.y += direction.y * 10;
//			System.out.println("v = " + velocity.x + ", " + velocity.y);
		}
		
	}
	
}
