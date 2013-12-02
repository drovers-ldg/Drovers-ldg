package player_data;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class WorldMap implements Externalizable{
	private static final long serialVersionUID = 201311261826L;
	
	public static WorldMapNode [][] map;
	public static int sizeX;
	public static int sizeY;
	
	public WorldMap(){
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		sizeX = in.readInt();
		sizeY = in.readInt();
		map = new WorldMapNode[sizeX][sizeY];
		
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				map[i][j] = new WorldMapNode(in.readUTF(), in.readInt(), in.readInt());
			}
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// void
	}

	public static void draw(Graphics g) {
		g.setColor(Color.red);
		for(int i = 0; i < sizeX; ++i){
			for(int j = 0; j < sizeY; ++j){
				g.drawImage(World.getTile(map[i][j].type), i*32+380, j*32, 32, 32, null);
				//g.drawString(map[i][j].areaName, i*32+380, j*32);
			}
		}
	}
}

class WorldMapNode{
	public int id;
	public String areaName;
	public int type;
	
	WorldMapNode(String areaName, int id, int type){
		this.id = id;
		this.areaName = areaName;
		this.type = type;
	}
}