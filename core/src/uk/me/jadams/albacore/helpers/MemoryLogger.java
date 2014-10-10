package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class MemoryLogger {

	private long lastLogTime;
	
	public MemoryLogger() {
		lastLogTime = TimeUtils.nanoTime();
	}
	
	public void log() {
		if (TimeUtils.nanoTime() - lastLogTime > 1000000000) {
			Gdx.app.log("MemoryLogger", "Native Heap: " + Gdx.app.getNativeHeap());
			Gdx.app.log("MemoryLogger", "Java Heap: " + Gdx.app.getJavaHeap());
			lastLogTime = TimeUtils.nanoTime();
		}
	}
	
}
