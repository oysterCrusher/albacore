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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerInputSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private OrthographicCamera camera;
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	public PlayerInputSystem(OrthographicCamera camera, Input input) {
		this.camera = camera;
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
		Vector3 mouseCoords = new Vector3();
		Vector2 direction = new Vector2();
		Vector2 move = new Vector2();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			position = pm.get(e);
			velocity = vm.get(e);
			
			// Unproject mouse position to get angle between player position and mouse
			mouseCoords.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(mouseCoords);
			mouseCoords.sub(position.x, position.y, 0);
			mouseCoords.nor();
			
			// Set position.angle to face the mouse 
			direction.x = mouseCoords.x;
			direction.y = mouseCoords.y;
			position.angle = direction.angle();
			
			// Add velocity in the direction of the wasd keys
			move.set(0, 0);
			if (Gdx.input.isKeyPressed(Keys.A)) {
				move.x -= 150;
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				move.x += 150;
			}
			if (Gdx.input.isKeyPressed(Keys.W)) {
				move.y += 150;
			}
			if (Gdx.input.isKeyPressed(Keys.S)) {
				move.y -= 150;
			}
			if (move.len() != 0) {
				move.nor();
				velocity.x += move.x * 150;
				velocity.y += move.y * 150;
			}
		}
	}

}
