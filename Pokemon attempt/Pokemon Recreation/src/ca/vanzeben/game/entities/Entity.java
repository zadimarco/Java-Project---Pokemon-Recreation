package ca.vanzeben.game.entities;

import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.level.Level;

public abstract class Entity {
	
	//FIELDS
	
	protected int x, y;
	protected Level level;
	protected String name;
	
//	public enum ID(){
//		
//		Player,
//		Gary,
//		Oak;
//		
//	}
	
	
	//CONSTRUCTORS
	
	public Entity(Level level){
		
		init(level);
		
		
	}
	
	
	//FUNCTIONS
	
	public final void init(Level level){
		this.level = level;
	}
	
	
	public int getX(){return x;}
	public int getY(){return y;}
	public String getName(){return name;}
	
	public void setX(int i ){x = i;}
	public void setY(int i){y = i;}
	
	
	public abstract void tick();
	public abstract void render(Screen screen);
	
}
