package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.components.WeaponComponent;
import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.helpers.Boundaries;
import uk.me.jadams.albacore.helpers.Cursor;
import uk.me.jadams.albacore.helpers.Input;
import uk.me.jadams.albacore.helpers.Particles;
import uk.me.jadams.albacore.systems.AIMovementSystem;
import uk.me.jadams.albacore.systems.AnimationSystem;
import uk.me.jadams.albacore.systems.BoundaryCollisionSystem;
import uk.me.jadams.albacore.systems.BulletCollisionSystem;
import uk.me.jadams.albacore.systems.EnemySpawnSystem;
import uk.me.jadams.albacore.systems.LifetimeSystem;
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
	
	Particles largeBlue;
	Particles smallWhite;
	
	@Override
	public void render(float delta) {
		fpsLogger.log();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		smallWhite.render(batch, delta);
		largeBlue.render(batch, delta);
		batch.end();
		gameBoundary.render();
		engine.update(delta);
		batch.begin();
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
		
		// Messing around with particles
		largeBlue = new Particles("blue_explosion.p");
		smallWhite = new Particles("white_puff.p");
		
		batch = new SpriteBatch();
		
		cursor = new Cursor();
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
		player.add(new VelocityComponent(200f));
		player.add(new TextureComponent(new TextureRegion(Assets.player)));
		player.add(new PlayerInputComponent());
		player.add(new SizeComponent(28f));
		player.add(new WeaponComponent());
		engine.addEntity(player);
		
		// Add all the systems. Order is important.		
		PlayerInputSystem playerInputSystem = new PlayerInputSystem(camera, cursor);
		engine.addSystem(playerInputSystem);
		
		AIMovementSystem AIMovementSystem = new AIMovementSystem(player);
		engine.addSystem(AIMovementSystem);
		
		BoundaryCollisionSystem boundaryCollisionSystem = new BoundaryCollisionSystem(gameBoundary, smallWhite);
		engine.addSystem(boundaryCollisionSystem);
		
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);
		
		AnimationSystem animationSystem = new AnimationSystem();
		engine.addSystem(animationSystem);

		RenderSystem renderSystem = new RenderSystem(camera);
		engine.addSystem(renderSystem);
		
		BulletCollisionSystem bulletCollisionSystem = new BulletCollisionSystem(largeBlue);
		engine.addSystem(bulletCollisionSystem);
		
		ShootingSystem shootingSystem = new ShootingSystem();
		engine.addSystem(shootingSystem);
		
		EnemySpawnSystem enemySpawnSystem = new EnemySpawnSystem(gameBoundary);
		engine.addSystem(enemySpawnSystem);
		
		LifetimeSystem lifetimeSystem = new LifetimeSystem();
		engine.addSystem(lifetimeSystem);
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
		largeBlue.dispose();
		smallWhite.dispose();
	}

}
