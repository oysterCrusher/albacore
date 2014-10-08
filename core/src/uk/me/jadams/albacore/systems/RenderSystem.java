package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

	public RenderSystem(OrthographicCamera camera) {
		this.camera = camera;
		batch = new SpriteBatch();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class,
				TextureComponent.class,
				SizeComponent.class));
	}
	
	@Override
	public void update(float deltaTime) {
		PositionComponent position;
		TextureComponent texture;
		SizeComponent size;
		
		camera.update();
		
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			position = pm.get(e);
			texture = tm.get(e);
			size = sm.get(e);
			
			// Just assume the texture is not null?			
			batch.draw(texture.region,
					position.x - size.size * 0.5f, position.y - size.size * 0.5f,
					size.size * 0.5f, size.size * 0.5f,
					size.size, size.size,
					1, 1,
					position.angle, true);
		}
		batch.end();
	}

}
