package ships;

import projectiles.Flame;
import projectiles.GreenLaser;
import projectiles.Rocket;
import spaceAttack.Game;

public class ESpecial extends EnemyShip{

	int dmgGreen, dmgRocket, dmgFlame;
	
	public ESpecial(Game g, int fact) {
		super(g, 500, -60, 40, 2+(fact+1)/2, (int)(8*Math.pow(1.55, fact-1)), 50, 2, (int)(30*Math.pow(1.55, fact-1)), 100, 0, fact);
		
		img = game.media.specialI;
		xPos=gen.nextInt(801)+100;
		
		dmgGreen = (int)(20*Math.pow(1.55, fact-1));
		dmgRocket = (int)(15*Math.pow(1.55, fact-1));
		dmgFlame = 5*(faction+1)/2;
	}
	
	public ESpecial(Game g, int fact, int x) {
		super(g, x, -60, 40, 2+(fact+1)/2, (int)(8*Math.pow(1.55, fact-1)), 50, 2, (int)(30*Math.pow(1.55, fact-1)), 100, 0, fact);
		
		img = game.media.specialI;
		xPos=gen.nextInt(801)+100;
		
		dmgGreen = (int)(20*Math.pow(1.55, fact-1));
		dmgRocket = (int)(15*Math.pow(1.55, fact-1));
		dmgFlame = 5*(faction+1)/2;
	}

	public boolean move() {
		if(yPos>game.myShip.yPos-250)
			yPos-=speed;
		else if(yPos<game.myShip.yPos+300)
			yPos+=speed;
		if(game.myShip.xPos>xPos+10)
			xPos+=speed;
		else if (game.myShip.xPos<xPos-10)
			xPos-=speed;
		
		if(!game.failed && game.millis()>lastShot+1000){
			if(Math.abs(xPos-game.myShip.xPos)<=10 && game.myShip.yPos-yPos>150 && game.myShip.yPos-yPos<350 && ene>=30){
				if(faction==1) game.missiles.add(new GreenLaser(game, xPos, (int)(yPos+2*radius), 0, 1, dmgGreen));
				else if(faction<5) game.missiles.add(new Rocket(game, xPos, (int)(yPos+2*radius), dmgRocket, 1));
				else game.missiles.add(new Flame(game, xPos, (int)(3.5*radius), 1, dmgFlame, this));
				ene-=30;
			lastShot=game.millis();
			}
		}
		
		if(!game.completed)
			recharges();
		
		if(hp<=0){
			hit=true;
			destroy();
			return true;
		}
		
		if(yPos>750){
			hit=false;
			destroy();
			return true;
		}
		
		return  false;
	}
	
	public void draw(){
		super.draw();
		int hpPer = 40*hp/maxHp;
		if(hpPer<0) hpPer=0;
		game.strokeWeight(2);
		game.stroke(50,50,50);
		game.noFill();
		game.rect(xPos-20, yPos-80, 40, 10);
		game.fill(190,0,0);
		game.rect(xPos-20,  yPos-80, hpPer, 10);
	}
}
