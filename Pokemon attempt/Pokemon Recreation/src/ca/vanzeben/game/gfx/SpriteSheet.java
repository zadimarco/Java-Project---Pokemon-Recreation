package ca.vanzeben.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public String path;
	public int width, height, gapWidth,gapHeight,size;

	public int[] pixels;
	private int[] alpha;
	
	
	public SpriteSheet(int size, int gapWidth,int gapHeight, String path){
		BufferedImage image = null;
		
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(image == null){
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.gapWidth = gapWidth;
		this.gapHeight = gapHeight;
		this.size = size;
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		alpha = new int[pixels.length];
		
	
		
	}
	
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int[] getPixels(){return pixels;}
	public int getGapWidth(){return gapWidth;}
	public int getGapHeight(){return gapHeight;}
	public int getSize(){return size;}
	
}
