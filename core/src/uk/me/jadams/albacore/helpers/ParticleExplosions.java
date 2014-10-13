package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleExplosions {
	
	public static enum ExplosionType {
		BLUE_HEX,
		GREEN_COG,
		PURPLE_PENT,
		REC_CIRC,
		WHITE_SQUARE
	}
	
	public static enum SprayType {
		WHITE_SQUARE
	}

	private Particles blueHex;
	private Particles greenCog;
	private Particles purplePent;
	private Particles redCirc;
	private Particles whiteSquareExplosion;
	private Particles whiteSquareBurst;
	
	public ParticleExplosions() {
		blueHex = new Particles("effect_blue_hex.p");
		greenCog = new Particles("effect_green_cog.p");
		purplePent = new Particles("effect_purple_pent.p");
		redCirc = new Particles("effect_red_circ.p");
		whiteSquareExplosion = new Particles("white_puff.p");
		whiteSquareBurst = new Particles("effect_white_spray.p");
	}
	
	public void start(ExplosionType explosionType, float x, float y) {
		switch (explosionType) {
			case BLUE_HEX:
				blueHex.start(x, y);
				break;
			case GREEN_COG:
				greenCog.start(x, y);
				break;
			case PURPLE_PENT:
				purplePent.start(x, y);
				break;
			case REC_CIRC:
				redCirc.start(x, y);
				break;
			case WHITE_SQUARE:
				whiteSquareExplosion.start(x, y);
				break;
			default:
				break;
		}
	}
	
	public void start(SprayType sprayType, float x, float y, float angleMin, float angleMax) {
		switch (sprayType) {
			case WHITE_SQUARE:
				whiteSquareBurst.start(x, y, angleMin, angleMax);
				break;
			default:
				break;
		}
	}
	
	public void render(SpriteBatch batch, float delta) {
		whiteSquareBurst.render(batch, delta);
		whiteSquareExplosion.render(batch, delta);
		blueHex.render(batch, delta);
		greenCog.render(batch, delta);
		purplePent.render(batch, delta);
		redCirc.render(batch, delta);
	}
	
	public void dispose() {
		blueHex.dispose();
		greenCog.dispose();
		purplePent.dispose();
		redCirc.dispose();
		whiteSquareExplosion.dispose();
		whiteSquareBurst.dispose();
	}
	
}
