package projectiles;

import processing.core.PImage;
import ships.Ship;
import spaceAttack.Game;

public class Shield extends Missile{

	Ship father;
	PImage[] shieldAnim;
	int started;
	int lastHit;
	
	public Shield(Game g, int x, int y, int dmg, Ship f) {
		super(g, x, y, 225, 0, 0, 0, 0, 0, 0, dmg);
		father = f;
		father.shielded = true;
		shieldAnim = game.media.shieldAnim;
		started = game.millis();
		lastHit = game.millis();
		sound = game.media.shieldS;
		sound.rewind();
		sound.play();
	}

	public boolean move() {
		xPos = father.xPos;
		yPos = father.yPos;
		
		if(game.millis()<started+3000 && !father.isDead){
			if(game.millis()>lastHit+400){
				lastHit = game.millis();
				
				if(game.myShip!=father && game.myShip.checkCollision(this)){
					game.myShip.takeDmg(damage);
					sound.rewind();
					sound.play();
				}
				
				for(int i=0; i<game.enemyShips.size(); i++){
					if(game.enemyShips.get(i)!=father && game.enemyShips.get(i).checkCollision(this)){
						game.enemyShips.get(i).takeDmg(damage);
						sound.rewind();
						sound.play();
					}
				}
				
				for(int i=0; i<game.missiles.size(); i++){
					if(game.missiles.get(i)!=this && !(game.missiles.get(i) instanceof Flame) && game.missiles.get(i).checkCollision(this)){
						sound.rewind();
						sound.play();
						game.missiles.get(i).toDestroy=true;
					}
				}
			}
			return false;
		}
		else{
			father.shielded=false;
			return true;
		}
	}
	
	public void draw(){
		game.image(shieldAnim[(int) ((game.millis()/33)%shieldAnim.length)], xPos, yPos);
	}
}
