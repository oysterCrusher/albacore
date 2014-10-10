package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

	public static Texture player;
	public static Texture enemy;
	public static Texture bullet;
	public static Texture cursor;
	public static Texture boundary;
	
	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		player = loadTexture("player.png");
		enemy = loadTexture("enemy.png");
		bullet = loadTexture("bullet.png");
		cursor = loadTexture("cursor.png");
		boundary = loadTexture("boundary.png");
	}

}
