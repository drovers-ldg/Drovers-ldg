package messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 201312021833L;
	
	public enum Type {
		// read Manual\MessageTypes.txt
		DEFAULT,
		TIME,
		CHAT,
		LOGIN,
		LOGOUT,
		DISCONNECT,
		UPDATEAREA,
		UPDATEWORLD,
		UPDATEPLAYER,
		CONNECTIONSUCESS,
		CONNECTIONFAILED,
		
		// Squad Move
		SQMOVEUP,
		SQMOVEDOWN,
		SQMOVELEFT,
		SQMOVERIGHT,
		
		SQMOVEUPLEFT,
		SQMOVEUPRIGHT,
		SQMOVEDOWNLEFT,
		SQMOVEDOWNRIGHT,
		
		UPDATESQUADS,
		
		// Squad attack
		SQATTACKUP,
		SQATTACKDOWN,
		SQATTACKLEFT,
		SQATTACKRIGHT,
		
		SQATTACKUPLEFT,
		SQATTACKUPRIGHT,
		SQATTACKDOWNLEFT,
		SQATTACKDOWNRIGHT,
		
		// AreaMaps loading
		BATTLEAREA1,
		BATTLEAREA2,
		SQUPDATE,
		ROBOTSLOAD,
		
		// Map Actions
		AREAUPDATEUNITS,
		
		// err msg
		ERROR
	};
	
	public Type type = Type.DEFAULT;
	public int prefix = -1;
	public String data = null;
	
	public Message(Type type){
		this.type = type;
	}
	public Message(Type type, String data){
		this(type);
		this.data = data;
	}
	
	public Message(Type type, String data, int prefix){
		this(type, data);
		this.prefix = prefix;
	}
	
	public void send(ObjectOutputStream out) throws IOException{
		out.writeObject(this);
		out.flush();
	}
}