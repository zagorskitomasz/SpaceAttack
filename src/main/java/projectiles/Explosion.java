package projectiles;

import ddf.minim.AudioPlayer;
import processing.core.PImage;
import spaceAttack.Game;
import spaceAttack.SpaceObject;

public class Explosion extends SpaceObject{

	AudioPlayer sound;
	PImage[] exGif;
	int startedT, duration;
	
	public Explosion(Game g, int x, int y, int exT) {
		super(g, x, y, 0, 0, 0, 0, exT);
		
		switch(exType){
		case 1:
			exGif = game.media.exMAnim;
			sound = game.media.exMS;
			radius = 120;
			duration = 3000;
			break;
		case 2:
			exGif = game.media.exFAnim;
			sound = game.media.exSS;
			radius = 70;
			duration = 3000;
			break;
		case 3:
			exGif = game.media.exTAnim;
			sound = game.media.exSS;
			radius = 80;
			duration = 3000;
			break;
		case 4:
			exGif = game.media.exBAnim;
			sound = game.media.exBS;
			radius = 250;
			duration = 3000;
			break;
		}
		
		sound.rewind();
		sound.play();
		startedT=game.millis();
	}

	public boolean move() {
		if(game.millis()>startedT+duration || this.toDestroy)
			return true;
		return false;
	}

	public void draw() {
		if((int) (game.millis()-startedT)/40 < exGif.length)
			game.image(exGif[(int) ((game.millis()-startedT)/40)%exGif.length], xPos, yPos);
	}
}
