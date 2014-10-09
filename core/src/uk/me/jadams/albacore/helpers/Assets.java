package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	public static Texture player;
	public static Texture enemy;
	public static Texture bullet;
	public static Texture cursor;
	public static Texture boundary;
	
	public static Texture playerAnimTexture;
	public static Animation playerAnim;
	
	public static Texture enemyExplodeTexture;
	public static Animation enemyExplodeAnim;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		player = loadTexture("player.png");
		enemy = loadTexture("enemy.png");
		bullet = loadTexture("bullet.png");
		cursor = loadTexture("cursor.png");
		boundary = loadTexture("boundary.png");
		
//		enemyExplodeTexture = loadTexture("enemy_dissipate.png");
//		TextureRegion[][] ttr = TextureRegion.split(enemyExplodeTexture, 64, 64);
//		TextureRegion[] enemyExplodeFrames = new TextureRegion[15];
//		for (int i = 0; i < 5; i++) {
//			for (int j = 0; j < 3; j++) {
//				enemyExplodeFrames[j + 3 * i] = ttr[i][j];
//			}
//		}
//		enemyExplodeAnim = new Animation(0.1f, enemyExplodeFrames);
		
		enemyExplodeTexture = loadTexture("enemy_dissipate2.png");
		TextureRegion[][] ttr = TextureRegion.split(enemyExplodeTexture, 64, 64);
		TextureRegion[] enemyExplodeFrames = new TextureRegion[8];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				enemyExplodeFrames[j + 4 * i] = ttr[i][j];
			}
		}
		enemyExplodeAnim = new Animation(0.1f, enemyExplodeFrames);
		
	}

}
