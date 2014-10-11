package uk.me.jadams.albacore.helpers;

import java.text.NumberFormat;
import java.util.Locale;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scoring {
	
	private int score = 0;
	private String scoreString;
	private NumberFormat f = NumberFormat.getInstance(Locale.UK);
	
	public Scoring() {
		score = 0;
		generateString();
	}
	
	public void add(int s) {
		score += s;
		generateString();
	}
	
	private void generateString() {
		scoreString = f.format(score);
	}
	
	public void render(SpriteBatch batch) {
		float x = 0.5f * (1280f - Assets.fontOstrichSansRegular64.getBounds(scoreString).width);
		Assets.fontOstrichSansRegular64.draw(batch, scoreString, x, 700);
	}

}
