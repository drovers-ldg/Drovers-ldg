package player_data;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Vector;

public class PlayersOnline implements Externalizable{
	private static final long serialVersionUID = 201312021605L;
	
	private Vector<PlayerSend> set;
	
	public PlayersOnline(){
		set = new Vector<PlayerSend>();
	}
	
	protected class PlayerSend implements Serializable{
		private static final long serialVersionUID = 201312021605L;
		
		int mapX;
		int mapY;
		String playerName;
		
		PlayerSend(int mapX, int mapY, String playerName){
			this.mapX = mapX;
			this.mapY = mapY;
			this.playerName = playerName;
		}
	}
	
	public void draw(Graphics g){
		for(PlayerSend item: this.set){
			//if(item != null){
				g.drawImage(World.texture_set.get("sturm").getImage(), item.mapX*32+380, item.mapY*32-32, 32, 32, null);
				g.setColor(Color.red);
				g.drawString(item.playerName, item.mapX*32+380, item.mapY*32);
			//}
		}
	}
	
	public int size(){
		return this.set.size();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		set.setSize(in.readInt());
		for(int i = 0; i < set.size(); ++i){
			set.set(i, new PlayerSend(in.readInt(), in.readInt(), in.readUTF()));
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
	}
}