package projectiles;

import spaceAttack.Game;

public class RedLaser extends Missile{
	
	public RedLaser(Game g, int x, int y, int xD, int yD, int dmg) {
		super(g, x, y, 3, 10, 0, 0, 0, xD, yD, dmg);
		if(xDir==0) img = game.media.redLNSI;
		else if(yDir==0) img = game.media.redLWEI;
		else if((xDir==1 && yDir==1) || (xDir==-1 && yDir==-1)) img = game.media.redLS1I;
		else if((xDir==1 && yDir==-1) || (xDir==-1 && yDir==1)) img = game.media.redLS2I;
		
		sound = game.media.redLS;
		sound.rewind();
		sound.play();
	}
}
