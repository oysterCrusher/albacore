package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Input;
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

	@Override
	public void render(float delta) {
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
		
		OrthographicCamera camera = new OrthographicCamera(800, 600);
//		camera.position.set(400, 300, 0);
		
		engine = new Engine();
		
		Entity player = new Entity();
		player.add(new PositionComponent());
		player.add(new VelocityComponent());
		player.add(new TextureComponent(new TextureRegion(playerTexture)));
		player.add(new PlayerInputComponent());
		engine.addEntity(player);
		
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);
		
		PlayerInputSystem playerInputSystem = new PlayerInputSystem(camera, input);
		engine.addSystem(playerInputSystem);
		
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
