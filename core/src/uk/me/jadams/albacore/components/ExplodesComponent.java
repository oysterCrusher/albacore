package uk.me.jadams.albacore.components;

import uk.me.jadams.albacore.helpers.ParticleExplosions.ExplosionType;

import com.badlogic.ashley.core.Component;

public class ExplodesComponent extends Component {

	public ExplosionType type;
	
	public ExplodesComponent(ExplosionType explosionType) {
		type = explosionType; 
	}
}
