package ships;

import projectiles.Mine;
import projectiles.RedLaser;
import projectiles.Shield;
import spaceAttack.Game;

public class ETank extends EnemyShip{

	int dmgMine, dmgShield;
	
	public ETank(Game g, int fact) {
		super(g, 500, -60, 50, 1, (int)(16*Math.pow(1.55, fact-1)), 70, 3, (int)(60*Math.pow(1.55, fact-1)), 100, (int)(10*Math.pow(1.55, fact-1)), fact);
		
		ene=30;
		img = game.media.tankI;
		xPos=gen.nextInt(801)+100;
		
		dmgMine = (int)(20*Math.pow(1.55, fact-1));
		dmgShield = (int)(5*Math.pow(1.55, fact-1));
	}

	public boolean move() {
		yPos+=speed;
		
		if(!game.failed && game.millis()>lastShot+500){
			if(xPos+50<game.myShip.xPos){
				if(yPos+50<game.myShip.yPos)
					game.missiles.add(new RedLaser(game, (int)(xPos+1.2*radius), (int)(yPos+1.2*radius), 1, 1, dmgRed));
				else if(yPos-50>game.myShip.yPos)
					game.missiles.add(new RedLaser(game, (int)(xPos+1.2*radius), (int)(yPos-1.2*radius), 1, -1, dmgRed));
				else
					game.missiles.add(new RedLaser(game, (int)(xPos+1.2*radius), yPos, 1, 0, dmgRed));
			}
			else if(xPos-50>game.myShip.xPos){
				if(yPos+50<game.myShip.yPos)
					game.missiles.add(new RedLaser(game, (int)(xPos-1.2*radius), (int)(yPos+1.2*radius), -1, 1, dmgRed));
				else if(yPos-50>game.myShip.yPos)
					game.missiles.add(new RedLaser(game, (int)(xPos-1.2*radius), (int)(yPos-1.2*radius), -1, -1, dmgRed));
				else
					game.missiles.add(new RedLaser(game, (int)(xPos-1.2*radius), yPos, -1, 0, dmgRed));
			}
			else{
				if(yPos+50<game.myShip.yPos)
					game.missiles.add(new RedLaser(game, xPos, (int)(yPos+1.2*radius), 0, 1, dmgRed));
				else if(yPos-50>game.myShip.yPos)
					game.missiles.add(new RedLaser(game, xPos, (int)(yPos-1.2*radius), 0, -1, dmgRed));
			}
			
			lastShot=game.millis();
		}
		
		if(faction>2 && ene>=90){
			if(faction==3){
				if(xPos>game.myShip.xPos) game.missiles.add(new Mine(game, xPos-120, yPos+80, dmgMine));
				else game.missiles.add(new Mine(game, xPos+120, yPos+80, dmgMine));
			}
			else if(faction>=4) game.missiles.add(new Shield(game, xPos, yPos, dmgShield, this));
			ene-=90;
			lastShot=game.millis();
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
		int hpPer = 60*hp/maxHp;
		if(hpPer<0) hpPer=0;
		game.strokeWeight(2);
		game.stroke(50,50,50);
		game.noFill();
		game.rect(xPos-30, yPos-90, 60, 10);
		game.fill(190,0,0);
		game.rect(xPos-30,  yPos-90, hpPer, 10);
	}
}
