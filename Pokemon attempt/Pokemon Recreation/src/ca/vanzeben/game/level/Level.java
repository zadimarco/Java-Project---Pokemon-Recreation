package ca.vanzeben.game.level;

import java.io.File;
import java.util.ArrayList;

import ca.vanzeben.game.Game;
import ca.vanzeben.game.InputHandler;
import ca.vanzeben.game.entities.Entity;
import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.gui.TextBox;
import ca.vanzeben.game.level.tiles.Tile;

public class Level {
	
	//FIELDS
	
	private int[] tiles;
	private int[] topTiles;
	private int[] interactables;
	private int width;
	private int height;
	private boolean indoors;
	private boolean upstairs;
	private int currentLev;
	
	public TextBox textBox;
	public static String signPhrases[];
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	
	//CONTRUCTOR
	
	public Level(int width, int height, InputHandler handler){
		tiles = new int[width*height];
		topTiles = new int[width*height];
		interactables = new int[width*height];
		this.width = width;
		this.height = height;
		textBox = new TextBox(false, 50,50,handler);
		
		
		int[][] information = MapInterpreter.createMap("resources/Maps/" + TextInterpreter.getPhrases("resources/Places.txt")[1] + "/Collisions.txt");

		Tile.loadTiles(information);
		upstairs = false;
		indoors = false;
		this.generateLevel(1);
	}
	
	
	
	//FUNCTIONS
	
	public void render(Screen screen, int xOffset, int yOffset){
		

		if(xOffset < 0)xOffset = 0;
		if(yOffset < 0)yOffset = 0;
		
		if(xOffset > (width<<4) - screen.getWidth()) xOffset = (width<<4) - screen.getWidth();

		if(yOffset > (height<<4) - screen.getHeight()) yOffset =(height<<4) - screen.getHeight();
		
		
		renderTiles(screen,xOffset,yOffset);
		renderEntities(screen);
		renderTopTiles(screen,xOffset,yOffset);
		textBox.render(screen, xOffset, yOffset);
	}
	
	public void generateLevel(int mapType){
		//put Level in here
//		tiles[0] = (byte)Tile.GRASS.getID();
		int playerSpawn = 0;

		if(!indoors){
			switch(Game.Place){
			case RedHouse:
				playerSpawn = 1;
				
				break;
			
			case BlueHouse:
				playerSpawn = 2;
				
				break;
			
			case OakLab:
				playerSpawn = 3;
				
				break;
			
			}
			indoors = true;
			Game.Place = PLACE.PalletTown;

		}
		currentLev = mapType;
		
		String[] places = TextInterpreter.getPhrases("resources/Places.txt");
		
		
		
		String place =places[mapType] +(new File("resources/Maps/" + places[mapType] + "/Upstairs/Bottom.txt").exists() && upstairs? "/Upstairs/": "") ;
		int[][] map = MapInterpreter.createMap("resources/Maps/" + place + "/Bottom.txt");
		int[][] topMap = MapInterpreter.createMap("resources/Maps/" + place+ "/Top.txt");
		signPhrases = TextInterpreter.getPhrases("resources/TextPhrases/Signs.txt");
		
		
		width = map.length;
		height = map[0].length;
		

	
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tiles[x + y*width] = Tile.tiles[0].getID();
				topTiles[x + y*width] = Tile.tiles[9].getID();
			}
		}
		
		int[] numFound = new int[4];
		for(int i = 0; i < numFound.length; i++){
			numFound[i] = 1;
		}
		for(int y = 0; y < map[0].length; y++){
			for(int x = 0; x < map.length; x++){
				if(map[x][y] == 4){
					
					interactables[x + y*width] = numFound[0];
					numFound[0]++;
				}else if(map[x][y] == 231){
					interactables[x+y*width] = numFound[1]+500;
					if(numFound[1] - 500 == playerSpawn){
						for(Entity e: entities){
							if(e.getName().equals("Player")){
								e.setX((x<<4) + 8);
								e.setY((y<<4) + 24);
							}
						}
					}
					numFound[1]++;
				}else if(map[x][y] == 690){
					interactables[x+y*width] = numFound[2] + 1000;
					numFound[2]++;
					
				}else if(map[x][y] == 842 || map[x][y] == 804){
					interactables[x+y*width] = numFound[3] + 1500;
					numFound[3]++;
						for(Entity e: entities){
							if(e.getName().equals("Player")){
								
									e.setX((x<<4) - 8);
								
								
							}
						}
					
				}
				tiles[x + y*width] = map[x][y];
				topTiles[x + y*width] = topMap[x][y];
			}
		}
		
	}
	
	public void tick(){
		for(Entity e: entities){
			e.tick();
		}
		
		
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset){
		
		
		
		
		screen.setOffset(xOffset, yOffset);
		
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
//				if(getTile(x,y) != null)
					getTile(tiles, x,y).render(screen, this, x<<4,y<<4);
			}
		}
		
	}
	public void renderTopTiles(Screen screen, int xOffset, int yOffset){
		

		
		
		screen.setOffset(xOffset, yOffset);
		
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
//				if(getTile(x,y) != null)
					getTile(topTiles, x,y).render(screen, this, x<<4, y<<4);
			}
		}
		
	}
	
	public void renderEntities(Screen screen){
		for(Entity e: entities){
			e.render(screen);
		}
	}
	
	private Tile getTile(int[] tiles, int x, int y){
		if(0 > x || x >= width || 0 > y || y >= height)return Tile.tiles[0];
		return Tile.tiles[tiles[x + y*width]];
		
		
	}
	
	public Tile getBottomTiles(int x, int y){
		if(0 > x || x >= width || 0 > y || y >= height)return Tile.tiles[9];
		return Tile.tiles[tiles[x + y*width]];
	}
	
	public int[] isInteractable(int x, int y){
		if(0 > x || x >= width || 0 > y || y >= height)return new int[3];
		int[] isInteractableAndType = new int[3];
		isInteractableAndType[0] = interactables[x + y*width] !=0? 1:0;
		isInteractableAndType[1] = interactables[x+y*width]/500;
		isInteractableAndType[2] = interactables[x+y*width] - interactables[x+y*width]/500*500;
		return isInteractableAndType;
	}
	
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity){
		entities.remove(entity);
	}
	
	public void changeUpstairs(){
		upstairs = !upstairs;
	}
	
	public int getCurrentLevel(){
		
		return currentLev;
	}
	
	
	public void goOutside(){
		generateLevel(1);
	}
	
	
	
}
