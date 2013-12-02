package client;

import java.awt.event.KeyEvent;

public class InputField{
	public String str;
	
	public InputField(){
		str = "";
	}
	
	public void inputKey(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(str.length() != 0){
				if(str.length() == 1){
					str = "";
				}
				else{
					char [] type = str.toCharArray();
					char [] tmp = new char[str.length()-1];
					for(int i = 0; i < tmp.length; ++i)
						tmp[i] = type[i];
					
					str = new String(tmp);
				}
			}
		}
		else{
			if(str.length() < 128 && ((e.getKeyChar() > 31 && e.getKeyChar() < 127) || (e.getKeyChar() > 1039 && e.getKeyChar() < 1120)))
				str += e.getKeyChar();
		}	
	}
}