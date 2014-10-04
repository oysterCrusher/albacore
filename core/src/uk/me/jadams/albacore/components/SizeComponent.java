package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class SizeComponent extends Component{	
	public float size = 0f;
	
	public SizeComponent(float size) {
		this.size = size;
	}
}
