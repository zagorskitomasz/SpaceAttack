package ships;

import projectiles.Mine;
import projectiles.Rocket;
import spaceAttack.Game;

public class Boss2 extends EnemyShip{

	int dmgMine = 50;
	int dmgRocket = 40;
	int lastRocket;
	
	public Boss2(Game g) {
		super(g, 500, -60, 120, 2, 1, 1, 4, 800, 500, 0, 2);
		
		img = game.media.bossI;
		lastRocket = game.millis();
	}

	public boolean move() {
		if(yPos>game.myShip.yPos-350)
			yPos-=speed;
		else if(yPos<game.myShip.yPos+400)
			yPos+=speed;
		if(game.myShip.xPos>xPos+10)
			xPos+=speed;
		else if (game.myShip.xPos<xPos-10)
			xPos-=speed;
		
		if(game.millis()>lastRocket+1000 && Math.abs(xPos-game.myShip.xPos)<=50 && game.myShip.yPos-yPos>150 && game.myShip.yPos-yPos<400){
			game.missiles.add(new Rocket(game, xPos-100, (int)(yPos+2*radius)-30, dmgRocket, 1));
			game.missiles.add(new Rocket(game, xPos+100, (int)(yPos+2*radius)-30, dmgRocket, 1));
			game.missiles.add(new Rocket(game, xPos, (int)(yPos+2*radius), dmgRocket, 1));
			lastRocket=game.millis();
		}
		
		if(game.millis()>lastShot+2000 ){
			game.missiles.add(new Mine(game, gen.nextInt(801)+100, (int)(yPos+2*radius)+gen.nextInt(200), dmgMine));
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
		int hpPer = 100*hp/maxHp;
		if(hpPer<0) hpPer=0;
		game.strokeWeight(2);
		game.stroke(50,50,50);
		game.noFill();
		game.rect(xPos-40, yPos+50, 100, 10);
		game.fill(190,0,0);
		game.rect(xPos-40,  yPos+50, hpPer, 10);
	}
}
