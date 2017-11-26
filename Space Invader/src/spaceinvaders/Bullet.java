package spaceinvaders;
import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Enemy{
	
	static int width = 5; //TODO add actual dim once known
	static int height = 15;
	int dir = 0;

	public Bullet(int xpos, int ypos) {
		super(xpos, ypos, width, height, 1, false, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		if(!isDead()){
			g.setColor(Color.RED);
			g.fillRect(xpos, ypos, 5, 15);
		}
	}

	@Override
	public void update() {
		if(ypos < -40)
			setDead(true);
		if(!isDead()){
			if(dir == 0)
				ypos-=4;
			else
				ypos+=4;
			collisionBox.setRect(xpos,ypos,10,10);
		}else
			collisionBox.setRect(0,0,0,0);
	}
}
