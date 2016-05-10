package ca.vanzeben.game.level;

import java.io.BufferedReader;
import java.io.FileReader;



public class MapInterpreter {
	
	
	
	private static int[][] map;
	
	private static int mapWidth;
	private static int mapHeight;
	
	public static int[][] createMap(String file){
		
		try{
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapWidth][mapHeight];
			
			String delimiters = "\\s+";
			for(int col = 0; col < mapHeight; col++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				
				
				for(int row = 0; row < mapWidth; row++){
					
					map[row][col] = Integer.parseInt(tokens[row]);
					if(map[row][col] < 0)map[row][col] = 9;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
		
	}
	

	
	
}
