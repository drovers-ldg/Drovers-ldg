package World;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import server.Server;
import database.DBAccounts;

public class PlayersOnline implements Externalizable{
	private static final long serialVersionUID = 201312021605L;
	
	private Vector<PlayerSend> set;
	
	public PlayersOnline(){
		set = new Vector<PlayerSend>();
	}
	
	public PlayersOnline updateSet(int clientId){
		Set<Integer> client_list = Server.client_list.keySet();
		Set<Integer> send_list = new HashSet<Integer>();
		
		for(Integer index: client_list){
			if(Server.client_list.get(index).get_connection() && index != clientId){
				send_list.add(index);
			}
		}
		this.set.clear();
		for(Integer index: send_list){
			this.set.add(new PlayerSend(DBAccounts.map.get(Server.client_list.get(index).get_account_id()).mapX,
					DBAccounts.map.get(Server.client_list.get(index).get_account_id()).mapY,
					DBAccounts.map.get(Server.client_list.get(index).get_account_id()).playerName));
		}
		return this;
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

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(set.size());
		for(PlayerSend item: set){
			out.writeInt(item.mapX);
			out.writeInt(item.mapY);
			out.writeUTF(item.playerName);
		}
		out.flush();
	}
}

