package projectiles;

import ddf.minim.AudioPlayer;
import processing.core.PImage;
import spaceAttack.Game;
import spaceAttack.SpaceObject;

public abstract class PowerUp extends SpaceObject{

	AudioPlayer sound;
	PImage img;
	
	public PowerUp(Game g, int x, int y) {
		super(g, x, y, 20, 3, 0, 0, 0);
		sound = game.media.puS;
	}

	public boolean move() {
		yPos += speed;
		
		if(checkCollision(game.myShip)){
			use();
			return true;
		}
		
		if(xPos<-100 || xPos>1100 || yPos<-100 || yPos>750){
			return true;
		}
		return false;
	}

	public void draw() {
		game.image(img, xPos, yPos);
	}

	public abstract void use();
}
