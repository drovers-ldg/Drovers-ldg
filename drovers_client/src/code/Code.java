package code;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Serializable;
import client.Chat;
import player_data.World;

public class Code{
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
		WAIT, // wait event					 (WAIT->EVENT)
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
	
	public void draw(Graphics g){
		for(int i = 0; i < 10; ++i){
			for(int j = 0; j < 10; ++j){
				if(code[i][j].type == Type.MAIN){
					g.drawImage(World.texture_set.get("codeMain").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].type == Type.EVENT){
					g.drawImage(World.texture_set.get("codeEvent").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].type == Type.ACTION){
					g.drawImage(World.texture_set.get("codeAction").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].type == Type.NULL){
					g.drawImage(World.texture_set.get("codeNull").getImage(), i*32+380, j*32, 32, 32, null);
				}
			}
		}
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
		out.flush();
	}

	public String getCode(int x, int y) throws IOException {
			Block block = code[x][y];
			int type = 0;
			int data1 = 0;
			int data2 = 0;
	
			if(block instanceof BlockMain){
					type = 5;
					data1 = 0;
					data2 = 0;
				}
				else if(block instanceof BlockEventZone){
					type = 1;
						
					// EVENT
					if(((BlockEvent) block).event == Code.Event.NULL);
						data1 = 0; // null
					if(((BlockEvent) block).event == Code.Event.DESTROY);
						data1 = 1;
					if(((BlockEvent) block).event == Code.Event.DETECT);
						data1 = 2;
					if(((BlockEvent) block).event == Code.Event.ENTERZONE);
						data1 = 3;
					if(((BlockEvent) block).event == Code.Event.MARK);
						data1 = 4;
					if(((BlockEvent) block).event == Code.Event.MOVE);
						data1 = 5;
					// ZONE	
					if(((BlockEventZone) block).zone == Code.Zone.NULL)
						data2 = 0;
					if(((BlockEventZone) block).zone == Code.Zone.ENEMY)
						data2 = 1;
					if(((BlockEventZone) block).zone == Code.Zone.NETURAL)
						data2 = 2;
					if(((BlockEventZone) block).zone == Code.Zone.TEAM)
						data2 = 3;
				}
				else if(block instanceof BlockEventType){
					type = 2;
						
					// EVENT
					if(((BlockEvent) block).event == Code.Event.NULL);
						data1 = 0; // null
					if(((BlockEvent) block).event == Code.Event.DESTROY);
						data1 = 1;
					if(((BlockEvent) block).event == Code.Event.DETECT);
						data1 = 2;
					if(((BlockEvent) block).event == Code.Event.ENTERZONE);
						data1 = 3;
					if(((BlockEvent) block).event == Code.Event.MARK);
						data1 = 4;
					if(((BlockEvent) block).event == Code.Event.MOVE);
						data1 = 5;
						
					// TYPE
					if(((BlockEventType) block).enemytype == Code.Enemy.NULL)
						data2 = 0; // null
					if(((BlockEventType) block).enemytype == Code.Enemy.EASY)
						data2 = 1;
					if(((BlockEventType) block).enemytype == Code.Enemy.HEAVY)
						data2 = 2;
					if(((BlockEventType) block).enemytype == Code.Enemy.ART)
						data2 = 3;
				}
				else if(block instanceof BlockActionZone){
					type = 3;
						
					// ACTION
					if(((BlockAction) block).action == Code.Action.NULL)
						data1 = 0;
					if(((BlockAction) block).action == Code.Action.MARK)
						data1 = 1;
					if(((BlockAction) block).action == Code.Action.MOVE)
						data1 = 2;
					if(((BlockAction) block).action == Code.Action.SHOT)
						data1 = 3;
					if(((BlockAction) block).action == Code.Action.WAIT)
						data1 = 4;
						
					// ZONE	
					if(((BlockEventZone) block).zone == Code.Zone.NULL)
						data2 = 0;
					if(((BlockEventZone) block).zone == Code.Zone.ENEMY)
						data2 = 1;
					if(((BlockEventZone) block).zone == Code.Zone.NETURAL)
						data2 = 2;
					if(((BlockEventZone) block).zone == Code.Zone.TEAM)
						data2 = 3;
				}
				else if(block instanceof BlockActionType){
					type = 4;
						
					// ACTION
					if(((BlockAction) block).action == Code.Action.NULL)
						data1 = 0;
					if(((BlockAction) block).action == Code.Action.MARK)
						data1 = 1;
					if(((BlockAction) block).action == Code.Action.MOVE)
						data1 = 2;
					if(((BlockAction) block).action == Code.Action.SHOT)
						data1 = 3;
					if(((BlockAction) block).action == Code.Action.WAIT)
						data1 = 4;
						
					// TYPE
					if(((BlockEventType) block).enemytype == Code.Enemy.NULL)
						data2 = 0; // null
					if(((BlockEventType) block).enemytype == Code.Enemy.EASY)
						data2 = 1;
					if(((BlockEventType) block).enemytype == Code.Enemy.HEAVY)
						data2 = 2;
					if(((BlockEventType) block).enemytype == Code.Enemy.ART)
						data2 = 3;
				}
				
			Chat.add_to_msg_log("Type: " + type + " data1: " + data1 + " data2:" + data2);
			return ""+type+" "+data1+" "+data2;
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
		out.flush();
	}
}
//--------------------------------
//EVENT
class BlockEvent extends Block{
	private static final long serialVersionUID = 201312021911L;
	public Code.Event event;
	
	public BlockEvent(){
		super(Code.Type.EVENT);
		this.event = Code.Event.NULL;
	}
	
	public BlockEvent(Code.Event event){
		super(Code.Type.EVENT);
		this.event = event;
	}
}

class BlockEventZone extends BlockEvent{
	private static final long serialVersionUID = 201312021911L;
	public Code.Zone zone;
	
	public BlockEventZone(Code.Event event, Code.Zone zone){
		super(event);
		this.zone = zone;
	}
}
class BlockEventType extends BlockEvent{
	private static final long serialVersionUID = 201312021911L;
	public Code.Enemy enemytype;
	
	public BlockEventType(Code.Event event, Code.Enemy enemytype){
		super(event);
		this.enemytype = enemytype;
	}
}
//--------------------------------
//ACTION
class BlockAction extends Block{
	private static final long serialVersionUID = 201312021911L;
	public Code.Action action;
	
	public BlockAction(){
		super(Code.Type.ACTION);
		this.action = Code.Action.NULL;
	}
	
	public BlockAction(Code.Action action){
		super(Code.Type.ACTION);
		this.action = action;
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