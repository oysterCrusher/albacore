package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class PlayerInputSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private OrthographicCamera camera;
	private Input input;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	public PlayerInputSystem(OrthographicCamera camera, Input input) {
		this.camera = camera;
		this.input = input;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(PlayerInputComponent.class,
				PositionComponent.class, VelocityComponent.class));
		super.addedToEngine(engine);
	}
	
	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		VelocityComponent velocity;
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			
			// Unproject mouse position to get angle between player position and mouse
			Vector3 mouseCoords = new Vector3(input.getMouseX(), input.getMouseY(), 0);
			camera.unproject(mouseCoords);
			mouseCoords.sub(position.x, position.y, 0);
			mouseCoords.nor();
			
			// Player wants to move in this direction
			velocity.x += mouseCoords.x * 60f;
			velocity.y += mouseCoords.y * 60f;
		}
	}

}
