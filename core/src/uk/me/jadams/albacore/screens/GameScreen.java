package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.Albacore;
import uk.me.jadams.albacore.components.PlayerInputComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.components.WeaponComponent;
import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.helpers.Boundary;
import uk.me.jadams.albacore.helpers.Cursor;
import uk.me.jadams.albacore.helpers.Input;
import uk.me.jadams.albacore.helpers.ParticleExplosions;
import uk.me.jadams.albacore.helpers.Scoring;
import uk.me.jadams.albacore.systems.AIMovementSystem;
import uk.me.jadams.albacore.systems.AnimationSystem;
import uk.me.jadams.albacore.systems.BoundaryCollisionSystem;
import uk.me.jadams.albacore.systems.BulletCollisionSystem;
import uk.me.jadams.albacore.systems.CorpseRemovalSystem;
import uk.me.jadams.albacore.systems.EnemySpawnSystem;
import uk.me.jadams.albacore.systems.ExplosionSystem;
import uk.me.jadams.albacore.systems.LifetimeSystem;
import uk.me.jadams.albacore.systems.MovementSystem;
import uk.me.jadams.albacore.systems.PlayerEnemyCollisionSystem;
import uk.me.jadams.albacore.systems.PlayerInputSystem;
import uk.me.jadams.albacore.systems.RemoveOutOfRangeSystem;
import uk.me.jadams.albacore.systems.RenderSystem;
import uk.me.jadams.albacore.systems.ShootingSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
	private Albacore game;
	
	public boolean isRunning = true;
	
	private SpriteBatch batch;
	
	private Input input;
	private Engine engine;
	private OrthographicCamera camera;
	private Boundary gameBoundary;
	private Cursor cursor;
	private Scoring scoring;
	
	private ParticleExplosions particleExplosions;
	
	public GameScreen(Albacore game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		particleExplosions.render(batch, delta);
		scoring.render(batch);
		gameBoundary.render(batch);
		batch.end();
		if (isRunning) {
			engine.update(delta);
		} else {
			game.setScreen(new MainMenuScreen(game));
		}
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
		// Messing around with particles
		particleExplosions = new ParticleExplosions();
		
		batch = new SpriteBatch();
		
		cursor = new Cursor();
		Gdx.input.setCursorCatched(true);
		input = new Input(cursor);
//		Gdx.input.setCursorImage(pixmap, xHotspot, yHotspot);
		Gdx.input.setInputProcessor(input);
		
		scoring = new Scoring();
		
		// Make the camera viewport 1280x720 and move it such that the origin is the lower left corner
		camera = new OrthographicCamera(1280, 720);
		camera.position.set(1280f * 0.5f, 720f * 0.5f, 0f);

		// Set the players boundary to a rectangle in the centre of the viewport
		gameBoundary = new Boundary(120, 120, 1040, 480);
		
		// vroom vroom
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
		
		BoundaryCollisionSystem boundaryCollisionSystem = new BoundaryCollisionSystem(gameBoundary, particleExplosions);
		engine.addSystem(boundaryCollisionSystem);
		
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);
		
		PlayerEnemyCollisionSystem playerEnemyCollisionSystem = new PlayerEnemyCollisionSystem(this, player);
		engine.addSystem(playerEnemyCollisionSystem);
		
		AnimationSystem animationSystem = new AnimationSystem();
		engine.addSystem(animationSystem);

		RenderSystem renderSystem = new RenderSystem(camera);
		engine.addSystem(renderSystem);
		
		BulletCollisionSystem bulletCollisionSystem = new BulletCollisionSystem(scoring);
		engine.addSystem(bulletCollisionSystem);
		
		ExplosionSystem explosionSystem = new ExplosionSystem(particleExplosions);
		engine.addSystem(explosionSystem);
		
		ShootingSystem shootingSystem = new ShootingSystem();
		engine.addSystem(shootingSystem);
		
		EnemySpawnSystem enemySpawnSystem = new EnemySpawnSystem(gameBoundary);
		engine.addSystem(enemySpawnSystem);
		
		RemoveOutOfRangeSystem removeOutOfRangeSystem = new RemoveOutOfRangeSystem(engine);
		engine.addSystem(removeOutOfRangeSystem);
		
		LifetimeSystem lifetimeSystem = new LifetimeSystem();
		engine.addSystem(lifetimeSystem);
		
		CorpseRemovalSystem corpseRemoveSystem = new CorpseRemovalSystem(engine);
		engine.addSystem(corpseRemoveSystem);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCursorCatched(false);
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
		particleExplosions.dispose();
		Assets.dispose();
	}

}
