package ca.vanzeben.game.entities;

import ca.vanzeben.game.level.Level;
import ca.vanzeben.game.level.tiles.Tile;

public abstract class Mob extends Entity{
	
	protected int speed, numSteps = 0, movingDir = 1, scale = 1;
	protected boolean isMoving;
	protected int walkSpeed;
	protected int normalWalkSpeed;
	protected boolean collided;
	
	protected int startingX;
	protected int startingY;
	protected int currentX, currentY;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.startingX = x;
		this.startingY = y;
		this.x = x;
		this.y = y;
		this.name = name;
		this.speed = speed;
		
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean Teleporter(int xa, int ya){
		
		if(level == null)return false;
		Tile lastTile = level.getBottomTiles((this.x)>>4, (this.y)>>4);
		Tile newTile = level.getBottomTiles((this.x + xa*16)>>4, (this.y + ya*16)>>4);
		return !lastTile.equals(newTile) && newTile.isSolid();
		
		
	}
	public void move(int xa, int ya){
		if(xa != 0 && ya != 0){
			System.out.println("Booo");
			move(xa, 0);
			move(0, ya);
			numSteps-= 1;
			return;
		}
		
		//Was tile based movement but didnt work well
		boolean stillMoving = false;//!collided && Math.abs(currentX - startingX) <= 16 && Math.abs(currentY - startingY) <= 16;
		if(!stillMoving){
			
			if(ya < 0)
				movingDir = 0;
			if(ya > 0)
				movingDir = 1;
			if(xa < 0)
				movingDir = 2;
			if(xa > 0)
				movingDir = 3;
			
			if(xa != 0 || ya != 0){
				numSteps+= 1;
				isMoving = true;
			}
			if(!hasCollided(xa,ya) && isMoving){
				x+=xa*speed;
				y+=ya*speed;
				walkSpeed = normalWalkSpeed;
				
				startingX = x;
				startingY = y;
				currentX = x;
				currentY = y;
			}else{
				walkSpeed = 2*normalWalkSpeed;
			}
			collided = false;
		}else{
			
			int moveY = 0;
			int moveX = 0;
			
			if(movingDir == 0){
				moveY = -speed;
			}else if(movingDir == 1){
				moveY =  speed;
			}else if(movingDir == 2){
				moveX =  -speed;
			}else if(movingDir == 3){
				moveX =  speed;
			}
			if(!hasCollided(moveX/speed,moveY/speed)){
				y+=moveY;
				x+=moveX;
				collided = false;
				isMoving = true;

			}else{
				collided = true;
			}
			
			numSteps+= 1;
			currentX = x;
			currentY = y;
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya);
	protected abstract boolean isLedge(Tile tile);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		if(level == null)return false;
//		Tile lastTile = level.getBottomTiles((this.x + x)>>4, (this.y + y)>>4);
		Tile newTile = level.getBottomTiles((this.x + x + xa)>>4, (this.y + y + ya)>>4);
		if((name.equals("Player") && isLedge(newTile))){
			return false;
		}else{
			return (newTile.isSolid());
		}
	}
	

}
