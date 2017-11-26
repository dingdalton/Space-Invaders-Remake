package spaceinvaders;
import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InGameApplet extends Applet implements Runnable, KeyListener {

    Thread theThread;
    Player player;

    //Initialize the applet
    public void init() {
    	addKeyListener(this);
    	this.setSize(new Dimension(1280,720));
    	player = new Player(50, 50);
    }
    
    public void keyPressed( KeyEvent e ) {
    	char c = e.getKeyChar();
    	if(c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT){
    		player.moveLeft(4);
    	}else if(c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT){
    		player.moveRight(4);
    	}
    }
    public void keyReleased( KeyEvent e ) { }
    public void keyTyped( KeyEvent e ) {
       char c = e.getKeyChar();
       if ( c != KeyEvent.CHAR_UNDEFINED ) {
          repaint();
          e.consume();
       }
    }

    //start the thread for applet
    public void start() {
        if (theThread == null) {
            theThread = new Thread(this);
            theThread.start();
        }
    }

    //stops the thread
    public void stop() {
        if (theThread != null) {
            theThread.stop();
            theThread = null;
        }
    }

    //Draw objects with this method
    public void paint(Graphics g) {
    	drawBG(g);
    	g.setColor(Color.RED);
    	g.fillRect(50, 50, 5, 15);
    }
    
    public void drawBG(Graphics g){
    	g.setColor(Color.black);
    	g.fillRect(0, 0, 10000, 10000);
    }
    
    public void drawGameOverScreen(Graphics g){
    	
    }

    /**
     * All work should be done here.
     */
    public void run() {
    }
}