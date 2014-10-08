package uk.me.jadams.albacore.systems;

import uk.me.jadams.albacore.components.AnimationComponent;
import uk.me.jadams.albacore.components.TextureComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AnimationSystem extends IteratingSystem {

	private ComponentMapper<TextureComponent> tm;
	private ComponentMapper<AnimationComponent> am;

	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(Family.getFor(TextureComponent.class, AnimationComponent.class));

		tm = ComponentMapper.getFor(TextureComponent.class);
		am = ComponentMapper.getFor(AnimationComponent.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		TextureComponent texture = tm.get(entity);
		AnimationComponent animation = am.get(entity);

		animation.time += deltaTime;

		if (animation.animation != null) {
			texture.region = animation.animation.getKeyFrame(animation.time, true);
		}
	}

}
