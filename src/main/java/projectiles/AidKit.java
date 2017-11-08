package projectiles;

import spaceAttack.Game;

public class AidKit extends PowerUp{

	public AidKit(Game g, int x, int y) {
		super(g, x, y);
		img = game.media.aidI;
	}

	public void use() {
		sound.rewind();
		sound.play();
		int newHp = game.myShip.hp + game.myShip.maxHp/3;
		if(newHp<game.myShip.maxHp)
			game.myShip.hp = newHp;
		else
			game.myShip.hp = game.myShip.maxHp;
	}
}
