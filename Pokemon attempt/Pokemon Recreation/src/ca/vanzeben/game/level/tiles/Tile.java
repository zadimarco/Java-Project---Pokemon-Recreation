package ca.vanzeben.game.level.tiles;

import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[Screen.getSheetHeight(Screen.TILESET)*Screen.getSheetWidth(Screen.TILESET)];
	
	

	private static int width = Screen.getSheetWidth(Screen.TILESET);
	private static int height = Screen.getSheetWidth(Screen.TILESET);
	
	protected int x,y;
	protected int id;
	protected boolean solid;
	protected boolean emmitter;
	protected boolean foreground;
	
	public Tile(int id, boolean isSolid, boolean isEmmitter, boolean foreground){
		this.id =  id;
		if(tiles[id] != null)throw new RuntimeException("Duplicate Tile id on " + id);
		this.solid = isSolid;
		this.emmitter = isEmmitter;
		this.foreground = foreground;
		tiles[id] = this;
		
		
	}
	
	public static void loadTiles(int[][] info){
		
		
		for(int y = 0; y < height;y++){
			for(int x = 0; x < width; x++){
				if(x + y*width >= tiles.length)break;
				boolean solid = false;
				boolean interactable = false;
				
				for(int i = 0; i < info.length; i++){
					
					if(info[i][0] == x + y*width){
						solid = true;
					}
				}
//				for(int i = 0; i < info.length; i++){
//					if(info[i][1] == x + y*width){
//						interactable = true;
//					}
//				}
				
				
				
				
				
				if(!interactable)
					tiles[x + y*width] = new BasicTile(x + y*width, x + y*width, solid);
				else
					tiles[x + y*width] = new InteractableTile(x + y*width, x + y*width, solid);
			
			}
		}
		
		
		
	}
	
	
	
	public void setSolid(boolean i){solid = i;}
	
	public int getID(){return id;}
	public int getX(){return x;}
	public int getY(){return y;}
	
	
	public boolean isSolid(){return solid;}
	public boolean isEmmitter(){return emmitter;}
	public boolean isForeground(){return foreground;}
	
	public abstract void render(Screen screen, Level level, int x, int y);
	public abstract void interact(Level level);
	
}
