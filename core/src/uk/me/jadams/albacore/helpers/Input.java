package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {
	
	private float mouseX = 0f;
	private float mouseY = 0f;
	
	public Input() {
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		constrainCursor(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		constrainCursor(screenX, screenY);
		mouseX = screenX;
		mouseY = screenY;
//		cursor.setX(screenX);
//		cursor.setY(screenY);
		return true;
	}
	
	private void constrainCursor(int screenX, int screenY) {
		if (screenX > Gdx.graphics.getWidth()) {
		screenX = Gdx.graphics.getWidth();
		Gdx.input.setCursorPosition(screenX, screenY);
	} else if (screenX < 0) {
		screenX = 0;
		Gdx.input.setCursorPosition(screenX, screenY);
	} 
	if (screenY > Gdx.graphics.getHeight()) {
		screenY = Gdx.graphics.getHeight();
		Gdx.input.setCursorPosition(screenX, screenY);
	} else if (screenY < 0) {
		screenY = 0;
		Gdx.input.setCursorPosition(screenX, screenY);
	}		
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public float getMouseX() {
		return mouseX;
	}
	
	public float getMouseY() {
		return mouseY;
	}

}
