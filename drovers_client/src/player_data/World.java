package player_data;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import code.Code;
import client.State;
import GUI.AreaMapMenu;

public class World{
	public static Area_Map areaMap1;
	public static Area_Map areaMap2;
	public static WorldMap worldMap;
	public static HashMap<String, Sprite> texture_set;
	public static Player playerData;
	public static PlayersOnline playersOnline;
	public static Squad squad;
	public static Code unit1;
	public static Code unit2;
	public static Code unit3;
	
	public World(){
		// Player
		squad = new Squad();
		
		// GeoData
		areaMap1 = new Area_Map();
		areaMap2 = new Area_Map();
		worldMap = new WorldMap();
		playerData = new Player();
		playersOnline = new PlayersOnline();
		
		// maps
		texture_set = new HashMap<String, Sprite>();
		load_textures();
		
		// code
		unit1 = new Code();
		unit2 = new Code();
		unit3 = new Code();
	}

	public Sprite getSprite(String path)
	{
	    BufferedImage sourceImage = null;
	    try {
	        java.net.URL url = this.getClass().getClassLoader().getResource(path);
	        sourceImage = ImageIO.read(url);
	    } 
	    catch (IOException e){
	        e.printStackTrace();
	    }
	    Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));    
	    return sprite;
	}
	
	public void load_textures(){
		texture_set.put("char", getSprite("char.png"));
		texture_set.put("null", getSprite("null.png"));
		texture_set.put("grass1", getSprite("grass.png"));
		texture_set.put("grass2", getSprite("grass2.png"));
		texture_set.put("grass3", getSprite("grass3.png"));
		texture_set.put("dirt1", getSprite("dirt1.png"));
		texture_set.put("dirt2", getSprite("dirt2.png"));
		texture_set.put("dirt3", getSprite("dirt3.png"));
		texture_set.put("rock1", getSprite("rock1.png"));
		texture_set.put("rock2", getSprite("rock2.png"));
		texture_set.put("rock3", getSprite("rock3.png"));
		texture_set.put("tree1", getSprite("tree1.png"));
		texture_set.put("tree2", getSprite("tree2.png"));
		texture_set.put("tree3", getSprite("tree3.png"));
		texture_set.put("tree4", getSprite("tree4.png"));
		texture_set.put("stone1", getSprite("stone1.png"));
		texture_set.put("stone2", getSprite("stone2.png"));
		texture_set.put("stone3", getSprite("stone3.png"));
		texture_set.put("water1", getSprite("water1.png"));
		
		// Fields
		texture_set.put("inputString1", getSprite("inputString1.png"));
		texture_set.put("chat", getSprite("chat.png"));
		// Buttons
		texture_set.put("buttonLogin", getSprite("loginButton.png"));
		texture_set.put("buttonExit", getSprite("exitButton.png"));
		// Buttons Pressed
		texture_set.put("buttonLoginPressed", getSprite("loginButtonPressed.png"));
		texture_set.put("buttonExitPressed", getSprite("exitButtonPressed.png"));
		
		//other
		texture_set.put("gear1", getSprite("gear1.png"));
		texture_set.put("gear2", getSprite("gear2.png"));
		texture_set.put("logo", getSprite("logo.png"));
		
		texture_set.put("cursor", getSprite("cursor.png"));
		texture_set.put("cursorSelected", getSprite("cursorSelected.png"));
		texture_set.put("robotSelected", getSprite("roboSelect.png"));
		
		
		texture_set.put("art", getSprite("art.png"));
		texture_set.put("sturm", getSprite("sturm.png"));
		texture_set.put("scout", getSprite("scout.png"));
		
		// Code
		texture_set.put("codeMain", getSprite("codeMain.png"));
		texture_set.put("codeEvent", getSprite("codeEvent.png"));
		texture_set.put("codeAction", getSprite("codeAction.png"));
		texture_set.put("codeNull", getSprite("codeNull.png"));
	}
	
	public static Image getTexture(int type){
		// all types and codes see at player_data/WorldMap/WorldMapNode.class
		switch(type){
			case 0:
				// null
				return texture_set.get("null").getImage();
			case 1:
				// grass
				return texture_set.get("grass1").getImage();
			case 2:
				// forest
				return texture_set.get("tree1").getImage();
			case 3:
				// deep forest
				return texture_set.get("null").getImage();
			case 4:
				// rocks
				return texture_set.get("dirt1").getImage();
			case 5:
				// inputString-1
				return texture_set.get("inputString1").getImage();
			case 6:
				// loginButton
				return texture_set.get("buttonLogin").getImage();
			case 7: 
				// exitButton
				return texture_set.get("buttonExit").getImage();
			case 8: 
				// loginButtonPressed
				return texture_set.get("buttonLoginPressed").getImage();
			case 9: 
				// exitButtonPressed
				return texture_set.get("buttonExitPressed").getImage();
			case 10:
				// LoginMenu 'Gear'
				return texture_set.get("gear1").getImage();
			case 11:
				// LoginMenu 'Gear'
				return texture_set.get("gear2").getImage();
			case 12:
				// LoginMenu 'Logo'
				return texture_set.get("logo").getImage();
			case 13:
				// chat
				return texture_set.get("chat").getImage();
			default:
				return texture_set.get("null").getImage();
		}
	}
	
	public static Image getTile(int type){
		switch(type){
			case 0:
				return texture_set.get("grass1").getImage();
			case 1:
				return texture_set.get("grass2").getImage();
			case 2:
				return texture_set.get("tree1").getImage();
			case 3:
				return texture_set.get("dirt1").getImage();
			case 4:
				return texture_set.get("tree4").getImage();
			case 5:
				return texture_set.get("stone1").getImage();
			case 6:
				return texture_set.get("rock1").getImage();
			case 7:
				return texture_set.get("water1").getImage();
			default:
				return texture_set.get("null").getImage();
		}
	}
	
	public static void mergeAreas(){
		int [][] map = null;
		switch(AreaMapMenu.topology){
			case "1": // UP
				map = new int[areaMap1.size_x][areaMap1.size_y*2];
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap2.map[i][j];
					}
				}
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = areaMap1.size_x, z = 0; j < (areaMap1.size_y*2); ++j, ++z){
						map[i][j] = areaMap1.map[i][z];
					}
				}
				areaMap1.map = map;
				areaMap1.size_x = areaMap1.size_x;
				areaMap1.size_y = areaMap1.size_y*2;
				State.state = "areaMap";
				break;
			case "2": // DOWN
				map = new int[areaMap1.size_x][areaMap1.size_y*2];
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap1.map[i][j];
					}
				}
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = areaMap1.size_x, z = 0; j < (areaMap1.size_y*2); ++j, ++z){
						map[i][j] = areaMap2.map[i][z];
					}
				}
				areaMap1.map = map;
				areaMap1.size_x = areaMap1.size_x;
				areaMap1.size_y = areaMap1.size_y*2;
				State.state = "areaMap";
				break;
			case "3":
				map = new int[areaMap1.size_x*2][areaMap1.size_y];
				
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap2.map[i][j];
					}
				}
				for(int i = areaMap1.size_x, z = 0; i < (areaMap1.size_x*2); ++i, ++z){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap1.map[z][j];
					}
				}
				areaMap1.map = map;
				areaMap1.size_x = areaMap1.size_x*2;
				areaMap1.size_y = areaMap1.size_y;
				State.state = "areaMap";
				break;
			case "4":
				map = new int[areaMap1.size_x*2][areaMap1.size_y];
				
				for(int i = 0; i < areaMap1.size_x; ++i){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap1.map[i][j];
					}
				}
				for(int i = areaMap1.size_x, z = 0; i < (areaMap1.size_x*2); ++i, ++z){
					for(int j = 0; j < areaMap1.size_y; ++j){
						map[i][j] = areaMap2.map[z][j];
					}
				}
				areaMap1.map = map;
				areaMap1.size_x = areaMap1.size_x*2;
				areaMap1.size_y = areaMap1.size_y;
				State.state = "areaMap";
				
				State.state = "areaMap";
				break;
			default:
		}
	}
	public static void drawArea(){
		
	}
}