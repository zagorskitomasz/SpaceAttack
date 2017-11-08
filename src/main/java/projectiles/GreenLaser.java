package projectiles;

import spaceAttack.Game;

public class GreenLaser extends Missile{

	public GreenLaser(Game g, int x, int y, int xD, int yD, int dmg) {
		super(g, x, y, 3, 15, 0, 0, 0, xD, yD, dmg);
		img = game.media.greenLI;
		sound = game.media.greenLS;
		sound.rewind();
		sound.play();
	}
}
