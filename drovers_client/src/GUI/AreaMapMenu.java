package GUI;

import java.awt.Color;
import java.awt.Graphics;
import player_data.Player;
import player_data.World;

public class AreaMapMenu{
	public static String topology;
	
	public AreaMapMenu(){
		topology = "null";
	}
	
	public static void draw(Graphics g){
		switch(topology){
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
		
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 120, 380, 15, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 0, 380, 120, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 135, 380, 115, null);
		g.drawImage(World.texture_set.get("chat").getImage(), 0, 250, 380, 300, null);
		
		g.setColor(Color.white);
		g.drawString(Player.playerName, 0, 10);
		g.drawString(topology, 0, 20);
		
		// PlayersOnline.draw(g); - Draw enemies SQ
		// g.drawImage(World.texture_set.get("char").getImage(), Player.mapX*32+380, Player.mapY*32, 32, 32, null); - Show own SQ
	}
}