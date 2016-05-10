package ca.vanzeben.game.gui;

import java.awt.event.KeyEvent;

import ca.vanzeben.game.GAMESTATE;
import ca.vanzeben.game.Game;
import ca.vanzeben.game.InputHandler;
import ca.vanzeben.game.gfx.Screen;

public class TextBox {

	private int x,y, onScreenX, onScreenY;
	private InputHandler input;
	
	private long startTime;
	private long timer;
	private long delay;
	
	
	private long playerStart;
	private long playerTimer;
	
	private boolean sudden;
	
	private boolean finishedScrolling;
	private boolean finished;
	private int textPlace;
	private int endPlace;
	private String s;
	private int printSpeed;
	private boolean visible;
	
	private int width;
	private int height;
	
	public TextBox(boolean sudden, int width, int height,InputHandler handler){
		
		this.width = Screen.screenWidth - 10;
		this.height = 40;
		this.x = 0;
		this.y = 0;
		this.input = handler;
		onScreenX = 0;
		onScreenY = Screen.screenHeight - height;
		s = "DISPLAYED BEFORE TEXT WAS READY";
		delay = 500;
		printSpeed = 50;
		this.sudden = sudden;
		
	}
	
	public void tick(){
		if(!sudden && input.isPressed(KeyEvent.VK_ENTER)){
			printSpeed = 30;
		}else if(!sudden){
			printSpeed = 50;
		}else{
			printSpeed = 0;
		}
		
		if(finished && input.isPressed(KeyEvent.VK_ENTER)){
			this.setVisible(false);
			startTime = System.nanoTime();
			Game.state = GAMESTATE.Playing;
			finished = false;
			finishedScrolling = false;
		}else if(finishedScrolling && input.isPressed(KeyEvent.VK_ENTER)){
			finishedScrolling = false;
			endPlace = textPlace-1;
			
		}
	}
	
	
	public void render(Screen screen, int xOffset, int yOffset){
		if(visible){
			x = screen.getXOffset() + onScreenX;
			y = screen.getYOffset() + onScreenY;
			playerStart = System.nanoTime();
			for(int x = this.x;x <= width + this.x; x++){
				for(int y = this.y; y <= height + this.y; y++){
					int screenPart = 4;
					if(x == this.x && y == this.y){
						screenPart = 0;
						
					}else if(x == this.x && y == height + this.y){
						screenPart = 6;
					}else if(x == width + this.x  && y == this.y){
						screenPart = 2;
					}else if(x == width + this.x && y == height + this.y){
						
						screenPart = 8;
					}else if(x == this.x){
						
						screenPart = 3;
					}else if(x == width + this.x){
						screenPart = 5;
					}else if(y == this.y){
	
						screenPart = 1;
					}else if(y == height + this.y){
	
						screenPart = 7;
					}
					screen.render(Screen.TEXTBOXSPRITE, x, y, screenPart);
					if(x == this.x && y == this.y + height){
						x+=7;
					
					}
					if(y == this.y ){
						y+=7;
					}
				
				}
				
			}

			int y =  this.y + 10;
			int x = this.x + 10 ;	
			timer = System.nanoTime();
			long elapsed = (timer - startTime)/1000000;
			String s = this.s.substring(endPlace, textPlace);
			for(int i = 0; i < s.length(); i++){
				if(x > this.x + width - 8 || (i+1 < s.length() && s.substring(i, i+2).equals("  "))){
					
					y+=16;
					x = this.x + 10;
					if(s.charAt(i) == ' '){
						i++;
						if(i < s.length() && s.charAt(i) == ' ')i++;
						if(i >= s.length()){
							break;
						}
						
					}
				}
				if(y > height + this.y - 8){
					finishedScrolling = true;
					break;
				}

				screen.render(Screen.FONTSPRITE, x, y , -32 + s.charAt(i));
				x += 8;
				
				
			}
			if(elapsed > printSpeed && !finishedScrolling){
				startTime = System.nanoTime();
				textPlace++;
				finished = textPlace >= this.s.length();
				finishedScrolling = finished|| finishedScrolling;
			}
			if(finishedScrolling && !finished){
				screen.render(Screen.FONTSPRITE, this.x + width-8, this.y+height - 8, 95);
			}
			
		}else{
			startTime = System.nanoTime();
			timer = System.nanoTime();
			finished = false;
			textPlace = 0;
			endPlace = 0;
		}
		
		
	}
	
	
	public void setVisible(boolean i){visible = i;}
	public void setString(String s){this.s = s;}
	
	public boolean isFinished(){
		
		playerTimer = System.nanoTime();
		long elapsed = (playerTimer - playerStart)/1000000;
		return elapsed > delay;
		
	}
	
	
}
