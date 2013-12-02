package client;

import java.awt.event.KeyEvent;
import java.io.IOException;

import GUI.CharacterMenu;
import client.Game;

public class Chat{
	Chat(){
		
	}
	
	public static void set_console_type(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(State.console_type.length() != 0){
				if(State.console_type.length() == 1){
					State.console_type = "";
				}
				else{
					char [] type = State.console_type.toCharArray();
					char [] tmp = new char[State.console_type.length()-1];
					for(int i = 0; i < tmp.length; ++i)
						tmp[i] = type[i];
					
					State.console_type = new String(tmp);
				}
			}
		}
		else{
			if(State.console_type.length() < 128 && ((e.getKeyChar() > 31 && e.getKeyChar() < 127) || (e.getKeyChar() > 1039 && e.getKeyChar() < 1120)))
				State.console_type += e.getKeyChar();
		}
	}
	
	public static void process_command(String command) throws IOException{
		if(command.indexOf("/") == 0){
			if(command.matches("^/state [a-zA-Z0-9]+$")){
				String [] tmp = command.split(" ");
				Game.state.set_state(tmp[1]);
				if(tmp[1].equals("areaMap"))
				{
					CharacterMenu.showArea = true;
				}
				else{
					CharacterMenu.showArea = false;
					Chat.add_to_msg_log("[GAME] State changed: " + tmp[1]);
				}
			}
			else if(command.matches("^/login [a-zA-Z0-9]+ [a-zA-Z0-9]+$")){
				String [] tmp = command.split(" ");
				Sender.login(tmp[1], tmp[2]);
				Chat.add_to_msg_log("[GAME] Try to login.");
			}
			else if(command.matches("^/logout$")){
				Sender.logout();
				Chat.add_to_msg_log("[GAME] Logout.");
			}
			else
				Chat.add_to_msg_log("Command is not found.");
		}
		else{
			Sender.send(command);
		}
		
	}
	
	public static void add_to_msg_log(String command){
		for(int i = 0; i < Game.msg_log.length-1; ++i)
			Game.msg_log[i] = Game.msg_log[i+1];
		Game.msg_log[9] = command;
	}
}