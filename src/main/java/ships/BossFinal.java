package ships;

import projectiles.Mine;
import projectiles.Rocket;
import projectiles.Shield;
import spaceAttack.Game;

public class BossFinal extends EnemyShip{

	int dmgRocket = 150, dmgMine = 150;
	boolean hidden = false, afterFirst = false, afterSecond = false;
	
	public BossFinal(Game g) {
		super(g, 500, 60, 60, 0, 1, 1, 4, 30000, 500, 0, 5);
		
		img = game.media.bossFI;
		game.missiles.add(new Shield(game, xPos, yPos, 100, this));
	}
	
	public void hide(){
		hidden=true;
		xPos=2000;
		yPos=-300;
	}
	
	public void unHide(){
		hidden=false;
		game.missiles.add(new Shield(game, xPos, yPos, 100, this));
		xPos=500;
		yPos=60;
	}
	
	public boolean move() {
		
		if(!hidden && game.millis()>lastShot+600){
			if(gen.nextBoolean()==true)
				game.missiles.add(new Rocket(game, 100+gen.nextInt(801), (int)(yPos+2*radius), dmgRocket, 1));
			else
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
		
		if(!hidden && !afterFirst && hp<20000){
			game.enemyShips.add(new BossEndChild1(game));
			afterFirst=true;
			hide();
		}
		if(!hidden && !afterSecond && hp<10000){
			game.enemyShips.add(new BossEndChild2(game));
			afterSecond=true;
			hide();
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
