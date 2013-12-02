package World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class World {
	public static HashMap<String, AreaMap> areaMaps;
	public static WorldMap worldMap;
	
	public World() throws FileNotFoundException, UnsupportedEncodingException{
		//Uncoment to generate new world map
		//generateWorld();
		
		worldMap = new WorldMap();
		areaMaps = new HashMap<String, AreaMap>();
		areaMaps.put("default", new AreaMap(10, 10));
	
		loadAreas();
	}
	
	public static void generateWorld() throws FileNotFoundException, UnsupportedEncodingException{
		int sizeX = 20;
		int sizeY = 20;
		int id = 0;
		
		PrintWriter out = new PrintWriter("maps\\allworld.map", "UTF-8");
		
		out.println(sizeX);
		out.println(sizeY);
	
		int max = 7;
		int min = 0;
		Random rand = new Random();
		
		// 1. 2. 3. 4. 5. 6. 7
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				int randomNum = rand.nextInt((max - min) + 1) + min;
				out.write(i+"_"+j+" "+id+" "+randomNum+" ");
				getnerateArea(i, j, randomNum);
				id++;
			}
			out.write("\n");
		}
		out.close();
	}
	public static void getnerateArea(int x, int y, int type) throws FileNotFoundException, UnsupportedEncodingException{
		int sizeX = 10;
		int sizeY = 10;
		
		PrintWriter out = new PrintWriter("maps\\" + x + "_" + y + ".map", "UTF-8");
		out.println(sizeX);
		out.println(sizeY);
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				out.print(type + " ");
			}
			out.println();
		}
		out.close();
	}
	
	public static void loadAreas(){
		for(int i = 0; i < WorldMap.sizeX; ++i){
			for(int j = 0; j < WorldMap.sizeY; ++j){
				try {
					Scanner in = new Scanner(new File("maps\\"+i+"_"+j+".map"));
					areaMaps.put(WorldMap.map[i][j].areaName, new AreaMap(in));
					in.close();
				} catch (FileNotFoundException e) {
					System.out.println("AreaMap name not found: maps\\" + WorldMap.map[i][j].areaName+".map");
				}
			}
		}
		
		System.out.println("---------------World---------------");
		System.out.println("Areas loaded: " + areaMaps.size());
		System.out.println("-----------------------------------");
	}
}