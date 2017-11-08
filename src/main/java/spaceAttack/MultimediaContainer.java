package spaceAttack;

import java.util.Locale;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import gifAnimation.Gif;
import processing.core.PImage;

public class MultimediaContainer {
	Game game;
	Minim minim;
	String separator;
	
	public AudioPlayer music, redLS, greenLS, rocketS, mineS, shieldS, flameS, exMS, exSS, exBS, puS;
	
	public PImage 	bg, barL, barR, barExp, barQuest, 
					iRocket, iMine, iShield, iFlame, iLock, 
					failedScreen, completedScreen, leveledScreen, helloScreen, bewareScreen;
	
	public PImage[] shieldAnim, flameEnAnim, flamePlAnim, exMAnim, exFAnim, exTAnim, exBAnim, fireAnim;
			
	public PImage 	redLNSI, redLWEI, redLS1I, redLS2I, greenLI, rocketEnI, rocketPlI, mineI, aidI, batteryI,
					fighterI, specialI, tankI, bossI, bossFI, child1I, child2I,
					myShipI, myShipLI, myShipRI;

	public MultimediaContainer(Game g){
		game = g;
		minim = new Minim(game);
		
		if(System.getProperty("os.name").toLowerCase(Locale.ENGLISH).indexOf("win")>=0)
			separator = "\\";
		else
			separator = "/";
		if(game.mission!=11)
			music=minim.loadFile("music"+separator+"faction"+game.faction+".mp3");
		else
			music=minim.loadFile("music"+separator+"final.mp3");
		
		redLS = minim.loadFile("sfx"+separator+"redShot.mp3");
		greenLS = minim.loadFile("sfx"+separator+"greenShot.mp3");
		puS = minim.loadFile("sfx"+separator+"powerUp.mp3");
		exMS = minim.loadFile("sfx"+separator+"exMissile.mp3");
		exSS = minim.loadFile("sfx"+separator+"exShip.mp3");
		exBS = minim.loadFile("sfx"+separator+"exBoss.mp3");
		flameS = minim.loadFile("sfx"+separator+"fire.mp3");
		mineS = minim.loadFile("sfx"+separator+"mine.mp3");
		rocketS = minim.loadFile("sfx"+separator+"missileFly.mp3");
		shieldS = minim.loadFile("sfx"+separator+"shield.mp3");
		
		bg = game.loadImage("backgrounds"+separator+"mission"+game.mission+".png");
		barL = game.loadImage("interface"+separator+"barL.png");
		barR = game.loadImage("interface"+separator+"barR.png");
		barExp = game.loadImage("interface"+separator+"barExp.png");
		barQuest = game.loadImage("interface"+separator+"barQuest.png");
		iRocket = game.loadImage("interface"+separator+"missile.png");
		iMine = game.loadImage("interface"+separator+"mine.png");
		iShield = game.loadImage("interface"+separator+"shield.png");
		iFlame = game.loadImage("interface"+separator+"fire.png");
		iLock = game.loadImage("interface"+separator+"locked.png");
		failedScreen = game.loadImage("interface"+separator+"failed.png");
		if(game.mission==11)
			completedScreen = game.loadImage("interface"+separator+"completedF.png");
		else
			completedScreen = game.loadImage("interface"+separator+"completed.png");
		leveledScreen = game.loadImage("interface"+separator+"level.png");
		bewareScreen = game.loadImage("interface"+separator+"beware.png");
		if(game.battle)
			helloScreen = game.loadImage("interface"+separator+"forces.png");
		else
			helloScreen = game.loadImage("interface"+separator+"base.png");
		
		shieldAnim = Gif.getPImages(game, "effects"+separator+"shield.gif");
		flameEnAnim = Gif.getPImages(game, "effects"+separator+"flameE.gif");
		flamePlAnim = Gif.getPImages(game, "effects"+separator+"flameP.gif");
		exMAnim = Gif.getPImages(game, "effects"+separator+"missileEx.gif");
		exFAnim = Gif.getPImages(game, "effects"+separator+"fighterEx.gif");
		exTAnim = Gif.getPImages(game, "effects"+separator+"tankEx.gif");
		exBAnim = Gif.getPImages(game, "effects"+separator+"bossEx.gif");
		fireAnim = Gif.getPImages(game, "effects"+separator+"fire.gif");
		
		redLNSI = game.loadImage("effects"+separator+"laserNS.png");
		redLWEI = game.loadImage("effects"+separator+"laserWE.png");
		redLS1I = game.loadImage("effects"+separator+"laserS1.png");
		redLS2I = game.loadImage("effects"+separator+"laserS2.png");
		greenLI = game.loadImage("effects"+separator+"turbo.png");
		rocketEnI = game.loadImage("effects"+separator+"missileE.png");
		rocketPlI = game.loadImage("effects"+separator+"missileP.png");
		mineI = game.loadImage("effects"+separator+"mine.png");
		aidI = game.loadImage("effects"+separator+"aid.png");
		batteryI = game.loadImage("effects"+separator+"battery.png");
		
		fighterI = game.loadImage("ships"+separator+"faction"+game.faction+separator+"fighter.png");
		specialI = game.loadImage("ships"+separator+"faction"+game.faction+separator+"special.png");
		tankI = game.loadImage("ships"+separator+"faction"+game.faction+separator+"tank.png");
		bossI = game.loadImage("ships"+separator+"faction"+game.faction+separator+"boss.png");
		bossFI = game.loadImage("ships"+separator+"final"+separator+"boss.png");
		child1I = game.loadImage("ships"+separator+"final"+separator+"child1.png");
		child2I = game.loadImage("ships"+separator+"final"+separator+"child2.png");
		
		myShipI = game.loadImage("ships"+separator+"player"+separator+"form"+game.faction+".png");
		myShipLI = game.loadImage("ships"+separator+"player"+separator+"form"+game.faction+"L.png");
		myShipRI = game.loadImage("ships"+separator+"player"+separator+"form"+game.faction+"R.png");
	}
}
