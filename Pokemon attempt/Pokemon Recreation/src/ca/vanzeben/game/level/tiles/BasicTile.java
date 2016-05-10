package ca.vanzeben.game.level.tiles;

import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.level.Level;

public class BasicTile extends Tile{
	
	protected int tileId;
	
	
	public BasicTile(int id, int tileId, boolean solid){
		super(id, solid, false, false);
		this.tileId = tileId;
	}


	@Override
	public void render(Screen screen, Level level, int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		screen.render(Screen.TILESET, x, y, tileId);
		
	}
	
	public void interact(Level level){   }
	
}