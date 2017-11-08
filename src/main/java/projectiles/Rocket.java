package projectiles;

import spaceAttack.Game;

public class Rocket extends Missile{

	public Rocket(Game g, int x, int y, int ex, int yD) {
		super(g, x, y, 10, 4, ex, 50, 1, 0, yD, 0);
		if(yDir==1) img = game.media.rocketEnI;
		else if(yDir==-1) img = game.media.rocketPlI;
		sound = game.media.rocketS;
		sound.rewind();
		sound.play();
	}
}
