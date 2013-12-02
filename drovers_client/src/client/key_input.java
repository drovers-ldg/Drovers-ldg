package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import GUI.LoginMenu;

public class key_input extends KeyAdapter 
{
	
	// GUI INPUTS
	public static boolean systemConsole = false;
	// GUI.LoginMenu.java
	public static boolean loginMenuLoginFeild = false;
	public static boolean loginMenuPasswordField = false;
	
	// -------------------------
	public void keyPressed(KeyEvent e){
		switch(State.state){
			case "login":{
					if(loginMenuLoginFeild){
						// ENTER TO LOGIN STRING
						LoginMenu.loginString.inputKey(e);
						return;
					}
					else if(loginMenuPasswordField){
						// ENTER TO PASSWORD STRING
						LoginMenu.passwordString.inputKey(e);
						return;
					}
				}
				break;
			default:
		}
		if(Game.state.get_console() && e.getKeyCode() != KeyEvent.VK_ENTER){
			Chat.set_console_type(e);
		}
		else{
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				try {
					Game.state.console();
				}
				catch (IOException e1) {
				}
			}
		}
	} 	
	
	public void keyReleased(KeyEvent e) {	

	}
}