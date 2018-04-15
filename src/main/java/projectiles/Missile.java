package projectiles;

import java.util.ConcurrentModificationException;

import ddf.minim.AudioPlayer;
import processing.core.PImage;
import ships.EnemyShip;
import spaceAttack.Game;
import spaceAttack.SpaceObject;

public abstract class Missile extends SpaceObject{

	AudioPlayer sound;
	PImage img;
	int xDir, yDir, damage;
	
	public Missile(Game g, int x, int y, int r, int sp, int ex, int exR, int exT, int xD, int yD, int dmg) {
		super(g, x, y, r, sp, ex, exR, exT);
		xDir = xD;
		yDir = yD;
		damage = dmg;
	}
	
	public void draw(){
		game.image(img, xPos, yPos);
	}
	
	public boolean move() {
		xPos = xPos+xDir*speed;
		yPos = yPos+yDir*speed;
		
		if(game.myShip.checkCollision(this)){
			game.myShip.takeDmg(damage);
			if(explosive)
				explode();
			return true;
		}
		
		try{
			for(EnemyShip ship : game.enemyShips){
				if(ship.checkCollision(this)){
					ship.takeDmg(damage);
					if(explosive)
						explode();
					return true;
				}
			}
		}
		catch(ConcurrentModificationException ex){}

		if(toDestroy){
			if(explosive)
				explode();
			return true;
		}
		
		if(xPos<-100 || xPos>1100 || yPos<-100 || yPos>750){
			return true;
		}
		
		return false;
	}
}
