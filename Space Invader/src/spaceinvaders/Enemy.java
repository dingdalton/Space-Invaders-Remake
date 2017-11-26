package spaceinvaders;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {

	private int hp = 0;
	private boolean canAttack = false;
	private boolean dead = false;
	private int pointsWorth = 0;
	private int collisionBoxW, collisionBoxH;
	Rectangle collisionBox;
	int xpos, ypos;
	
	public Enemy(int xpos, int ypos, int width, int height, int hp, boolean atk, int points){
		this.hp = hp;
		canAttack = atk;
		pointsWorth = points;
		this.xpos = xpos;
		this.ypos = ypos;
		this.collisionBoxW = width;
		this.collisionBoxH = height;
		collisionBox = new Rectangle(xpos, ypos, width, height);
	}
	
	public boolean canAttack(){
		return canAttack;
	}
	
	public void moveLeft(int amt){
		xpos-=amt;
	}
	
	public void moveRight(int amt){
		xpos+=amt;
	}
	
	public void moveDown(int amt){
		ypos+=amt;
	}
	
	public int getHP(){
		return hp;
	}
	
	public void setHP(int hp){
		this.hp = hp;
	}
	
	public void setDead(boolean deadornah){
		dead = deadornah;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public int getPoints(){
		return pointsWorth;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public void setCollisionBox(int x, int y, int x1, int y1){
		collisionBox = new Rectangle(x, y, x1, y1);
	}
}
