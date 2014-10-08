package uk.me.jadams.albacore;

import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class albacore extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

}