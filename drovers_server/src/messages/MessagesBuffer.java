package messages;

import java.util.ArrayList;

public class MessagesBuffer{
	private ArrayList<MessageIn> list;
	
	public MessagesBuffer(){
		this.list = new ArrayList<MessageIn>();
	}
	public void add(MessageIn msg){
		this.list.add(msg);
	}
	public void clear(){
		this.list.clear();
	}
	public int size(){
		return this.list.size();
	}
	public MessageIn get(int i){
		return this.list.get(i);
	}
}