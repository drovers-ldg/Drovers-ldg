package code;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Serializable;
import player_data.World;

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
		WAIT, // null
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
	
	public static enum EmenyType{
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
	public void setBlock(int x, int y, Code.Action action, Code.EmenyType enemytype){
		if(x != 0 && y != 0)
			code[x][y] = new BlockActionType(action, enemytype);
	}
	public void setBlock(int x, int y, Code.Event event, Code.Zone zone){
		if(x != 0 && y != 0)
			code[x][y] = new BlockEventZone(event, zone);
	}
	public void setBlock(int x, int y, Code.Event event, Code.EmenyType enemytype){
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
//EVENT
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
	public Code.EmenyType enemytype;
	
	public BlockEventType(Code.Event type, Code.EmenyType enemytype){
		super(type);
		this.enemytype = enemytype;
	}
}
//--------------------------------
//ACTION
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
	public Code.EmenyType enemytype;
	
	BlockActionType(Code.Action action, Code.EmenyType enemytype){
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