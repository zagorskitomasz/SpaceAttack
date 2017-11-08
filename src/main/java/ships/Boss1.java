package ships;

import projectiles.GreenLaser;
import projectiles.Rocket;
import spaceAttack.Game;

public class Boss1 extends EnemyShip{

	int dmgGreen = 20;
	int dmgRocket = 30;
	int lastRocket;
	
	public Boss1(Game g) {
		super(g, 500, -60, 100, 2, 1, 1, 4, 500, 500, 0, 1);
		
		img = game.media.bossI;
		lastRocket = game.millis();
	}

	public boolean move() {
		if(yPos>game.myShip.yPos-300)
			yPos-=speed;
		else if(yPos<game.myShip.yPos+350)
			yPos+=speed;
		if(game.myShip.xPos>xPos+10)
			xPos+=speed;
		else if (game.myShip.xPos<xPos-10)
			xPos-=speed;
		
		if(game.millis()>lastRocket+1500){
			game.missiles.add(new Rocket(game, gen.nextInt(801)+100, (int)(yPos+2*radius), dmgRocket, 1));
			lastRocket=game.millis();
		}
		
		if(game.millis()>lastShot+750 && Math.abs(xPos-game.myShip.xPos)<=50 && game.myShip.yPos-yPos>150 && game.myShip.yPos-yPos<350){
			game.missiles.add(new GreenLaser(game, xPos+30, (int)(yPos+1.2*radius), 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos-30, (int)(yPos+1.2*radius), 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos+40, (int)(yPos+1.2*radius)-10, 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos-40, (int)(yPos+1.2*radius)-10, 0, 1, dmgGreen));
			lastShot=game.millis();
		}
		
		if(!game.completed){
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
		}
		
		if(hp<=0){
			hit=true;
			destroy();
			game.completed();
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
		int hpPer = 80*hp/maxHp;
		if(hpPer<0) hpPer=0;
		game.strokeWeight(2);
		game.stroke(50,50,50);
		game.noFill();
		game.rect(xPos-40, yPos+40, 80, 10);
		game.fill(190,0,0);
		game.rect(xPos-40,  yPos+40, hpPer, 10);
	}
}
