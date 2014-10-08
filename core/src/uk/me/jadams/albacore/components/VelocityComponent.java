package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent extends Component {
	public float x = 0f;
	public float y = 0f;
	public float max = 200f;
	
	public VelocityComponent(float max) {
		this.max = max;
	}
}
