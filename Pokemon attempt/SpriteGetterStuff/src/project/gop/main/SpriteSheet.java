package project.gop.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage spriteSheet;
	private int gap;
	private int eachWidth;
	public SpriteSheet(){
		
		
		
	}
	
	public void setSpriteSheet(BufferedImage spriteSheet, int gaps, int width) {
		this.spriteSheet = spriteSheet;
		this.gap = gaps;
		this.eachWidth = width;
	}
	
	private BufferedImage getTile(int xTile, int yTile, int width, int height){
		BufferedImage sprite = spriteSheet.getSubimage(xTile, yTile, width, height);
		return sprite;
	}
	
	public BufferedImage getTile(int row, int col){
		int x = col*(eachWidth+gap);
		int y = row*(this.eachWidth + gap);
		
		return getTile(x, y, eachWidth, eachWidth);
	}
	
}
