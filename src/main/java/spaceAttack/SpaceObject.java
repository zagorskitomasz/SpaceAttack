package spaceAttack;

import java.util.ConcurrentModificationException;

import projectiles.Explosion;
import projectiles.Mine;
import projectiles.Missile;
import projectiles.Rocket;
import ships.EnemyShip;

public abstract class SpaceObject {
	protected Game game;
	public boolean explosive, toDestroy=false;
	public int xPos, yPos, radius, speed, exStr, exRad, exType;
	
	public SpaceObject(Game g, int x, int y, int r, int sp, int ex, int exR, int exT){
		game = g;
		
		xPos = x;
		yPos = y;
		radius = r;
		speed = sp;
		
		exStr=ex;
		exRad=exR;
		exType = exT;
		if(exStr>0)
			explosive = true;
		else
			explosive = false;
	}
	
	public void explode(){
		Explosion ex = new Explosion(game, xPos, yPos, exType);
		game.explosions.add(game.explosions.size(), ex);
		
		if(game.myShip.checkCollision(ex)){
			game.myShip.takeDmg(exStr);
			game.myShip.takeFire(exStr/5);
		}
		
		try{
			for(EnemyShip eS : game.enemyShips){
				if(eS.checkCollision(ex)){
					eS.takeDmg(exStr);
					eS.takeFire(exStr/5);
				}
			}
			
			for(Missile mS : game.missiles)
				if((mS instanceof Rocket || mS instanceof Mine) && mS.checkCollision(ex))
					mS.toDestroy=true;
		}
		catch(ConcurrentModificationException exc){}
	}
	
	public abstract boolean move();
	public abstract void draw();
	
	public boolean checkCollision(SpaceObject other){
		return (Math.sqrt(Math.pow(Math.abs(this.xPos-other.xPos), 2) + Math.pow(Math.abs(this.yPos-other.yPos), 2))<this.radius+other.radius);
	}
}
