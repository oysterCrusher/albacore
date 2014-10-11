package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

	public static Texture player;
	public static Texture enemy;
	public static Texture enemyGreen;
	public static Texture enemyPurple;
	public static Texture bullet;
	public static Texture cursor;
	public static Texture boundary;
	public static BitmapFont fontOstrichSansRegular64;
	
	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		player = loadTexture("player.png");
		enemy = loadTexture("enemy.png");
		enemyGreen = loadTexture("enemy_green.png");
		enemyPurple = loadTexture("enemy_purple.png");
		bullet = loadTexture("bullet.png");
		cursor = loadTexture("cursor.png");
		boundary = loadTexture("boundary.png");
		fontOstrichSansRegular64 = new BitmapFont(Gdx.files.internal("ostrich_sans_regular_64.fnt"));
	}
	
	public static void dispose() {
		player.dispose();
		enemy.dispose();
		enemyGreen.dispose();
		enemyPurple.dispose();
		bullet.dispose();
		cursor.dispose();
		boundary.dispose();
		fontOstrichSansRegular64.dispose();
	}

}
