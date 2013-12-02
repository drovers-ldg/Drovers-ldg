package messages;

import java.io.ObjectOutput;

public class MessageDouble extends Message{
	
	private static final long serialVersionUID = 2L;
	public String data2;
	
	public MessageDouble(String data, String data2){
		super(Message.Type.CHAT, data);
		this.data2 = data2;
	}

	public void send(ObjectOutput out) {
		// TODO Auto-generated method stub
		
	}
}