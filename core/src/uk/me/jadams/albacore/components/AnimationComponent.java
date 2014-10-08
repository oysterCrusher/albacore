package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationComponent extends Component {
	
	public Animation animation;
	public float time = 0f;
	
	public AnimationComponent(Animation animation) {
		this.animation = animation;
		time = 0f;		
	}
}
