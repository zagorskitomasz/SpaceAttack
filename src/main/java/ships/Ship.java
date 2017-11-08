package ships;

import java.util.Random;

import processing.core.PImage;
import spaceAttack.Game;
import spaceAttack.SpaceObject;

public abstract class Ship extends SpaceObject{

	public boolean fired = false, shielded = false, isDead = false;
	
	protected Random gen = new Random();
	
	protected PImage img;
	protected PImage[] burning;
	
	public int hp, ene, maxHp, maxEne, dmgRed, dmgFired, lastShot, lastBurn, lastHpR, lastEnR, lastFired;
	
	public Ship(Game g, int x, int y, int r, int sp, int ex, int exR, int exT, int mH, int mE, int dR) {
		super(g, x, y, r, sp, ex, exR, exT);
		
		maxHp = mH;
		hp = maxHp;
		maxEne = mE;
		ene = maxEne;
		dmgRed = dR;
		dmgFired = 0;
		
		lastShot = game.millis();
		lastBurn = game.millis();
		lastHpR = game.millis();
		lastEnR = game.millis();
		lastFired = game.millis();
		
		burning = game.media.fireAnim;
	}
	
	public void takeDmg(int dmg){
		if(!shielded){
			hp -= dmg*3/4+gen.nextInt(Math.round(dmg/4)+1);
		}
	}
	
	public void takeFire(int dmg){
		fired = true;
		dmgFired = dmg;
		lastFired = game.millis();
	}
	
	public void recharges(){
		if(fired){
			if(game.millis()>lastBurn+500){
				takeDmg(dmgFired);
				lastBurn = game.millis();
			}
			if(game.millis()>lastFired+5000){
				dmgFired = 0;
				fired = false;
			}
		}
		
		if(game.millis()>lastHpR+500 && !fired){
			if(hp<maxHp){
				hp+=maxHp/20;
				if(hp>maxHp)
					hp=maxHp;
			}
			else
				hp=maxHp;
			lastHpR=game.millis();
		}
		
		if(game.millis()>lastEnR+500){
			if(ene<maxEne){
				ene+=maxEne/20;
				if(ene>maxEne)
					ene=maxEne;
			}
			else
				ene=maxEne;
			lastEnR=game.millis();
		}
	}
}
