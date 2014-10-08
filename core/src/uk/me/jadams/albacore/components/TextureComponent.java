package uk.me.jadams.albacore.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent extends Component {
	public TextureRegion region;

	public TextureComponent() {
		
	}
	
	public TextureComponent(TextureRegion region) {
		this.region = region;
	}
}
