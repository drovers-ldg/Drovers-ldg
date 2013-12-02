package player_data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class Player implements Externalizable{

	private static final long serialVersionUID = 201311201743L;
	
	public static String playerName;
	public static int gm;

	// recources
	public static int thorium;
	public static int metal;
	public static int money;
	public static int mapX;
	public static int mapY;

	public Player(){
		playerName = "";
		gm = 0;
		thorium = 0;
		metal = 0;
		money = 0;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		playerName = in.readUTF();
		gm = in.readInt();
		thorium = in.readInt();
		metal = in.readInt();
		money = in.readInt();
		mapX = in.readInt();
		mapY = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// void
	}	
}