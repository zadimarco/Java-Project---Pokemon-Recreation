package ca.vanzeben.game.level;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextInterpreter {

	
	public static String[] getPhrases(String file){
		String[] phrases = new String[0];
		int height = 0;
		try{
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			height = Integer.parseInt(br.readLine());
			phrases = new String[height];
			
			String delimiters = "\\s+";
			for(int i = 0; i < height; i++){
				phrases[i] = br.readLine();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return phrases;
		
		
	}
	
}
