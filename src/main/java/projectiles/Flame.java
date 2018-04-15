package projectiles;

import processing.core.PImage;
import ships.EnemyShip;
import ships.Ship;
import spaceAttack.Game;

public class Flame extends Missile{

	PImage[] flameAnim;
	Ship father;
	int move, tStarted;
	
	public Flame(Game g, int x, int move, int yD, int dmg, Ship f) {
		super(g, x, f.yPos+move*yD, 100, 0, 0, 0, 0, 0, yD, dmg);
		father = f;
		this.move = move*yD;
		tStarted = game.millis();
		
		if(yD==1) flameAnim = game.media.flameEnAnim;
		else if (yD==-1) flameAnim = game.media.flamePlAnim;
		sound = game.media.flameS;
		sound.rewind();
		sound.play();
	}

	public boolean move() {
		xPos = father.xPos;
		yPos = father.yPos+move;
		
		if(!father.isDead && game.millis()<tStarted+4000){
			if(game.myShip.checkCollision(this))
				game.myShip.takeFire(damage);
			
			for(EnemyShip ship : game.enemyShips)
				if(ship.checkCollision(this))
					ship.takeFire(damage);
			
			return false;
		}
		return true;
	}
	
	public void draw(){
		game.image(flameAnim[(int) ((game.millis()/33)%flameAnim.length)], xPos, yPos);
	}
}
