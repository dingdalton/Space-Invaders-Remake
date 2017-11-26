package spaceinvaders;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class Utils {
	
	public static boolean gg = false;
	public static int CANVAS_WIDTH = 1280;
	public static int CANVAS_HEIGHT= 720;
	public static Timer gameTimer;
	public static int timeremaining = 120;
	public static ArrayList<Point> stars = new ArrayList();
	public static ArrayList<String> scores = new ArrayList();
	public static int points = 0;
	public static Player player = new Player(CANVAS_WIDTH/2, CANVAS_HEIGHT-80);
	
	public static Music bgMusic;
    //public static SoundEffect death = new SoundEffect("Game Over.wav");

	
	//score written as <name>:<score>
	public static void readScores(){
		try {
			File f = new File("scores.in");
			if(!f.exists())
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine())
				scores.add(sc.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeScores(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("scores.in", "UTF-8");
			for(int i = 0; i < (Utils.scores.size() < 5 ? Utils.scores.size() : 5); i++)
				writer.println(scores.get(i));
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
