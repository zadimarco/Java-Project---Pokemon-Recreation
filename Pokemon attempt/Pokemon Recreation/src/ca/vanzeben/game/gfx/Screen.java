package ca.vanzeben.game.gfx;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ca.vanzeben.game.Game;
import ca.vanzeben.game.InputHandler;


public class Screen {

	public static final int PLAYERSHEET = 0,SPRITESHEET = 1,TILESET = 2, FONTSPRITE = 3, TEXTBOXSPRITE = 4;
	
	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public static int screenWidth;
	public static int screenHeight;
	
	private int[] tiles;
	private int[] pixels;
	
	private int xOffset;
	private int yOffset;
	
	private int width;
	private int height;
	
	private static ArrayList<SpriteSheet> sheet = new ArrayList<SpriteSheet>();
	
	public Screen(int width, int height, int[] pixels){
		
		tiles = new int[MAP_WIDTH*MAP_WIDTH];
		
		this.height = height;
		this.width = width;
		screenHeight = height;
		screenWidth = width;
		this.sheet.add(new SpriteSheet(16,0,0,"/PlayerSprite.png"));
		this.sheet.add(new SpriteSheet(16,2,2,"/Spritesheet.png"));
		this.sheet.add(new SpriteSheet(16,0,0,"/TileSet.png"));
		this.sheet.add(new SpriteSheet(8,0,0,"/FontSprite.png"));
		this.sheet.add(new SpriteSheet(8,0,0,"/TextBoxSprite8x8.png"));

		this.pixels = pixels;
//		pixels = new int[width*height];
		
		
		
		
	}
	
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getXOffset(){return xOffset;}
	public int getYOffset(){return yOffset;}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
//	public int[] getPixels(){return pixels;}
	
	
	public void tick(){
		
		
		
	}
	

	
	public void render(int tileSet, int xPos, int yPos, int tile){
		xPos -= xOffset;
		yPos -= yOffset;
		
		int size = sheet.get(tileSet).getSize();
		int spritesPerRow = (sheet.get(tileSet).getWidth()+ sheet.get(tileSet).getGapWidth())/(size + sheet.get(tileSet).getGapWidth());
		
		int xTile= tile%spritesPerRow;
		int yTile = tile/spritesPerRow;
		
		int tileOffset = (xTile*(size + sheet.get(tileSet).getGapWidth())) + (yTile*(size + sheet.get(tileSet).getGapHeight()))*sheet.get(tileSet).getWidth();
	
		for(int y = 0; y < size; y++){
			
			if(y + yPos < 0 || y + yPos >= height)continue;

			int ySheet = y;
			for(int x = 0; x < size; x++){
				
				if(x + xPos < 0 || x + xPos >= width)continue;

				int xSheet = x;
				
				int col = sheet.get(tileSet).getPixels()[xSheet + ySheet*sheet.get(tileSet).getWidth() + tileOffset];
				
				if((col>>24&0xFF) != 0)
					pixels[x+xPos + (y + yPos)*getWidth()] = col;
				
			}
		}
	
	
	}
	
	
	public static int getSheetWidth(int sheetNum){
		return sheet.get(sheetNum).getWidth();
	}
	
	public static int getSheetHeight(int sheetNum){
		return sheet.get(sheetNum).getHeight();
	}
	
	
}
