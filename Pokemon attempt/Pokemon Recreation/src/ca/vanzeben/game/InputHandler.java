package ca.vanzeben.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{

	private boolean[] keys = new boolean[127];
	private int recentKey = 0;
	
	public InputHandler(Game game){
		game.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W ||
				e.getKeyCode() == KeyEvent.VK_D ||
				e.getKeyCode() == KeyEvent.VK_S ||
				e.getKeyCode() == KeyEvent.VK_A)
			recentKey = e.getKeyCode();
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isPressed(int key){
		return keys[key];
	}

	public int getMostRecent(){
		return recentKey;
	}
	
}
