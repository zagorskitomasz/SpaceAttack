package spaceAttack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import javax.swing.Timer;

import processing.core.PApplet;
import projectiles.Explosion;
import projectiles.Missile;
import projectiles.PowerUp;
import ships.Boss1;
import ships.Boss2;
import ships.Boss3;
import ships.Boss4;
import ships.Boss5;
import ships.BossFinal;
import ships.EFighter;
import ships.ESpecial;
import ships.ETank;
import ships.EnemyShip;
import ships.MyShip;

public class Game extends PApplet implements ActionListener{
	public MultimediaContainer media;
	
	String name;
	public int pLevel, exp, expReq, mission, faction, tEndGame, killCount=0, killReq, deadline, tLeveled, time, secLeft, tBeware, pausedTime;
	public int[] habits = {0,0,5,7,10,10,7,5,0,0};
	public int top1 = 4, top2 = 5, top3 = 3;
	public boolean saved = false, failed=false, completed=false, leveled=false, battle, beware=false, paused=false, ready = false, sound=true;
	Timer gameTimer, fighterCrea, specialCrea, tankCrea;
	int fighterFreq, specialFreq, tankFreq;
	
	public MyShip myShip;
	
	public LinkedList<Explosion> explosions = new LinkedList<>();
	public LinkedList<Missile> missiles = new LinkedList<>();
	public LinkedList<EnemyShip> enemyShips = new LinkedList<>();
	public LinkedList<PowerUp> powerUps = new LinkedList<>();
	
	public static void run () {
		PApplet.main(new String[] {"spaceAttack.Game"});
	}
	
	public void settings() {
		  size(1000, 650, "processing.opengl.PGraphics3D");
	}
	
	public void setup(){
		noCursor();
		
		pLevel = SpaceAttack.pLevel;
		exp = SpaceAttack.exp;
		mission = SpaceAttack.mission;
		name = SpaceAttack.name;
		
		if(mission%2==1)
			battle=true;
		else
			battle=false;
		if(mission==11)
			battle=false;
		
		if(battle){
			time=240000;
			killReq = 50+mission*5;
		}
		else{
			time=180000;
			killReq = 38+mission*4;
		}
		if(mission==11)
			time=240000;
		
		deadline=millis();

		expReq = 1000*(int)Math.round(Math.pow(1.15, pLevel-1));
		
		faction = (mission+1)/2;
		if(faction==6)
			faction=5;
		
		media = new MultimediaContainer(this);
		
		fighterFreq = 7000-mission*200;
		specialFreq = 9000-mission*300;
		tankFreq = 13000-mission*400;
		
		myShip = new MyShip(this, pLevel, faction);
		
		imageMode(CENTER);
		textAlign(CENTER, CENTER);
		
		gameTimer = new Timer(30, this);
		fighterCrea = new Timer(fighterFreq, event -> enemyShips.add(new EFighter(this, faction, 0, null)));
		specialCrea = new Timer(specialFreq, event -> enemyShips.add(new ESpecial(this, faction)));
		tankCrea = new Timer(tankFreq, event -> enemyShips.add(new ETank(this, faction)));
		
		gameTimer.start();
		fighterCrea.start();
		specialCrea.start();
		tankCrea.start();
		
		media.music.loop();
		media.music.play();
		if(!isLooping())
			loop();
	}
	
	public void draw(){
		background(media.bg);
		
		if(!failed)
			myShip.draw();
		
		try{
			for(EnemyShip eS : enemyShips){
				eS.draw();
			}
			
			for(PowerUp pU : powerUps){
				pU.draw();
			}
			
			for(Missile mS : missiles){
				mS.draw();
			}
			
			for(Explosion eX : explosions){
				eX.draw();
			}
		}
		catch(ConcurrentModificationException ex){}
		
		
		if(beware && millis()<tBeware+2000)
			image(media.bewareScreen, 500, 170);
		
		if(millis()<deadline+5000)
			image(media.helloScreen, 500, 170);
		else
			ready=true;
		
		if(failed && millis()>tEndGame+1000)
			image(media.failedScreen, 500, 170);
		
		if(completed && millis()>tEndGame+1000){
			image(media.completedScreen, 500, 170);
			if(!saved){
				SpaceAttack.save(pLevel, exp, mission+1);
				saved=true;
			}
		}
		
		if(((completed && mission!=11) || failed) && millis()>tEndGame+4000){
			this.dispose();
			this.noLoop();
			this.surface.setVisible(false);
		}
		
		if(completed && mission==11 && millis()>tEndGame+7000){
			this.dispose();
			this.noLoop();
			this.surface.setVisible(false);
		}
		
		if(leveled && millis()<tLeveled+2000)
			image(media.leveledScreen, 500, 470);
		else
			leveled=false;
		
		drawGUI();
	}
	
	public void drawGUI(){
		noStroke();
		fill(190,0,0);
		rect(120,620,260*myShip.hp/myShip.maxHp,20);
		fill(0,0,230);
		rect(620+(260-260*myShip.ene/myShip.maxEne),620,260*myShip.ene/myShip.maxEne,20);
		image(media.barL, 250, 630);
		image(media.barR, 750, 630);
		
		fill(170,0,240);
		rect(10, 527, 20, -(354*exp/expReq));
		image(media.barExp, 20, 350);
		
		secLeft = time-(millis()-deadline);
		fill(0,190,0);
		rect(953, 527, 20, -(Math.min(354*killCount/killReq, 354)));
		fill(230,230,0);
		rect(974, 527, 18, -(354*secLeft/time));
		image(media.barQuest, 970, 350);
		
		strokeWeight(3);
		textSize(20);

		stroke(0,0,0);
		if(myShip.unRocket){
			if(myShip.ene>=20)
				stroke(0,200,0);
			image(media.iRocket, 425, 625);
			fill(255,255,255);
			text("A", 425, 580);
		}
		else
			image(media.iLock, 425, 625);
		noFill();
		rect(401,601,48,48);
		
		stroke(0,0,0);
		if(myShip.unMine){
			if(myShip.ene>=30)
				stroke(0,200,0);
			image(media.iMine, 475, 625);
			fill(255,255,255);
			text("S", 475, 580);
		}
		else
			image(media.iLock, 475, 625);
		noFill();
		rect(451,601,48,48);
		
		stroke(0,0,0);
		if(myShip.unShield){
			if(myShip.ene>=100)
				stroke(0,200,0);
			image(media.iShield, 525, 625);
			fill(255,255,255);
			text("D", 525, 580);
		}
		else
			image(media.iLock, 525, 625);
		noFill();
		rect(501,601,48,48);
		
		stroke(0,0,0);
		if(myShip.unFlame){
			if(myShip.ene>=150)
				stroke(0,200,0);
			image(media.iFlame, 575, 625);
			fill(255,255,255);
			text("F", 575, 580);
		}
		else
			image(media.iLock, 575, 625);
		noFill();
		rect(551,601,48,48);
		
		fill(255,255,255);
		textSize(18);
		text(myShip.hp+"/"+myShip.maxHp, 250, 627);
		text(myShip.ene+"/"+myShip.maxEne, 750, 627);
		
	}
	
	public void mousePressed(){
		if(!myShip.isDead){
			if(mousePressed && mouseButton==LEFT)
				myShip.shotRed();
			if(mousePressed && mouseButton==RIGHT)
				myShip.shotGreen();
		}
	}
	
	public void keyPressed(){
		if(!myShip.isDead){
			if(key=='A' || key=='a')
				myShip.shotRocket();
			if(key=='S' || key=='s')
				myShip.shotMine();
			if(key=='D' || key=='d')
				myShip.shotShield();
			if(key=='F' || key=='f')
				myShip.shotFlame();
		}
		if(key=='P' || key=='p'){
			if(!paused){
				paused=true;
				noLoop();
				media.music.pause();
				pausedTime=millis();
				fighterCrea.stop();
				specialCrea.stop();
				tankCrea.stop();
				gameTimer.stop();
			}
			else{
				paused=false;
				loop();
				media.music.play();
				deadline+=millis()-pausedTime;
				fighterCrea.start();
				specialCrea.start();
				tankCrea.start();
				gameTimer.start();
			}
			
		}
		
		if(key=='N' || key=='n'){
			if(sound){
				sound=false;
				media.music.mute();
				media.redLS.mute();
				media.greenLS.mute();
				media.puS.mute();
				media.exMS.mute();
				media.exSS.mute();
				media.exBS.mute();
				media.flameS.mute();
				media.mineS.mute();
				media.rocketS.mute();
				media.shieldS.mute();
			}
			else{
				sound=true;
				media.music.unmute();
				media.redLS.unmute();
				media.greenLS.unmute();
				media.puS.unmute();
				media.exMS.unmute();
				media.exSS.unmute();
				media.exBS.unmute();
				media.flameS.unmute();
				media.mineS.unmute();
				media.rocketS.unmute();
				media.shieldS.unmute();
			}
			
		}
	}
	
	public void leveled(){
		pLevel++;
		int expLeft = exp-expReq;
		exp = expLeft;
		expReq = 1000*(int)Math.round(Math.pow(1.15, pLevel-1));
		tLeveled = millis();
		leveled=true;
		
		myShip.calculateShip(pLevel, faction);
	}
	
	public void completed(){
		completed=true;
		myShip.shielded=true;
		
		fighterCrea.stop();
		specialCrea.stop();
		tankCrea.stop();
		
		for(EnemyShip eS : enemyShips){
			eS.hp=0;
			eS.toDestroy=true;
		}
		tEndGame=millis();
	}
	
	public void bossFight(){
		if(!beware){
			beware=true;
			tBeware=millis();
			
			fighterCrea.stop();
			specialCrea.stop();
			tankCrea.stop();
			deadline=millis()-6000;
			
			if(mission==2)
				enemyShips.add(new Boss1(this));
			else if(mission==4)
				enemyShips.add(new Boss2(this));
			else if(mission==6)
				enemyShips.add(new Boss3(this));
			else if(mission==8)
				enemyShips.add(new Boss4(this));
			else if(mission==10)
				enemyShips.add(new Boss5(this));
			else if(mission==11)
				enemyShips.add(new BossFinal(this));
		}
	}

	public void actionPerformed(ActionEvent e) {
		
		if(!failed && myShip.move())
			failed=true;
		
		try{
			enemyShips.removeIf(element -> element.move());
			powerUps.removeIf(element -> element.move());
			missiles.removeIf(element -> element.move());
			explosions.removeIf(element -> element.move());
		}
		catch(ConcurrentModificationException ex){}
		
		if(exp>=expReq)
			leveled();
		
		if(!completed && killCount>=killReq){
			if(battle)
				completed();
			else
				bossFight();
		}
		
		if(!failed && !completed && millis()>deadline+time){
			failed=true;
			tEndGame=millis();
			myShip.isDead=true;
			myShip.explode();
			myShip.hp=0;
		}
		
		if(mission==11 && ready)
			bossFight();
	}
}
