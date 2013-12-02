package client;

import java.awt.event.KeyEvent;

public class PasswordField extends InputField{
	public String pass;
	
	public PasswordField(){
		super();
		pass = "";
	}
	
	public void inputKey(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			
			if(pass.length() != 0){
				if(pass.length() == 1){
					str = "";
					pass = "";
				}
				else{
					char [] typeStr = str.toCharArray();
					char [] typePass = pass.toCharArray();
					char [] tmpStr = new char[str.length()-1];
					char [] tmpPass = new char[pass.length()-1];
					
					for(int i = 0; i < tmpPass.length; ++i){
						tmpStr[i] = typeStr[i];
						tmpPass[i] = typePass[i];
					}
					
					str = new String(tmpStr);
					pass = new String(tmpPass);
				}
			}
		}
		else{
			if(str.length() < 128 && ((e.getKeyChar() > 31 && e.getKeyChar() < 127) || (e.getKeyChar() > 1039 && e.getKeyChar() < 1120))){
				str += "*";
				pass += e.getKeyChar();
			}
		}
	}
}