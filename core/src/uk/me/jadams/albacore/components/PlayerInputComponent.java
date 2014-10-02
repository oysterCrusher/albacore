package uk.me.jadams.albacore.components;

import uk.me.jadams.albacore.helpers.Input;

import com.badlogic.ashley.core.Component;

public class PlayerInputComponent extends Component {
	public Input input;
	
	public PlayerInputComponent(Input input) {
		this.input = input;
	}
}
