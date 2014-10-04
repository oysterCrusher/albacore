package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.helpers.Boundaries;
import uk.me.jadams.albacore.helpers.Cursor;
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
	SpriteBatch batch;
	
	Input input;
	Engine engine;
	OrthographicCamera camera;
	Boundaries gameBoundary;
	Cursor cursor;

	@Override
	public void render(float delta) {
		gameBoundary.render();
		cursor.udpate();
		engine.update(delta);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		cursor.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Texture playerTexture = new Texture(Gdx.files.internal("player.png"));
		Texture cursorTexture = new Texture(Gdx.files.internal("cursor.png"));
		
		batch = new SpriteBatch();
		
		cursor = new Cursor(cursorTexture);
		Gdx.input.setCursorCatched(true);
		input = new Input();
		Gdx.input.setInputProcessor(input);
		
		camera = new OrthographicCamera(1280, 720);
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
		
		PlayerInputSystem playerInputSystem = new PlayerInputSystem(camera, cursor);
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
