package uk.me.jadams.albacore.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleExplosions {
	
	public static enum ExplosionType {
		BLUE_HEX,
		GREEN_COG,
		PURPLE_PENT,
		REC_CIRC
	}

	private Particles blueHex;
	private Particles greenCog;
	private Particles purplePent;
	private Particles redCirc;
	
	public ParticleExplosions() {
		blueHex = new Particles("effect_blue_hex.p");
		greenCog = new Particles("effect_green_cog.p");
		purplePent = new Particles("effect_purple_pent.p");
		redCirc = new Particles("effect_red_circ.p");
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
			default:
				break;
		}
	}
	
	public void render(SpriteBatch batch, float delta) {
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
	}
	
}
