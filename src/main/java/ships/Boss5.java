package ships;

import projectiles.GreenLaser;
import spaceAttack.Game;

public class Boss5 extends EnemyShip{

	int dmgGreen = 80, lastSalve, lastShip;
	boolean salve = true;
	
	public Boss5(Game g) {
		super(g, 500, -60, 130, 2, 1, 1, 4, 3500, 500, 0, 3);
		
		img = game.media.bossI;
		lastSalve = game.millis();
		lastShip = game.millis();
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
		if(game.millis()>lastSalve+1500)
			salve=false;
		
		if(game.millis()>lastShot+175 && salve){
			for(int i=0; i<10; i++)
				game.missiles.add(new GreenLaser(game, 100+i*80, (int)(yPos+1.2*radius), 0, 1, dmgGreen));
			lastShot=game.millis();
		}
		
		if(game.millis()>lastShip+4000 ){
			game.enemyShips.add(new ESpecial(game, gen.nextInt(5)+1, 500+300*(gen.nextInt(2)-1)));
			lastShip=game.millis();
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
