package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;

public class WeaponComponent extends Component {

	public int dmg = 1;
	public float interval = 0.1f;
	public float timer = 0;
	
	public WeaponComponent() {
		timer = 0;
	}
	
}
