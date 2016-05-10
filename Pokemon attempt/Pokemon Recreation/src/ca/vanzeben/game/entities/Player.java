package ca.vanzeben.game.entities;

import java.awt.event.KeyEvent;

import ca.vanzeben.game.GAMESTATE;
import ca.vanzeben.game.Game;
import ca.vanzeben.game.InputHandler;
import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.level.Level;
import ca.vanzeben.game.level.MapInterpreter;
import ca.vanzeben.game.level.PLACE;
import ca.vanzeben.game.level.tiles.Tile;

public class Player extends Mob{

	private InputHandler input;
	private int row;
	private int col;
	private boolean jumping;
	
	public Player(Level level,int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
		this.normalWalkSpeed = 12;
		this.walkSpeed = 12;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		//2-11 for x and 4-0 for y
		int xMin = -3;
		int yMin = 3;
		
		int xMax = 3;
		int yMax = 7;
		
		for(int x = xMin; x < xMax; x++){
			if(isSolidTile(xa,ya,x,yMin )){

				return true;
			}
		}
		for(int x = xMin; x < xMax; x++){
			if(isSolidTile(xa,ya,x,yMax )){

				return true;
			}
		}
		
		for(int y = yMin; y < yMax; y++){
			if(isSolidTile(xa,ya,xMin,y )){

				return true;
			}
		}
		for(int y = yMin; y < yMax; y++){
			if(isSolidTile(xa,ya,xMax,y )){
				return true;
			}
		}
		
		
		return false;
	}


	
	private boolean isGrass(Tile tile){
		return tile.getID() == 39;
		
	}
	
	protected boolean isLedge(Tile tile){
		boolean isLedge = false;
		int[][] ledgeMap = MapInterpreter.createMap("resources/Ledges.txt");
		for(int i =0; i < ledgeMap.length;i++){

			if(tile.getID() == ledgeMap[i][movingDir]){
				isLedge = true;
			}

		}
		
		
		return isLedge;
	}
	
	private void attemptToInteract(int x, int y){
		
		
		
		if(movingDir == 0){
			y+= 1;
		
		
			int[] temp = level.isInteractable(x>>4, y>>4);
			
			if(temp[0] == 0)return;
			
			Game.state = GAMESTATE.Listening;
			if(temp[1] == 0){
				//signStuff
				level.textBox.setString(temp[2] >= level.signPhrases.length? level.signPhrases[0] + ":" + temp[2]: level.signPhrases[temp[2]]);
				level.textBox.setVisible(true);
				
			}
		}
		
		
	}
	
	private void checkInteractable(int xa, int ya){
		
		Tile oldTile = level.getBottomTiles(x, y);
		Tile newTile = level.getBottomTiles(x+xa, y+ya);
		

		int[] temp = level.isInteractable(x>>4, y>>4);

		if(temp[0] == 0)return;
		
		System.out.println(temp[1]);
		
		 if(temp[1] == 1){
			//doorStuff
//			 if(Game.Place != PLACE.PalletTown)
				 level.generateLevel(temp[2] + 1);
//			 else{
//				 level.goOutside();
//			 }
		}else if(temp[1] == 2){
			//Carpet Stuff
			
			
		}else if(temp[1] == 3){
			//Stair Stuff
			if(movingDir == 3){
				System.out.println("YAY");
				level.changeUpstairs();
				level.generateLevel(level.getCurrentLevel());
			}
		}
	}
	
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		int moveX = 0;
		int moveY = 0;
		
		
		if(!jumping){
			
			if(input.isPressed(KeyEvent.VK_W))moveY--;
			if(input.isPressed(KeyEvent.VK_S))moveY++;
			if(input.isPressed(KeyEvent.VK_A))moveX--;
			if(input.isPressed(KeyEvent.VK_D))moveX++;
			
		}else{
			
			moveY--;
			
		}
		
		
		
		if(Math.abs(moveY) > 0 && Math.abs(moveX) > 0){
			int recent = input.getMostRecent();
			if(recent == KeyEvent.VK_W || recent == KeyEvent.VK_S)moveX = 0;
			if(recent == KeyEvent.VK_A || recent == KeyEvent.VK_D)moveY = 0;
		}
		if(moveX ==0 && moveY == 0)isMoving = false;
		checkInteractable(moveX, moveY);
		move(moveX, moveY);
		

		if(input.isPressed(KeyEvent.VK_ENTER) && level.textBox.isFinished()){
			attemptToInteract(x,y);
		}
		
	}

	@Override
	public void render(Screen screen) {
		int colWidth = 4;
		row = movingDir;
		if(level != null){
			Tile thisTile = level.getBottomTiles((this.x)>>4, (this.y)>>4);
			
			
			if(walkSpeed == 0)System.out.println("BOOP");
			col = (numSteps/walkSpeed)%colWidth;
	
			if(!isMoving) col = 1;
			int tileID = row*colWidth + col;
			int modifier = 16*scale;
			int xOffset = x - modifier/2;
			int yOffset = y - modifier/2;
			screen.render(Screen.PLAYERSHEET, xOffset, yOffset, tileID);
			
			if(isGrass(thisTile) && (!(numSteps %12 == 0|| numSteps %12 == 1/*y %8 ==0 || y%8 == 1*/))){
				screen.render(Screen.SPRITESHEET, xOffset, yOffset, 322);
			}
		}
		
	}
	
	
	
	
	
	
}
