package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class SmallEnemy extends Enemy{

	static int width = 40; //TODO add actual dim once known
	static int height = 20;
	int whichenemy = 0;
	int vel = 1;
	SoundEffect kill = new SoundEffect(new File("kill.wav"));
	
	final static int PACMAN_GHOST = 0;
	final static int MUSHROOM_DUDE = 1;
	final static int MONSTER3 = 2;
	final static int MONSTER4 = 3;
	
	public SmallEnemy(int xpos, int ypos, int type) {
		super(xpos, ypos, width, height, 1, true, 50);
		whichenemy = type;
	}

	@Override
	public void update() {
		if(!isDead()){
			if(xpos > Utils.CANVAS_WIDTH-50 || xpos < 50){
				vel = -vel;
				ypos+=10;
			}
			xpos+=vel;
			collisionBox.setRect(xpos, ypos, width, height);
			for(Enemy b : GameFrame.entities)
				if(collisionBox.intersects(b.collisionBox)){
					setDead(true);
					b.setDead(true);
					kill.music();
					GameFrame.alive.remove(this);
					Utils.points+=1;
				}
		}else{
			xpos = -100;
			ypos = -100;
			collisionBox.setRect(0,0,0,0);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		if(!isDead()){
			switch(whichenemy){
			case PACMAN_GHOST :
				g.setColor(Color.WHITE);
				g.fillRect(xpos+10, ypos, 20, 4);
		    	g.fillRect(xpos+5, ypos+4, 30, 4);
		    	g.fillRect(xpos, ypos+8, 40, 4);
		    	g.fillRect(xpos, ypos+12, 40, 4);
		    	for(int i = 0; i < 5; i++)
		    		g.fillRect(xpos+i*9, ypos+16, 4, 2);
		    	break;
			case MUSHROOM_DUDE :
				g.setColor(Color.CYAN);
				g.fillRect(xpos+15, ypos, 10, 4);
		    	g.fillRect(xpos+10, ypos+4, 20, 4);
		    	g.fillRect(xpos+5, ypos+8, 30, 4);
		    	g.fillRect(xpos, ypos+12, 40, 4);
		    	g.fillRect(xpos+5, ypos+16, 30, 4);
		    	break;
			case MONSTER3 :
				g.setColor(Color.PINK);
				g.fillRect(xpos+10, ypos, 4, 4);
		    	g.fillRect(xpos+25, ypos, 4, 4);
		    	
		    	g.fillRect(xpos+5, ypos+4, 30, 4);
		    	g.fillRect(xpos, ypos+8, 40, 4);
		    	
		    	g.fillRect(xpos, ypos+12, 40, 4);
		    	g.fillRect(xpos, ypos+16, 40, 4);
		    	break;
			case MONSTER4 :
				g.setColor(Color.MAGENTA);
				g.fillRect(xpos+10, ypos, 4, 4);
		    	g.fillRect(xpos+25, ypos, 4, 4);
		    	
		    	g.fillRect(xpos+5, ypos+4, 30, 4);
		    	g.fillRect(xpos, ypos+8, 40, 4);
		    	
		    	g.fillRect(xpos+5, ypos+12, 4, 4);
		    	g.fillRect(xpos+5, ypos+16, 4, 4);
		    	g.fillRect(xpos+30, ypos+16, 4, 4);
		    	g.fillRect(xpos+30, ypos+12, 4, 4);
		    	break;
			}
		}
	}

}
