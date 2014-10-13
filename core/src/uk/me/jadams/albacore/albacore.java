package uk.me.jadams.albacore;

import uk.me.jadams.albacore.helpers.Assets;
import uk.me.jadams.albacore.screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;

public class Albacore extends Game {
	
	private FPSLogger fpsLogger;
//	private MemoryLogger memLogger;

	@Override
	public void create() {
		Assets.load();
		fpsLogger = new FPSLogger();
//		memLogger = new MemoryLogger();
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		fpsLogger.log();
//		memLogger.log();		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

}