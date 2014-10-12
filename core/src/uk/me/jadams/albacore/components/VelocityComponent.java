package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent extends Component {
	public float x = 0f;
	public float y = 0f;
	public float max = 200f;
	
	public float omega = 0f; // Angular velocity in degrees per second.
	public float omegaMax = 720f;
	
	public VelocityComponent(float max) {
		this.max = max;
	}
	
	public VelocityComponent(float x, float y, float max) {
		this.x = x;
		this.y = y;
		this.max = max;
	}
	
	public VelocityComponent(float x, float y, float max, float omega, float omegaMax) {
		this.x = x;
		this.y = y;
		this.max = max;
		this.omega = omega;
		this.omegaMax = omegaMax;
	}
}
