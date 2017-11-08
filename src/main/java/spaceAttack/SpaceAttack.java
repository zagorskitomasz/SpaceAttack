package spaceAttack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SpaceAttack extends JPanel{
	
	private static final long serialVersionUID = 1L;
	static int pLevel;
	static int exp;
	static int mission;
	static int missionMax;
	static String name;
	static File currentSave;
	public static JButton[] missions;
	static mainFrame frame;
	
	public static void main(String[] args){
		
		EventQueue.invokeLater(()->{
			missions = new JButton[11];
			for(int i=0; i<SpaceAttack.missions.length; i++){
				missions[i] = new JButton("Mission "+(i+1));
				missions[i].setBounds(18, 62+i*27, 140, 20);
			}
			
			missions[0].addActionListener(l -> {
				mission = 1;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[1].addActionListener(l -> {
				mission = 2;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[2].addActionListener(l -> {
				mission = 3;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[3].addActionListener(l -> {
				mission = 4;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[4].addActionListener(l -> {
				mission = 5;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[5].addActionListener(l -> {
				mission = 6;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[6].addActionListener(l -> {
				mission = 7;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[7].addActionListener(l -> {
				mission = 8;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[8].addActionListener(l -> {
				mission = 9;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[9].addActionListener(l -> {
				mission = 10;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			missions[10].addActionListener(l -> {
				mission = 11;
				JOptionPane.showMessageDialog(null, "If game lags or crashes after few levels, restart it!\n\nIf game becomes too hard, build some experience in previous missions!", "Information", 1);
				run();
			});
			
			frame = new mainFrame();
			frame.setTitle("SpaceAttack");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
	
	static Thread thread;
	
	public static void run() {
		thread = new Thread(() -> Game.run());
		thread.run();
	}
	
	public static boolean load(File file){
		
		try {
			Scanner reader = new Scanner(file);
			pLevel = reader.nextInt();
			exp = reader.nextInt();
			missionMax=reader.nextInt();
			currentSave = file;
			reader.close();
			
			for(int i=0; i<missions.length; i++){
				if(i<missionMax)
					missions[i].setEnabled(true);
				else
					missions[i].setEnabled(false);
			}
			if(frame.gameMenu==null)
				frame.gameMenu = new GameMenu(frame);
			frame.gameMenu.setVisible(true);
			if(frame.gameMenu!=null){
				frame.gameMenu.t1.setVisible(false); 
				frame.gameMenu.t1 = new JLabel("Your level: " + SpaceAttack.pLevel);
				frame.gameMenu.t1.setBounds(25, 10, 140, 25);
				frame.gameMenu.add(frame.gameMenu.t1);
				frame.gameMenu.t1.repaint();
				frame.gameMenu.t1.setVisible(true);
			}
			return true;
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File doesn't exist!", "Error", 0);
			return false;
		}
	}
	
	public static void newSave(File file){
		
		Locale.setDefault(new Locale("en", "US"));
		try {
			PrintWriter saver = new PrintWriter(file.getPath()+".sav");
			saver.print(1 + " " + 0 + " " + 1);
			saver.close();
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File doesn't exist!", "Error", 0);
		}
		load(new File (file.getAbsolutePath()+".sav"));
	}
				
	public static void save(int pLevel, int exp, int mission){
		
		try {
			Scanner reader = new Scanner(currentSave);
			reader.nextInt();
			reader.nextInt();
			missionMax=reader.nextInt();
			reader.close();
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File doesn't exist!", "Error", 0);
		}
		
		try {
			PrintWriter writer = new PrintWriter(currentSave);
			writer.print(pLevel + " " + exp + " ");
			if(mission>missionMax)
				writer.print(mission);
			else
				writer.print(missionMax);
			writer.close();
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File doesn't exist!", "Error", 0);
		}
		load(currentSave);
	}			
}

class mainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	JButton ng, load, info, exit;
	JLabel infos;
	GameMenu gameMenu;
	
	public mainFrame(){
		setSize(315, 435);
		setResizable(false);
		setLocation(300,100);
		setContentPane(new BackGround());setLayout(null);
		
		ng = new JButton("New Game");
		load = new JButton("Load Game");
		info = new JButton("Instructions");
		exit = new JButton("Exit Game");
		
		ng.setBounds(95, 150, 125, 30);
		load.setBounds(95, 210, 125, 30);
		info.setBounds(95, 270, 125, 30);
		exit.setBounds(95, 330, 125, 30);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("").getAbsoluteFile());
		chooser.setFileFilter(new FileNameExtensionFilter("Saves (*.sav)", "sav"));
		chooser.setAcceptAllFileFilterUsed(false);
		
		ng.addActionListener(l ->{
			chooser.showSaveDialog(this);
			File saveFile = chooser.getSelectedFile();
			if(saveFile!=null){
				SpaceAttack.name = saveFile.getName();
				SpaceAttack.newSave(saveFile);
			}
		});
		

		
		load.addActionListener(l -> {
			chooser.showOpenDialog(this);
			File openFile = chooser.getSelectedFile();
			if(openFile!=null){
					SpaceAttack.load(openFile);
			}
		});
		
		info.addActionListener(l -> JOptionPane.showMessageDialog(this, ""
				+ "GAME CONTROLS:\n\n"
				+ "LMB - red laser\n"
				+ "RMB - green laser\n"
				+ "A - rocket\n"
				+ "S - mine\n"
				+ "D - shield\n"
				+ "F - flame\n"
				+ "P - pause on/off\n"
				+ "N - sound on/off\n\n"
				+ "Red bar - HP\n"
				+ "Blue bar - energy\n"
				+ "Purple bar - experience\n"
				+ "Green bar - kill counter\n"
				+ "Yellow bar - time remaining\n\n\n"
				+ "SpaceAttack by Tomasz Zagorski\n"
				+ "zagorskitomasz@gmail.com\n\n"
				+ "Graphics by MilliontVector\n"
				+ "Music by JudasPriest"
				+ "", "Instructions", 1));
		exit.addActionListener(l -> System.exit(0));
		
		add(ng);
		add(load);
		add(info);
		add(exit);
		repaint();
	}
}

class BackGround extends JPanel{
	private static final long serialVersionUID = 1L;
	Image bg;
	
	public BackGround(){
		try{
			//String separator;
			//if(System.getProperty("os.name").toLowerCase(Locale.ENGLISH).indexOf("win")>=0)
			//	separator = "\\";
			//else
			//	separator = "/";
			bg = ImageIO.read(new File("menu.png"));
		}
		catch(IOException exc){}
		setVisible(true);
		setSize(315, 435);
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bg,  0, 0, 315, 435, null);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(315, 435);
	}	
}

class GameMenu extends JDialog{
	private static final long serialVersionUID = 1L;
	
	JButton exiter;
	JLabel t1, t2;
	
	public GameMenu(JFrame owner){
		super(owner, "Missions", true);
		
		setBounds(620, 100, 180, 450);
		setBackground(Color.BLACK);
		setResizable(false);
		
		t1 = new JLabel("Your level: " + SpaceAttack.pLevel);
		t2 = new JLabel("Choose your mission:");
		t1.setBounds(25, 10, 140, 25);
		t2.setBounds(25, 30, 140, 25);
		add(t1);
		add(t2);
		
		this.setLayout(null);
		
		for(int i=0; i<SpaceAttack.missions.length; i++){
			add(SpaceAttack.missions[i]);
		}
		
		
		exiter = new JButton("Back to menu");
		exiter.addActionListener(l -> setVisible(false));
		exiter.setBounds(18, 374, 140, 30);
		add(exiter);
	}
}
