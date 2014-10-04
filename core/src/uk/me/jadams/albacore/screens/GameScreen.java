package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;
import uk.me.jadams.albacore.helpers.Input;
import uk.me.jadams.albacore.systems.BoundaryCollisionSystem;
import uk.me.jadams.albacore.systems.MovementSystem;
import uk.me.jadams.albacore.systems.PlayerInputSystem;
import uk.me.jadams.albacore.systems.RenderSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
	Input input;
	Engine engine;
	Boundaries gameBoundary;

	@Override
	public void render(float delta) {
		gameBoundary.render();
		engine.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		input = new Input();
		Gdx.input.setInputProcessor(input);
		
		Texture playerTexture = new Texture(Gdx.files.internal("player.png"));
		
		OrthographicCamera camera = new OrthographicCamera(1280, 720);
		camera.position.set(1280f * 0.5f, 720f * 0.5f, 0f);

		gameBoundary = new Boundaries(camera);
		
		engine = new Engine();
		
		Entity player = new Entity();
		player.add(new PositionComponent());
		player.add(new VelocityComponent());
		player.add(new TextureComponent(new TextureRegion(playerTexture)));
		player.add(new PlayerInputComponent());
		player.add(new SizeComponent(32f));
		engine.addEntity(player);
		
		PlayerInputSystem playerInputSystem = new PlayerInputSystem(camera, input);
		engine.addSystem(playerInputSystem);
		
		BoundaryCollisionSystem boundaryCollisionSystem = new BoundaryCollisionSystem(gameBoundary);
		engine.addSystem(boundaryCollisionSystem);
		
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);
		
		RenderSystem renderSystem = new RenderSystem(camera);
		engine.addSystem(renderSystem);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
