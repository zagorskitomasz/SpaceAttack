package projectiles;

import spaceAttack.Game;

public class Mine extends Missile{

	int seated;
	
	public Mine(Game g, int x, int y, int ex) {
		super(g, x, y, 20, 0, ex, 70, 1, 0, 0, 0);
		img = game.media.mineI;
		sound = game.media.mineS;
		sound.rewind();
		sound.play();
		seated = game.millis();
	}

	public boolean move(){
		if(super.move())
			return true;
		if(game.millis()>seated+3000){
			explode();
			return true;
		}
		return false;
	}
}
