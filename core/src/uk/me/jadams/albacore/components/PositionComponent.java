package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class PositionComponent extends Component {
	public float x = 640f;
	public float y = 360f;
	public float angle = 0f; // Forward direction, in degrees. 

	public PositionComponent() {

	}
	
	public PositionComponent(float x, float y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
}
