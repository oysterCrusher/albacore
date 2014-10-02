package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.systems.MovementSystem;
import uk.me.jadams.albacore.systems.RenderSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
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
		Texture albacoreTexture = new Texture(Gdx.files.internal("albacore.png"));
		
		OrthographicCamera camera = new OrthographicCamera(800, 600);
		camera.position.set(400, 300, 0);
		
		engine = new Engine();
		
		Entity entity = new Entity();
		entity.add(new PositionComponent());
		entity.add(new TextureComponent(new TextureRegion(albacoreTexture)));
		engine.addEntity(entity);
		
		System.out.println("adding movement system");
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);
		
		System.out.println("adding render system");
		RenderSystem renderSystem = new RenderSystem(camera);
		engine.addSystem(renderSystem);
		System.out.println("added render system");
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
