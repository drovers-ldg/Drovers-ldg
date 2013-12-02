package GUI;

import java.awt.Color;

import java.awt.Graphics;
import client.Mouse;
import player_data.Player;
import player_data.World;
import player_data.WorldMap;

public class CharacterMenu{
	public enum Way {NULL,
					UP, DOWN,
					LEFT, RIGHT,
					UPLEFT, UPRIGHT,
					DOWNLEFT, DOWNRIGHT};
	
	public static int nodeX;
	public static int nodeY;
	public static int mouseX;
	public static int mouseY;
	public static int actionShowMouseX;
	public static int actionShowMouseY;
	public static boolean showMapCursor;
	public static boolean showCodeCursor;
	public static boolean actionShow;
	public static int actionMenuCursorX;
	public static int actionMenuCursorY;
	public static Way actionWay;
	public static boolean actionMenuDetected;
	
	public static boolean unit1code;
	public static boolean unit2code;
	public static boolean unit3code;
	
	public static boolean unit1codeShow;
	public static boolean unit2codeShow;
	public static boolean unit3codeShow;
	
	public static boolean showArea;
	
	public CharacterMenu(){
		nodeX = 0;
		nodeY = 0;
		actionShowMouseX = 0;
		actionShowMouseY = 0;
		showMapCursor = false;
		
		// action menu
		actionShow = false;
		actionMenuCursorX = 0;
		actionMenuCursorY = 0;
		actionMenuDetected = false;
		actionWay = Way.NULL;
	}
	
	public static void draw(Graphics g){
		if(!unit1codeShow && !unit2codeShow && !unit3codeShow && !showArea){
			WorldMap.draw(g);
			World.playersOnline.draw(g);
			g.drawImage(World.texture_set.get("art").getImage(), Player.mapX*32+380, Player.mapY*32, 32, 32, null);
		}
		else{
			if(unit1codeShow){
				World.unit1.draw(g);
				World.unit1.drawMenu(g);
				showMapCursor = false;
				actionShow = false;
				showCodeCursor = true;
			}
			else if(unit2codeShow){
				World.unit2.draw(g);
				World.unit2.drawMenu(g);
				showMapCursor = false;
				actionShow = false;
				showCodeCursor = true;
			}
			else if(unit3codeShow){
				World.unit3.draw(g);
				World.unit3.drawMenu(g);
				showMapCursor = false;
				actionShow = false;
				showCodeCursor = true;
			}
			else if(showArea){
				switch(AreaMapMenu.topology){
					case "1":
						// UP
						World.areaMap1.draw(g);
						break;
					case "2:":
						// DOWN
						World.areaMap1.draw(g);
						break;
					case "3":
						// LEFT  from-->
						World.areaMap1.draw(g);
						break;
					case "4":
						// RIGHT <--from
						World.areaMap1.draw(g);
						break;
					default:
				}
			}
		}
		
		
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 120, 380, 15, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 0, 380, 120, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 135, 380, 115, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 250, 380, 300, null);
		if(unit1codeShow){
			g.drawImage(World.texture_set.get("robotSelected").getImage(), 0, 270, 380, 50, null);
		}
		else if(unit2codeShow){
			g.drawImage(World.texture_set.get("robotSelected").getImage(), 0, 320, 380, 50, null);
		}
		else if(unit3codeShow){
			g.drawImage(World.texture_set.get("robotSelected").getImage(), 0, 370, 380, 50, null);
		}
		
		
		g.setColor(Color.white);
		g.drawString(Player.playerName, 0, 10);
		
		if(showMapCursor){
			g.drawImage(World.texture_set.get("cursor").getImage(), (mouseX/32)*32-5, (mouseY/32)*32, 32, 32, null);
		}
		if(showCodeCursor){
			g.drawImage(World.texture_set.get("cursor").getImage(), (mouseX/32)*32-5, (mouseY/32)*32, 32, 32, null);
		}
		if(actionShow){
			g.drawImage(World.texture_set.get("cursorSelected").getImage(), actionMenuCursorX, actionMenuCursorY, 32, 32, null);
			g.drawImage(World.texture_set.get("chat").getImage(), actionShowMouseX, actionShowMouseY, 50, 40, null);
			g.setColor(Color.white);
			if(Mouse.actionMenuMouseMove()){
				g.setColor(Color.red);
				g.drawString("Move", actionShowMouseX, actionShowMouseY+10);
				g.setColor(Color.white);
			}
			else{
				g.setColor(Color.white);
				g.drawString("Move", actionShowMouseX, actionShowMouseY+10);
			}
			
			if(Mouse.actionMenuMouseExplore()){
				g.setColor(Color.red);
				g.drawString("Attack", actionShowMouseX, actionShowMouseY+30);
				g.setColor(Color.white);
			}
			else{
				g.setColor(Color.white);
				g.drawString("Attack", actionShowMouseX, actionShowMouseY+30);
			}
		}

		
		g.setColor(Color.white);
		g.drawImage(World.texture_set.get("sturm").getImage(), 5, 270, 23, 32, null);
		g.drawString("U1: " + World.squad.unit1.name + " hp: " + World.squad.unit1.hp + "\\"  + World.squad.unit1.hpMax, 5, 310);
		g.drawImage(World.texture_set.get("scout").getImage(), 5, 320, 22, 32, null);
		g.drawString("U2: " + World.squad.unit2.name + " hp: " + World.squad.unit2.hp + "\\"  + World.squad.unit2.hpMax, 5, 360);
		g.drawImage(World.texture_set.get("art").getImage(), 5, 370, 25, 32, null);
		g.drawString("U3: " + World.squad.unit3.name + " hp: " + World.squad.unit3.hp + "\\"  + World.squad.unit3.hpMax, 5, 410);
	}
}
