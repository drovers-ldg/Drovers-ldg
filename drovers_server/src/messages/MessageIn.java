package messages;

public class MessageIn extends Message{
	private static final long serialVersionUID = 1L;
	
	public int client_id;
	
	public MessageIn(Message msg, int client_id){
		super(msg.type, msg.data, msg.prefix);
		this.client_id = client_id;
	}
	
	public MessageIn(Message.Type type, int client_id){
		super(type);
		this.client_id = client_id;
	}
}