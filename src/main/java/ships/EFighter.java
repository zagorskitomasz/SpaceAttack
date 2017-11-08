package ships;

import projectiles.RedLaser;
import spaceAttack.Game;

public class EFighter extends EnemyShip{

	int falange;
	Ship father;
	int mode;
	
	public EFighter(Game g, int fact, int fal, Ship f) {
		super(g, 500, -60, 30, 2, (int)(8*Math.pow(1.55, fact-1)), 40, 2, (int)(30*Math.pow(1.55, fact-1)), (int)(50*Math.pow(1.55, fact-1)), (int)(10*Math.pow(1.55, fact-1)), fact);
		
		img = game.media.fighterI;
		falange = fal;
		father = f;
		
		xPos=gen.nextInt(801)+100;
		
		if(falange==0){
			int modTemp = gen.nextInt(5);
			if(modTemp==0 || modTemp==4)
				mode=0;
			else
				mode=modTemp;
		}
		else
			mode=0;
		
		if(falange==0 && gen.nextInt(5)==0){
			game.enemyShips.add(new EFighter(game, faction, 1, this));
			game.enemyShips.add(new EFighter(game, faction, 2, this));
		}
	}

	public boolean move() {
		yPos+=speed;
		if(father!=null && father.isDead) father=null;
		
		if(falange==0 || father==null){
			if(mode==0){
				if(game.myShip.xPos>xPos+10)
					xPos+=3;
				else if (game.myShip.xPos<xPos-10)
					xPos-=3;
			}
			if(mode==1){
				if(game.top1*100+50>xPos+10)
					xPos+=3;
				else if (game.top1*100+50<xPos-10)
					xPos-=3;
			}
			if(mode==2){
				if(game.top2*100+50>xPos+10)
					xPos+=3;
				else if (game.top2*100+50<xPos-10)
					xPos-=3;
			}
			if(mode==3){
				if(game.top3*100+50>xPos+10)
					xPos+=3;
				else if (game.top3*100+50<xPos-10)
					xPos-=3;
			}
		}
		else if(falange==1){
			xPos=father.xPos-90;
			yPos=father.yPos-50;
		}
		else if(falange==2){
			xPos=father.xPos+90;
			yPos=father.yPos-50;
		}
		
		if(!game.failed && game.millis()>lastShot+1000){
			if(Math.abs(xPos-game.myShip.xPos)<=150 && ene>=5){
				game.missiles.add(new RedLaser(game, xPos, (int)(yPos+1.2*radius), 0, 1, dmgRed));
				ene-=5;
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
		game.rect(xPos-20, yPos-70, 40, 10);
		game.fill(190,0,0);
		game.rect(xPos-20,  yPos-70, hpPer, 10);
	}
}
