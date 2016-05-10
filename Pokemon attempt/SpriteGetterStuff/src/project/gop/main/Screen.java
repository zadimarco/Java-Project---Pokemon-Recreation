package project.gop.main;

import java.util.ArrayList;

public class Screen {

	public static final int PLAYERSHEET = 0,SPRITESHEET = 1,TILESET = 2;
	
	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	
	private int[] tiles;
	private int[] pixels;
	
	private int xOffset;
	private int yOffset;
	
	private int width;
	private int height;
	
	private ArrayList<SpriteSheet> sheet = new ArrayList<SpriteSheet>();
	
	public Screen(int width, int height, int[] pixels){
		
		tiles = new int[MAP_WIDTH*MAP_WIDTH];
		
		this.height = height;
		this.width = width;
//		this.sheet.add(new SpriteSheet().setSpriteSheet(ImageLoader.loadImageFrom(classfile, "/PlayerSprite.png"), gaps, width););
		this.sheet.add(new SpriteSheet());
		this.sheet.add(new SpriteSheet());

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
	

	
	
	
}
