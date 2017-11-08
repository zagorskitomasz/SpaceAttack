package ships;

import projectiles.Flame;
import projectiles.RedLaser;
import spaceAttack.Game;

public class Boss4 extends EnemyShip{
	
	int dmgFlame = 30, lastFlame, lastSalve;
	boolean salve = true;
	
	public Boss4(Game g) {
		super(g, 500, -60, 130, 2, 1, 1, 4, 3500, 500, 100, 3);
		
		img = game.media.bossI;
		lastFlame = game.millis()+4000;
		lastSalve = game.millis();
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
		
		if(game.millis()>lastSalve+5000){
			salve=true;
			lastSalve=game.millis();
		}
		if(game.millis()>lastSalve+2000)
			salve=false;
		
		if(game.millis()>lastShot+250 && salve){
			game.missiles.add(new RedLaser(game, xPos-20, (int)(yPos+1.2*radius), 0, 1, dmgRed));
			game.missiles.add(new RedLaser(game, xPos+20, (int)(yPos+1.2*radius), 0, 1, dmgRed));
			game.missiles.add(new RedLaser(game, xPos-20, (int)(yPos+1.2*radius), -1, 1, dmgRed));
			game.missiles.add(new RedLaser(game, xPos-20, (int)(yPos+1.2*radius), -1, 1, dmgRed));
			game.missiles.add(new RedLaser(game, xPos+20, (int)(yPos+1.2*radius), 1, 1, dmgRed));
			game.missiles.add(new RedLaser(game, xPos+20, (int)(yPos+1.2*radius), 1, 1, dmgRed));
			lastShot=game.millis();
		}
		
		if(game.millis()>lastFlame+5000 ){
			game.missiles.add(new Flame(game, xPos, (int)(2*radius), 1, dmgFlame, this));
			lastFlame=game.millis();
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
