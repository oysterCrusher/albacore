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
		
		playerAnimTexture = loadTexture("playerAnim.png");
		playerAnim = new Animation(1f,
				new TextureRegion(playerAnimTexture, 0, 0, 64, 64),
				new TextureRegion(playerAnimTexture, 64, 0, 64, 64),
				new TextureRegion(playerAnimTexture, 128, 0, 64, 64),
				new TextureRegion(playerAnimTexture, 192, 0, 64, 64));
		
		enemyExplodeTexture = loadTexture("enemy_explode.png");
		enemyExplodeAnim = new Animation(0.2f,
				new TextureRegion(enemyExplodeTexture, 0, 0, 64, 64),
				new TextureRegion(enemyExplodeTexture, 64, 0, 64, 64),
				new TextureRegion(enemyExplodeTexture, 128, 0, 64, 64),
				new TextureRegion(enemyExplodeTexture, 192, 0, 64, 64));
	}

}
