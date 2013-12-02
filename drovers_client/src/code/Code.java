package code;

import java.awt.Graphics;
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
	
	public static Block[][] code;
	
	public Code(){
		code = new Block[10][10];
		for(int i = 0; i < 10; ++i){
			for(int j = 0; j < 10; ++j){
				code[i][j] = new Block();
			}
		}
		this.setBlock(0, 0, Code.Type.MAIN, "");
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < 10; ++i){
			for(int j = 0; j < 10; ++j){
				if(code[i][j].blockType == Type.MAIN){
					g.drawImage(World.texture_set.get("codeMain").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].blockType == Type.EVENT){
					g.drawImage(World.texture_set.get("codeEvent").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].blockType == Type.ACTION){
					g.drawImage(World.texture_set.get("codeAction").getImage(), i*32+380, j*32, 32, 32, null);
				}
				else if(code[i][j].blockType == Type.NULL){
					g.drawImage(World.texture_set.get("codeNull").getImage(), i*32+380, j*32, 32, 32, null);
				}
			}
		}
	}
	
	public void drawMenu(Graphics g){
		g.drawImage(World.texture_set.get("codeEvent").getImage(), 12*32+380, 3*32, 32, 32, null);
		g.drawImage(World.texture_set.get("codeAction").getImage(), 12*32+380, 4*32, 32, 32, null);
	}
	
	public void setBlock(int x, int y, Code.Type type, String value){
		code[x][y].blockType = type;
		code[x][y].value = value;
	}
	public void clearBlock(int x, int y){
		code[x][y].blockType = Code.Type.NULL;
		code[x][y].value = "";
	}
}

class Block implements Serializable{
	private static final long serialVersionUID = 201312021911L;
	
	public Code.Type blockType;
	public String value;
	
	public Block(){
		this.blockType = Code.Type.NULL;
		this.value = "";
	}
	
	public Block(Code.Type type, String value){
		this.blockType = type;
		this.value = value;
	}
}