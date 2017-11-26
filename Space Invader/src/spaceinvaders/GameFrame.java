package spaceinvaders;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JPanel{
	
	public static JFrame frame1;
	public Thread animationThread;
	public boolean isRunning;
	public static ArrayList<Enemy> entities = new ArrayList();
	public static ArrayList<Enemy> alive = new ArrayList();
	public static Enemy[][] generatedChunk = new Enemy[4][24];
	boolean doneLoading = true;
	static int interval = 0;
	BufferedImage starsImage;
	
	static KeyListener listener = new KeyListener(){

		@Override
		public void keyPressed(KeyEvent e) {
			int c = e.getKeyCode();
	    	if((c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT) && !Utils.player.moving){
	    		Utils.player.moving = true;
	    		Utils.player.dir = 0;
	    	}else if((c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT) && !Utils.player.moving){
	    		Utils.player.moving = true;
	    		Utils.player.dir = 1;
	    	}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			int c = e.getKeyCode();
	    	if(c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT){
	    		Utils.player.moving = false;
	    	}else if(c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT){
	    		Utils.player.moving = false;
	    	}else if(c == KeyEvent.VK_SPACE){
	    		Utils.player.shoot();
	    	}
		}
		@Override
		public void keyTyped(KeyEvent e) {}
	};

    public GameFrame(){
        animationThread = new Thread () {
            @Override
            public void run() {
                long lastTime = System.nanoTime();
                long timer = System.currentTimeMillis();
                double ns = 1000000000.0/60.0;
                double delta = 0;
                int frames =0;
                int updates = 0;
                if (!animationThread.isInterrupted()) {
                    do {
                        long now = System.nanoTime();
                        delta += (now - lastTime) / ns;
                        lastTime = now;
                        while (delta >=1){
                            update();
                            updates++;
                            delta--;
                            repaint();
                            frames++;
                        }
                        repaint();
                        frames++;
                        if(System.currentTimeMillis() - timer > 1000) {
                            timer += 1000;
                            //System.out.println(updates + "ups, " + frames + " fps");
                            frame1.setTitle("Space Invaders" + " | " + updates + "ups, " + frames + " fps");
                            updates = 0;
                            frames = 0;
                        }  // Refresh the display
                    } while (!animationThread.isInterrupted());
                }
            }
        };
       // loadLevel();
        animationThread.start();  // start the thread to run animation
    }
    
    public void runGame(){
		   SwingUtilities.invokeLater(new Runnable() {
		         @Override
		         public void run() {
		            frame1 = new JFrame("Space Invaders");
		      	  	frame1.addKeyListener(listener);
		            JLabel label = new JLabel();
		            try {
						starsImage = ImageIO.read(new BufferedInputStream(new FileInputStream("stars.jpg")));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            frame1.getContentPane().add(new GameFrame());
		            frame1.pack();
		            frame1.setLocationRelativeTo(null); // center the application window
		            frame1.setResizable(false);
		            frame1.setSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);
		            frame1.setVisible(true);
		        	int delay = 1000;
		            int period = 1000;
		            Utils.gameTimer = new Timer();
		            interval = 120;
		            Utils.gameTimer.scheduleAtFixedRate(new TimerTask() {

		                public void run() {
		                    //System.out.println(setInterval());
		                    Utils.timeremaining = setInterval();
		                    if(Utils.timeremaining == 0)
		                    	Utils.gg = true;
		                }
		            }, delay, period);
		        	Utils.bgMusic = new Music(new File("fade.wav"));
		        	Utils.bgMusic.music();
		        	generateEnemyChunk();
		        	generateStars();
		        	//System.out.println(Utils.stars.size());
		        	System.out.println("done");
		        	doneLoading = true;
		        	Utils.readScores();
                 frame1.addWindowListener(new WindowAdapter() {
                     @Override
                     public void windowClosing(WindowEvent windowEvent) {
                         animationThread.interrupt();
                         //MainMenu.setVisible(true);
                         Utils.bgMusic.stop();
                         isRunning = false;
                         try {
                             animationThread.join();
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                         stop();
                         frame1.dispose();
                         super.windowClosed(windowEvent);
                     }
                 });
		         }
		      }); 
	   }
    
    public void stop(){
        isRunning = false;
    }
    
    public void generateStars(){
    	Random rand = new Random();
    	int maxX = Utils.CANVAS_WIDTH-50;
    	int maxY = Utils.CANVAS_HEIGHT-50;
    	for(int i = 0; i < 100; i++)
    	{
    		int x = rand.nextInt((maxX) + 1);
    		int y = rand.nextInt((maxY) + 1);
        	//System.out.println(x + " " + y);
    		Utils.stars.add(new Point(x, y));
    		//System.out.println(Utils.stars.size());
    		//System.out.println(stars[i].x + " " + stars[i].y);
    	}
    }
    
    
    public void drawBG(Graphics g){
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, 10000, 10000);
    	g.setColor(Color.white);
    	if(doneLoading){
    		//System.out.println(Utils.stars.size());
    		for(Point p : Utils.stars)
    			g.fillOval(p.x, p.y, 4, 4);
    	}
    }
    
    public void experimentHere(Graphics g){
    	g.setColor(Color.WHITE);
    	/*for(int j = 0; j < 4; j++){
    		for(int i = 0; i < 12; i++){
    			g.fillRect(240+i*70, 50+j*80, 40, 20);
    		}
    	}*/
    }
    
    int bossgennum = 0;
    int numCols = 12;
    
    private void generateEnemyChunk(){
    	for(int j = 0; j < 4; j++){
    		for(int i = 0; i < numCols; i++){
    			//if the number of bosses is greater than 2, dont spawn anymore
    			int k = bossgennum < 2 ? (int) Math.floor(Math.random()*2) : 0;
    			if(k == 0){
    				int k1 = (int) Math.floor(Math.random()*4);
    				generatedChunk[j][i] = new SmallEnemy(50+i*80, 60+j*80, k1);
    			}else if(k == 1){
    				bossgennum++;
    				int k1 =(int) Math.floor(Math.random()*2);
    				generatedChunk[j][i] = new BigEnemy(50+i*80, 60+j*80, k1);
    			}
    			
    			//g.fillRect(50+i*50, 50+j*80, 20, 20);
    		}
    	}
    	for(int j = 0; j < 4; j++){
    		for(int i = 0; i < numCols; i++){
    			if(generatedChunk[j][i] != null)
    				alive.add(generatedChunk[j][i]);
    		}
    	}
    }
    Font f = new Font("TimesRoman", Font.PLAIN, 18);
    Font f2 = new Font("TimesRoman", Font.PLAIN, 60);
    
    public void drawHUD(Graphics g){
    	g.setFont(f); 
    	g.drawString("Points: "+Utils.points, 20, 30);
    	g.drawString("Seconds Remaining: " + Utils.timeremaining, Utils.CANVAS_WIDTH-195, 30);
    }
    
    private static final int setInterval() {
        if (interval == 1)
            Utils.gameTimer.cancel();
        return --interval;
    }
    
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.clearRect(0, 0, Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);
    	drawBG(g);
    	experimentHere(g);
    	drawHUD(g);
		if(!Utils.gg){
			Utils.player.draw(g);
			for(Enemy b : entities)
				b.draw(g);
			if(doneLoading){
				for(int i = 0; i < generatedChunk.length; i++){
					for(int j = 0; j < generatedChunk[i].length; j++){
						if(generatedChunk[i][j] != null)
							generatedChunk[i][j].draw(g);
					}
				}
			}
		}else{
			   //draw Game over screen
			g.setFont(f2);
			g.drawString("Game Over", 500, 100);
			g.setFont(f);
			for(int i = 0; i < (Utils.scores.size() < 5 ? Utils.scores.size() : 5); i++){
				String s[] = Utils.scores.get(i).split(":");
				g.drawString(s[0]+ "................. " + s[1], 520, 200+i*100);
			}
		}
    }
    
    boolean updatedScore = false;
    boolean showOnceDialog = false;
    
    public void update(){
    	if(!Utils.gg){
    		Utils.player.update();
    		for(Enemy b : entities){
    			b.update();
    		}
/*    		if(generatedChunk[0][numCols-1] != null && generatedChunk[0][0] != null){
    			if(generatedChunk[0][numCols-1].xpos > Utils.CANVAS_WIDTH-50 || generatedChunk[0][0].xpos < 5){
    				direction = -direction;
    				for(int i = 0; i < generatedChunk.length; i++){
        				for(int j = 0; j < generatedChunk[i].length; j++){
        					if(generatedChunk[i][j] != null)
        						generatedChunk[i][j].ypos+=10;
        				}
        			}
    			}
    		}*/
    		if(doneLoading){
    			for(int i = 0; i < generatedChunk.length; i++){
    				for(int j = 0; j < generatedChunk[i].length; j++){
    					if(generatedChunk[i][j] != null)
    						generatedChunk[i][j].update();
    				}
    			}
    			if(Utils.points > 2 && alive.size() <= 0)
        			Utils.gg = true;
			}
    	}else{
    		if(!updatedScore){
    		JFrame frame = new JFrame();
    	    String message = "Enter your name here:";
    	    String text = JOptionPane.showInputDialog(frame, message);
    	    updatedScore = true;
    	    if (text.length() < 1) {
    	      frame.dispose();
    	    }else{
    	    	frame.dispose();
    	    	Utils.scores.add(text+":"+Utils.points);
    	    	Collections.sort(Utils.scores, new MyComparator());
    			Utils.writeScores();
    	    }
    		}
    	}
    }
    
    
//---------------------------------------------
    /** The Entry main method */
	   public static void main(String[] args) {
	      // Run the GUI codes on the Event-Dispatching thread for thread safety
	        new GameFrame().runGame();
	   }
}