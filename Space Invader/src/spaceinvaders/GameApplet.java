package spaceinvaders;
import java.applet.*;
import java.awt.*;

public class GameApplet extends Applet implements Runnable {

    Thread theThread;

    //Initialize the applet
    public void init() {
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
    	
    }

    /**
     * All work should be done here.
     */
    public void run() {
    }
}