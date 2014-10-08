package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class LifetimeComponent extends Component {
	public float timeLeft;
	
	public LifetimeComponent(float lifetime) {
		timeLeft = lifetime;
	}
}
