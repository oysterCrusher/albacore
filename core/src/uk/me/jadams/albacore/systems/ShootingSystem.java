package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.BulletComponent;
import uk.me.jadams.albacore.components.PositionComponent;
import uk.me.jadams.albacore.components.SizeComponent;
import uk.me.jadams.albacore.components.TextureComponent;
import uk.me.jadams.albacore.components.VelocityComponent;
import uk.me.jadams.albacore.components.WeaponComponent;
import uk.me.jadams.albacore.helpers.Assets;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShootingSystem extends EntitySystem {
	
	private Engine engine;
	
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
		
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class, WeaponComponent.class));
	}
	
	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			PositionComponent p = pm.get(e);
			WeaponComponent w = wm.get(e);
			
			if (w.timer > 0) {
				w.timer -= deltaTime;		
			}
			
			if (w.timer <= 0 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
				w.timer = w.interval;
				Entity newBullet = new Entity();
				
				PositionComponent newBulletPosition = new PositionComponent();
				newBulletPosition.x = p.x;
				newBulletPosition.y = p.y;
				newBulletPosition.angle = p.angle;
				newBullet.add(newBulletPosition);
				
				VelocityComponent newBulletVelocity = new VelocityComponent(600f);
				float angleRad = (float) Math.toRadians(p.angle);
				newBulletVelocity.x = (float) (800 * Math.cos(angleRad));
				newBulletVelocity.y = (float) (800 * Math.sin(angleRad));
				newBullet.add(newBulletVelocity);
				
				TextureComponent newBulletTexture = new TextureComponent(new TextureRegion(Assets.bullet));
				newBullet.add(newBulletTexture);
				
				SizeComponent newBulletSize = new SizeComponent(4f);
				newBullet.add(newBulletSize);
				
				newBullet.add(new BulletComponent());
				
				engine.addEntity(newBullet);
			}
		}
	}
}
