package ships;

import projectiles.GreenLaser;
import spaceAttack.Game;

public class BossEndChild1 extends EnemyShip{

	int dmgGreen = 100;
	
	public BossEndChild1(Game g) {
		super(g, 500, -60, 60, 3, 1, 1, 4, 7000, 500, 0, 5);
		
		img = game.media.child1I;
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
		
		if(game.millis()>lastShot+250 && Math.abs(xPos-game.myShip.xPos)<=50 && game.myShip.yPos-yPos>100 && game.myShip.yPos-yPos<400){
			game.missiles.add(new GreenLaser(game, xPos+30, (int)(yPos+1.2*radius), 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos-30, (int)(yPos+1.2*radius), 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos+40, (int)(yPos+1.2*radius)-10, 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos-40, (int)(yPos+1.2*radius)-10, 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos+50, (int)(yPos+1.2*radius)-25, 0, 1, dmgGreen));
			game.missiles.add(new GreenLaser(game, xPos-50, (int)(yPos+1.2*radius)-25, 0, 1, dmgGreen));
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
			for(EnemyShip eS : game.enemyShips){
				if(eS instanceof BossFinal){
					BossFinal temp = (BossFinal) eS;
					temp.unHide();
				}	
			}
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
