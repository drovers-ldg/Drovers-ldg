package player_data;

import java.awt.Color;

import java.awt.Graphics;
import java.util.HashMap;

public class PlayersOnline{
	
	public HashMap<String, PlayerSend> set;
	
	public PlayersOnline(){
		set = new HashMap<String, PlayerSend>();
	}
	
	public void draw(Graphics g){
		for(PlayerSend item: this.set.values()){
			if(item != null){
				g.drawImage(World.texture_set.get("sturm").getImage(), item.mapX*32+380, item.mapY*32, 32, 32, null);
				g.setColor(Color.red);
				g.drawString(item.playerName, item.mapX*32+380, item.mapY*32);
			}
		}
	}
	
	public int size(){
		return this.set.size();
	}
}