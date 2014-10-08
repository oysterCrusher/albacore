package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.AIMovementComponent;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.components.WeaponComponent;
import uk.me.jadams.albacore.helpers.Boundaries;
import uk.me.jadams.albacore.helpers.Cursor;
import uk.me.jadams.albacore.helpers.Input;
import uk.me.jadams.albacore.systems.AIMovementSystem;
import uk.me.jadams.albacore.systems.BoundaryCollisionSystem;
import uk.me.jadams.albacore.systems.BulletCollisionSystem;
import uk.me.jadams.albacore.systems.EnemySpawnSystem;
import uk.me.jadams.albacore.systems.MovementSystem;
import uk.me.jadams.albacore.systems.PlayerInputSystem;
import uk.me.jadams.albacore.systems.RenderSystem;
import uk.me.jadams.albacore.systems.ShootingSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
	// FPS logger for debug
	FPSLogger fpsLogger;
	
	SpriteBatch batch;
	
	Input input;
	Engine engine;
	OrthographicCamera camera;
	Boundaries gameBoundary;
	Cursor cursor;

	@Override
	public void render(float delta) {
		fpsLogger.log();
		gameBoundary.render();
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
		// FPS logger for debug
		fpsLogger = new FPSLogger();
		
		Texture playerTexture = new Texture(Gdx.files.internal("player.png"));
		Texture enemyTexture = new Texture(Gdx.files.internal("enemy.png"));
		Texture cursorTexture = new Texture(Gdx.files.internal("cursor.png"));
		
		playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch = new SpriteBatch();
		
		cursor = new Cursor(cursorTexture);
		Gdx.input.setCursorCatched(true);
		input = new Input(cursor);
//		Gdx.input.setCursorImage(pixmap, xHotspot, yHotspot);
		Gdx.input.setInputProcessor(input);
		
		camera = new OrthographicCamera(1280, 720);
		camera.position.set(1280f * 0.5f, 720f * 0.5f, 0f);

		gameBoundary = new Boundaries(camera);
		
		engine = new Engine();
		
		// Create the player entity
		Entity player = new Entity();
		player.add(new PositionComponent());
		player.add(new VelocityComponent());
		player.add(new TextureComponent(new TextureRegion(playerTexture)));
		player.add(new PlayerInputComponent());
		player.add(new SizeComponent(32f));
		player.add(new WeaponComponent());
		engine.addEntity(player);
		
		// Create a couple of enemy entities
		Entity enemy = new Entity();
		enemy.add(new PositionComponent(40, 680, 0));
		enemy.add(new VelocityComponent());
		enemy.add(new TextureComponent(new TextureRegion(enemyTexture)));
		enemy.add(new SizeComponent(24f));
		enemy.add(new AIMovementComponent());
		engine.addEntity(enemy);
		Entity enemy2 = new Entity();
		enemy2.add(new PositionComponent(40, 40, 0));
		enemy2.add(new VelocityComponent());
		enemy2.add(new TextureComponent(new TextureRegion(enemyTexture)));
		enemy2.add(new SizeComponent(24f));
		enemy2.add(new AIMovementComponent());
		engine.addEntity(enemy2);
		
		// Add all the systems. Order is important.		
		PlayerInputSystem playerInputSystem = new PlayerInputSystem(camera, cursor);
		engine.addSystem(playerInputSystem);
		
		AIMovementSystem AIMovementSystem = new AIMovementSystem(player);
		engine.addSystem(AIMovementSystem);
		
		BoundaryCollisionSystem boundaryCollisionSystem = new BoundaryCollisionSystem(gameBoundary);
		engine.addSystem(boundaryCollisionSystem);
		
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);

		RenderSystem renderSystem = new RenderSystem(camera);
		engine.addSystem(renderSystem);
		
		BulletCollisionSystem bulletCollisionSystem = new BulletCollisionSystem();
		engine.addSystem(bulletCollisionSystem);
		
		ShootingSystem shootingSystem = new ShootingSystem();
		engine.addSystem(shootingSystem);
		
		EnemySpawnSystem enemySpawnSystem = new EnemySpawnSystem(gameBoundary, enemyTexture);
		engine.addSystem(enemySpawnSystem);
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
