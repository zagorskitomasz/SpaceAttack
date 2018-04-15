package projectiles;

import spaceAttack.Game;

public class Battery extends PowerUp{

	public Battery(Game g, int x, int y) {
		super(g, x, y);
		img = game.media.batteryI;
	}

	public void use() {
		sound.rewind();
		sound.play();
		int newEne = game.myShip.ene + game.myShip.maxEne/3;
		if(newEne<game.myShip.maxEne)
			game.myShip.ene = newEne;
		else
			game.myShip.ene = game.myShip.maxEne;
	}
}
