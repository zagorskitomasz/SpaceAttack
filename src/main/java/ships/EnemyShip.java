package ships;

import projectiles.AidKit;
import projectiles.Battery;
import spaceAttack.Game;

public abstract class EnemyShip extends Ship{

	int faction;
	boolean hit=false;
	
	public EnemyShip(Game g, int x, int y, int r, int sp, int ex, int exR, int exT, int mH, int mE, int dR, int fact) {
		super(g, x, y, r, sp, ex, exR, exT, mH, mE, dR);
		faction = fact;
	}

	public void destroy() {
		if(hit){
			explode();
			game.exp+=maxHp;
			game.killCount++;
			if(gen.nextInt(3)==0)
				game.powerUps.add(new Battery(game, xPos, yPos));
			else if(gen.nextInt(5)==0)
				game.powerUps.add(new AidKit(game, xPos, yPos));
		}
		isDead=true;
	}

	public void draw() {
		game.image(img, xPos, yPos);
		if(fired)
			game.image(burning[(int) ((game.millis()/33)%burning.length)], xPos, yPos);
	}

}
