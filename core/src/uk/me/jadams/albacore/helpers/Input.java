package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {

	private Cursor cursor;

	private float mouseX = 0f;
	private float mouseY = 0f;

	public Input(Cursor cursor) {
		this.cursor = cursor;
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
		constrain(screenX, screenY);
		setCursor();
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		constrain(screenX, screenY);
		setCursor();
		return true;
	}

	private void constrain(int screenX, int screenY) {
		if (screenX > 1280) {
			screenX = 1280;
			Gdx.input.setCursorPosition(screenX, screenY);
		} else if (screenX < 0) {
			screenX = 0;
			Gdx.input.setCursorPosition(screenX, screenY);
		} 
		if (screenY > 720) {
			screenY = 720;
			Gdx.input.setCursorPosition(screenX, screenY);
		} else if (screenY < 0) {
			screenY = 0;
			Gdx.input.setCursorPosition(screenX, screenY);
		}		
	}
	
	private void setCursor() {
		cursor.setX(Gdx.input.getX());
		cursor.setY(Gdx.input.getY());
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
