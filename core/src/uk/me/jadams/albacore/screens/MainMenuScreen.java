package uk.me.jadams.albacore.screens;

import uk.me.jadams.albacore.Albacore;
import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.helpers.Boundary;
import uk.me.jadams.albacore.helpers.ParticleExplosions;
import uk.me.jadams.albacore.helpers.ParticleExplosions.ExplosionType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class MainMenuScreen implements Screen, InputProcessor {
	
	private Albacore game;
	
	SpriteBatch batch;
	private Boundary gameBoundary;

	private Button buttonPlay;
	private Button buttonOptions;
	private Button buttonQuit;
	private Color highlightColor;
	
	private ParticleExplosions particleExplosions;

	private int cursorX;
	private int cursorY;

	public MainMenuScreen(Albacore game) {
		this.game = game;
		
		particleExplosions = new ParticleExplosions();
		
		// Define some button dimensions, from which button positions are defined
		float height = 70;
		float spacing = 35;

		batch = new SpriteBatch();
		gameBoundary = new Boundary(120, 120, 1040, 480);
		buttonPlay = new Button(gameBoundary.getRight() - 400 - 40,
				(0.5f * (720 - 3 * height) - spacing) + height * 2 + spacing * 2, 400, height, "PLAY");
		buttonOptions = new Button(gameBoundary.getRight() - 400 - 40,
				(0.5f * (720 - 3 * height) - spacing) + height + spacing, 400, height, "OPTIONS");
		buttonQuit = new Button(gameBoundary.getRight() - 400 - 40,
				(0.5f * (720 - 3 * height) - spacing), 400, height, "QUIT");

		cursorX = 0;
		cursorY = 0;
		highlightColor = Assets.Light_Red;
	}

	@Override
	public void render(float delta) {
		buttonPlay.update(delta);
		batch.begin();
		particleExplosions.render(batch, delta);
		batch.draw(Assets.blackPixel,
				gameBoundary.getLeft(), gameBoundary.getBottom(),
				gameBoundary.getWidth(), gameBoundary.getHeight());
		Assets.fontOstrichSansRegular64.draw(batch, "ALBACORE", 160, 720 / 2 + 32);
		buttonPlay.render(batch);
		buttonOptions.render(batch);
		buttonQuit.render(batch);
		gameBoundary.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		int r = MathUtils.random(0, 2);
		switch (r) {
		case 0:
			highlightColor = Assets.Light_Red;
			break;
		case 1:
			highlightColor = Assets.Light_Green;
			break;
		case 2:
			highlightColor = Assets.Light_Blue;
			break;
		default:
			break;
		}
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	private class Button {
		float x = 0;
		float y = 0;
		float  w = 0;
		float h = 0;
		int t = 2;
		String text = "BUTTON TEXT";
		float textWidth = 0;
		float textHeight = 0;
		float textX = 0;

		public Button(float x, float y, float w, float h, String text) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.text = text;
			textWidth = Assets.fontOstrichSansRegular32.getBounds(text).width;
			textHeight = Assets.fontOstrichSansRegular32.getBounds(text).height;
			this.textX = x + w - textWidth - 20;
		}

		public void render(SpriteBatch batch) {
			if (click(cursorX, cursorY)) {
				Assets.fontOstrichSansRegular32.setColor(highlightColor);
			} else {
				Assets.fontOstrichSansRegular32.setColor(Color.WHITE);
			}
			Assets.fontOstrichSansRegular32.draw(batch, text,
					textX, y + 0.5f * (h + textHeight));
			batch.draw(Assets.boundary, x, y, w, t);
			batch.draw(Assets.boundary, x, y, t, h);
			batch.draw(Assets.boundary, x + w - t, y, t, h);
			batch.draw(Assets.boundary, x, y + h - t, w, t);
		}

		public void update(float delta) {

		}

		public boolean click(int cx, int cy) {
			return (cx > x && cx < x + w && cy > y && cy < y + h);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!gameBoundary.isInside(screenX, 720 - screenY)) {
			int r = MathUtils.random(0, 3);
			switch (r) {
			case 0:
				particleExplosions.start(ExplosionType.BLUE_HEX, screenX, 720 - screenY);
				break;
			case 1:
				particleExplosions.start(ExplosionType.GREEN_COG, screenX, 720 - screenY);
				break;
			case 2:
				particleExplosions.start(ExplosionType.PURPLE_PENT, screenX, 720 - screenY);
				break;
			case 3:
				particleExplosions.start(ExplosionType.REC_CIRC, screenX, 720 - screenY);
				break;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (buttonPlay.click(screenX, 720 - screenY)) {
			game.setScreen(new GameScreen(game));
		} else if (buttonQuit.click(screenX, 720 - screenY)) {
			Gdx.app.exit();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		updateCursorPosition(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		updateCursorPosition(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void updateCursorPosition(int x, int y) {
		cursorX = x;
		cursorY = 720 - y;
	}

}
