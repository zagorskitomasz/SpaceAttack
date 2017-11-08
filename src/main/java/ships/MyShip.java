package ships;

import java.util.ArrayDeque;

import processing.core.PImage;
import projectiles.Flame;
import projectiles.GreenLaser;
import projectiles.Mine;
import projectiles.RedLaser;
import projectiles.Rocket;
import projectiles.Shield;
import spaceAttack.Game;

public class MyShip extends Ship{

	int dmgGreen, dmgRocket, dmgMine, dmgShield, dmgFlame, faction, lastHabit;
	
	PImage imgL, imgR;
	
	ArrayDeque<Integer> shipDirect = new ArrayDeque<Integer>(10);
	
	public boolean unRocket=false, unMine=false, unFlame=false, unShield=false;
	
	public MyShip(Game g, int shipLvl, int fact){
		super(g, 500, 400, 1, 0, 1, 1, 4, 1, 1, 1);
		calculateShip(shipLvl, fact);
		img = game.media.myShipI;
		imgL = game.media.myShipLI;
		imgR = game.media.myShipRI;
		
		switch(faction){
		case 1:
			radius = 25;
			break;
		case 2:
			radius = 30;
			unRocket = true;
			break;
		case 3:
			radius = 35;
			unRocket = true;
			unMine = true;
			break;
		case 4:
			radius = 45;
			unRocket = true;
			unMine = true;
			unShield = true;
			break;
		case 5:
			radius = 55;
			unRocket = true;
			unMine = true;
			unShield = true;
			unFlame = true;
			break;
		}
		
		for(int i=0; i<3; i++)
			shipDirect.addFirst(500);
	}
	
	public void calculateShip(int shipLvl, int fact){
		faction = fact;
		double factor = Math.pow(1.1, shipLvl-1);
		maxHp = (int) Math.round((80*factor));
		hp = maxHp;
		maxEne = (int) Math.round((80*factor));
		ene = maxEne;
		dmgRed = (int) Math.round((10*factor));
		dmgGreen = (int) Math.round((20*factor));
		dmgRocket = (int) Math.round((15*factor));
		dmgMine = (int) Math.round((15*factor));
		dmgShield = (int) Math.round((3*factor));
		dmgFlame = (int) Math.round((5*factor));
		dmgFired = 0;
	}

	public void destroy() {
		explode();
		game.tEndGame=game.millis();
	}

	public boolean move() {
		if(game.mouseX<60)
			xPos = 60;
		else if(game.mouseX>940)
			xPos = 940;
		else
			xPos = game.mouseX;
		
		if(game.mouseY<70)
			yPos = 70;
		else if(game.mouseY>580)
			yPos = 580;
		else
			yPos = game.mouseY;
		
		if(game.millis()>lastHabit+500){
			int now = xPos/100;
			game.habits[now]++;
			
			game.top1=0;
			game.top2=0;
			game.top3=0;
			
			for(int i=0; i<game.habits.length; i++)
				if(game.habits[i]>game.habits[game.top1])
					game.top1=i;
			
			for(int i=0; i<game.habits.length; i++)
				if(game.habits[i]>game.habits[game.top2] && i!=game.top1)
					game.top2=i;
				
			for(int i=0; i<game.habits.length; i++)
				if(game.habits[i]>game.habits[game.top3] && i!=game.top1 && i!=game.top2)
					game.top3=i;
			
			lastHabit=game.millis();
		}
		
		if(!game.failed)
			recharges();
		
		if(!game.failed && hp<=0){
			hp=0;
			destroy();
			isDead = true;
			return true;
		}
		return  false;
	}

	public void draw() {
		int last = shipDirect.removeLast();
		if(xPos>last)
			game.image(imgR, xPos, yPos);
		else if (xPos<last)
			game.image(imgL, xPos, yPos);
		else
			game.image(img, xPos, yPos);
		shipDirect.addFirst(xPos);
		if(fired)
			game.image(burning[(int) ((game.millis()/33)%burning.length)], xPos, yPos);
	}

	public void shotRed() {
		if(game.millis()>lastShot+100){
			if(ene>=5){
				game.missiles.add(new RedLaser(game, xPos, (int)(yPos-1.2*radius), 0, -1, dmgRed));
				ene-=5;
			lastShot=game.millis();
			}
		}
	}

	public void shotGreen() {
		if(game.millis()>lastShot+100){
			if(ene>=15){
				game.missiles.add(new GreenLaser(game, xPos, (int)(yPos-1.2*radius), 0, -1, dmgGreen));
				ene-=15;
			lastShot=game.millis();
			}
		}
	}
	
	public void shotRocket() {
		if(unRocket && game.millis()>lastShot+100){
			if(ene>=20){
				game.missiles.add(new Rocket(game, xPos, (int)(yPos-2*radius), dmgRocket, -1));
				ene-=20;
			lastShot=game.millis();
			}
		}
	}
	
	public void shotMine() {
		if(unMine && game.millis()>lastShot+100){
			if(ene>=40){
				game.missiles.add(new Mine(game, xPos, (int)(yPos-2*radius), dmgMine));
				ene-=40;
			lastShot=game.millis();
			}
		}
	}
	
	public void shotShield() {
		if(unShield && game.millis()>lastShot+100){
			if(ene>=100){
				game.missiles.add(new Shield(game, xPos, yPos, dmgShield, this));
				ene-=100;
			lastShot=game.millis();
			}
		}
	}
	
	public void shotFlame() {
		if(unFlame && game.millis()>lastShot+100){
			if(ene>=150){
				game.missiles.add(new Flame(game, xPos, (int)(3.5*radius), -1, dmgFlame, this));
				ene-=150;
			lastShot=game.millis();
			}
		}
	}
}
