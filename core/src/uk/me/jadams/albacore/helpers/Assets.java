package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	
	public static Color Light_Blue = new Color(52f/255f, 152f/255f, 219f/255f, 1);
	public static Color Light_Green = new Color(46f/255f, 204f/255f, 113f/255f, 1);
	public static Color Light_Red = new Color(231f/255f, 76f/255f, 60f/255f, 1);

	public static Texture player;
	public static Texture enemy;
	public static Texture enemyGreen;
	public static Texture enemyPurple;
	public static Texture enemyRed;
	public static Texture bullet;
	public static Texture cursor;
	public static Texture boundary;
	public static Texture blackPixel;
	public static BitmapFont fontOstrichSansRegular64;
	public static BitmapFont fontOstrichSansRegular32;
	
	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		player = loadTexture("player.png");
		enemy = loadTexture("enemy.png");
		enemyGreen = loadTexture("enemy_green.png");
		enemyPurple = loadTexture("enemy_purple.png");
		enemyRed = loadTexture("enemy_red.png");
		bullet = loadTexture("bullet.png");
		cursor = loadTexture("cursor.png");
		boundary = loadTexture("boundary.png");
		blackPixel = loadTexture("black.png");
		fontOstrichSansRegular64 = new BitmapFont(Gdx.files.internal("ostrich_sans_regular_64.fnt"));
		fontOstrichSansRegular32 = new BitmapFont(Gdx.files.internal("ostrich_sans_regular_32.fnt"));
	}
	
	public static void dispose() {
		player.dispose();
		enemy.dispose();
		enemyGreen.dispose();
		enemyPurple.dispose();
		enemyRed.dispose();
		bullet.dispose();
		cursor.dispose();
		boundary.dispose();
		blackPixel.dispose();
		fontOstrichSansRegular64.dispose();
		fontOstrichSansRegular32.dispose();
	}

}
