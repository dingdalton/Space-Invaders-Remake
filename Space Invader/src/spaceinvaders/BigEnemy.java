package spaceinvaders;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class BigEnemy extends Enemy{

	static int width = 40; //TODO add actual dim once known
	static int height = 20;
	int speed = 0;
	int whichenemy = 0;
	int vel = 1;
	SoundEffect kill = new SoundEffect(new File("kill.wav"));
	Color rand = new Color(255, 255, 255);
	
	final static int SKULL_DUDE = 0;
	final static int ALIEN_SHIP = 1;
	
	public BigEnemy(int xpos, int ypos, int type) {
		super(xpos, ypos, width, height, 3, false, 300);
		whichenemy = type;
	}
	
	public void draw(Graphics g){
		if(!isDead()){
			g.setColor(rand);
			if(whichenemy == SKULL_DUDE){
				g.fillRect(xpos+15, ypos, 10, 4);
		    	g.fillRect(xpos+10, ypos+4, 20, 4);
		    	g.fillRect(xpos, ypos+8, 40, 4);
		    	g.fillRect(xpos, ypos+12, 40, 4);
		    	g.fillRect(xpos+5, ypos+16, 4, 4);
		    	g.fillRect(xpos+30, ypos+16, 4, 4);
			}else{
				g.fillOval(xpos, ypos, 40, 20);
			}
		}
	}
	
	int counter = 0;

	@Override
	public void update() {
		counter++;
		if(!isDead()){
			if(counter >= 40){
				rand = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
				counter = 0;
			}
			if(xpos > Utils.CANVAS_WIDTH-50 || xpos < 50){
				vel = -vel;
				ypos+=10;
			}
			xpos+=vel;
			collisionBox.setRect(xpos, ypos, width, height);
			for(Enemy b : GameFrame.entities)
				if(collisionBox.intersects(b.collisionBox)){
					b.setDead(true);
					this.setHP(getHP()-1);
					if(this.getHP() <= 0){
						setDead(true);
						kill.music();
						GameFrame.alive.remove(this);
						Utils.points+=3;
					}
				}
		}
	}

}
