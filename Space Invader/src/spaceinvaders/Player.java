package spaceinvaders;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class Player extends Enemy {
	
	static int width = 40; //TODO add actual dim once known
	static int height = 21;
	static boolean moving = false;
	static int dir = 0; //0 for left 1 for right
	SoundEffect laser = new SoundEffect(new File("laser.wav"));

	public Player(int xpos, int ypos) {
		super(xpos, ypos, width, height, 3, true, 0);
	}

	public void draw(Graphics g){
		g.setColor(Color.white);
    	g.fillRect(xpos, ypos+height-8, 40, 8);
    	g.fillRect(xpos+10, ypos+height-13, 20, 5);
    	g.setColor(Color.cyan);
    	g.fillRect(xpos+16, ypos+height-21, 8, 8);
	}
	
	public void shoot(){
		laser.music();
		GameFrame.entities.add(new Bullet(Utils.player.xpos+17, Utils.player.ypos));
	}

	@Override
	public void update() {
		if(moving){
			if(dir == 0 && xpos > 10)
				xpos-=4;
			else if(dir == 1 && xpos < Utils.CANVAS_WIDTH-55)
				xpos+=4;
		}
		collisionBox.setRect(xpos,ypos, width, height);
	}
}
