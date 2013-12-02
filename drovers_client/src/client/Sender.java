package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import messages.Message;


public class Sender{
	protected static ObjectOutputStream out;
	
	public Sender(ObjectOutputStream out) throws IOException{
		Sender.out = out;
	}
	public static void send(String msg) throws IOException{
		new Message(Message.Type.CHAT, msg).send(out);
	}
	public static void logout() throws IOException{
		new Message(Message.Type.LOGOUT).send(out);
	}
	public static void login(String account, String password) throws IOException{
		new Message(Message.Type.LOGIN, account+":"+password).send(out);
	}
	public static void updateMap() throws IOException{
		new Message(Message.Type.UPDATEAREA).send(out);
	}
	public static void updateWorld() throws IOException{
		new Message(Message.Type.UPDATEWORLD).send(out);
	}	
	public static void updatePlayer() throws IOException{
		new Message(Message.Type.UPDATEPLAYER).send(out);
	}
	
	// SQMove
	public static void moveUp() throws IOException{
		new Message(Message.Type.SQMOVEUP).send(out);
	}
	public static void moveDown() throws IOException{
		new Message(Message.Type.SQMOVEDOWN).send(out);
	}
	public static void moveLeft() throws IOException{
		new Message(Message.Type.SQMOVELEFT).send(out);
	}
	public static void moveRight() throws IOException{
		new Message(Message.Type.SQMOVERIGHT).send(out);
	}
	public static void moveUpLeft() throws IOException{
		new Message(Message.Type.SQMOVEUPLEFT).send(out);
	}
	public static void moveUpRight() throws IOException{
		new Message(Message.Type.SQMOVEUPRIGHT).send(out);
	}
	public static void moveDownLeft() throws IOException{
		new Message(Message.Type.SQMOVEDOWNLEFT).send(out);
	}
	public static void moveDownRight() throws IOException{
		new Message(Message.Type.SQMOVEDOWNRIGHT).send(out);
	}
	public static void sendSQUpdate() throws IOException{
		new Message(Message.Type.UPDATESQUADS).send(out);
	}
	
	//SQAttack
	public static void attackUp() throws IOException{
		new Message(Message.Type.SQATTACKUP).send(out);
	}
	public static void attackDown() throws IOException{
		new Message(Message.Type.SQATTACKDOWN).send(out);
	}
	public static void attackLeft() throws IOException{
		new Message(Message.Type.SQATTACKLEFT).send(out);
	}
	public static void attackRight() throws IOException{
		new Message(Message.Type.SQATTACKRIGHT).send(out);
	}
	public static void attackUpLeft() throws IOException{
		new Message(Message.Type.SQATTACKUPLEFT).send(out);
	}
	public static void attackUpRight() throws IOException{
		new Message(Message.Type.SQATTACKUPRIGHT).send(out);
	}
	public static void attackDownLeft() throws IOException{
		new Message(Message.Type.SQATTACKDOWNLEFT).send(out);
	}
	public static void attackDownRight() throws IOException{
		new Message(Message.Type.SQATTACKDOWNRIGHT).send(out);
	}
	public static void UpdateArea1() throws IOException {
		new Message(Message.Type.BATTLEAREA1).send(out);
	}
	public static void UpdateArea2() throws IOException {
		new Message(Message.Type.BATTLEAREA2).send(out);
	}
	
	// SQUpdate
	public static void updateSquad() throws IOException{
		new Message(Message.Type.SQUPDATE).send(out);
	}
	public static void sendError(String error) throws IOException{
		new Message(Message.Type.ERROR, error).send(out);
	}
	public static void sendRobotsLoad() throws IOException{
		new Message(Message.Type.ROBOTSLOAD, null).send(out);
	}
}