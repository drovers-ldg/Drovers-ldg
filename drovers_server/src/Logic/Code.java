package Logic;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Serializable;

public class Code implements Serializable {
		private static final long serialVersionUID = 201312021911L;
		
		public static enum Type{
			NULL,
			MAIN,
			EVENT, 
			ACTION
		};
		
		public static enum Event{
			NULL,
			DETECT,	 // detect 					 (DETECT->TYPE|ENEMY)
			MARK,	 // mark   					 (MARK)
			DESTROY, // if kill				     (DESTROY)
			MOVE,	 // if move  				 (MOVE->TYPE|ALLIES|ENEMY)
			ENTERZONE// if anybody enter to zone (ENTERZONE->TYPE|ALLIES|ENEMY)
		};
		
		public static enum Action{
			NULL,
			WAIT, // null						 (WAIT->EVENT)
			MOVE, // move to zone 				 (MOVE->ZONE)
			MARK, // "mark" enemy 				 (MARK->TYPE)
			SHOT  // "shot" enemy 				 (SHOT->TYPE|ENEMY) 
		};
		
		public static enum Zone{
			NULL,
			ENEMY,   // enemy squad
			NETURAL, // out of range
			TEAM     // allies squad
		};
		
		public static enum Enemy{
			NULL,
			EASY,
			HEAVY,
			ART
		}
		
		public static Block[][] code;
		
		public Code(){
			code = new Block[10][10];
			for(int i = 0; i < 10; ++i){
				for(int j = 0; j < 10; ++j){
					code[i][j] = new Block();
				}
			}
			code[0][0] = new BlockMain();
		}
		
		public void clearBlock(int x, int y){
			if(x != 0 && y != 0)
				code[x][y].type = Code.Type.NULL;
		}
		public void setBlock(int x, int y, Code.Action action, Code.Zone zone){
			if(x != 0 && y != 0)
				code[x][y] = new BlockActionZone(action, zone);
		}
		public void setBlock(int x, int y, Code.Action action, Code.Enemy enemytype){
			if(x != 0 && y != 0)
				code[x][y] = new BlockActionType(action, enemytype);
		}
		public void setBlock(int x, int y, Code.Event event, Code.Zone zone){
			if(x != 0 && y != 0)
				code[x][y] = new BlockEventZone(event, zone);
		}
		public void setBlock(int x, int y, Code.Event event, Code.Enemy enemytype){
			if(x != 0 && y != 0)
				code[x][y] = new BlockEventType(event, enemytype);
		}
		
		public void send(ObjectOutput out) throws IOException{
			out.writeObject(out);
		}
}

//--------------------------------
class Block implements Serializable{
	private static final long serialVersionUID = 201312021911L;	
	public Code.Type type; // null, main, event, action

	public Block(){
		this.type = Code.Type.NULL;
	}
		
	public Block(Code.Type type){
		this.type = type;
	}
	
	public void send(ObjectOutput out) throws IOException{
		out.writeObject(this);
	}
}
//--------------------------------
// EVENT
class BlockEvent extends Block{
	private static final long serialVersionUID = 201312021911L;
	public Code.Event type;
	
	public BlockEvent(){
		super(Code.Type.EVENT);
		this.type = Code.Event.NULL;
	}
	
	public BlockEvent(Code.Event type){
		super(Code.Type.EVENT);
		this.type = type;
	}
}

class BlockEventZone extends BlockEvent{
	private static final long serialVersionUID = 201312021911L;
	public Code.Zone zone;
	
	public BlockEventZone(Code.Event type, Code.Zone zone){
		super(type);
		this.zone = zone;
	}
}
class BlockEventType extends BlockEvent{
	private static final long serialVersionUID = 201312021911L;
	public Code.Enemy enemytype;
	
	public BlockEventType(Code.Event type, Code.Enemy enemytype){
		super(type);
		this.enemytype = enemytype;
	}
}
//--------------------------------
// ACTION
class BlockAction extends Block{
	private static final long serialVersionUID = 201312021911L;
	public Code.Action type;
	
	public BlockAction(){
		super(Code.Type.ACTION);
		this.type = Code.Action.NULL;
	}
	
	public BlockAction(Code.Action type){
		super(Code.Type.ACTION);
		this.type = type;
	}
}

class BlockActionZone extends BlockAction{
	private static final long serialVersionUID = 201312021911L;
	public Code.Zone zone;
	
	BlockActionZone(Code.Action action, Code.Zone zone){
		super(action);
		this.zone = zone;
	}
}

class BlockActionType extends BlockAction{
	private static final long serialVersionUID = 201312021911L;
	public Code.Enemy enemytype;
	
	BlockActionType(Code.Action action, Code.Enemy enemytype){
		super(action);
		this.enemytype = enemytype;
	}
}

//--------------------------------
class BlockMain extends Block{
	private static final long serialVersionUID = 201312021911L;
	public BlockMain(){
		super(Code.Type.MAIN);
	}
}