package World;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Scanner;

public class AreaMap implements Externalizable{
	private static final long serialVersionUID = 61120131305L;
	
	public int size_x;
	public int size_y;
	public int [][] map;
	
	public AreaMap(){
		this.size_x = 0;
		this.size_y = 0;
		this.map = null;
	}
	public AreaMap(int size_x, int size_y){
		this.size_x = size_x;
		this.size_y = size_y;
		map = new int[size_x][size_y];
		
		for(int i = 0; i < size_x; ++i){
			for(int j = 0; j < size_y; ++j){
				map[i][j] = 1;
			}
		}
	}
	
	public AreaMap(Scanner in){
		if(in.hasNext()){
			this.size_x = in.nextInt();
			this.size_y = in.nextInt();
			map = new int[this.size_x][this.size_y];
		}
		
		for(int i = 0; i < size_x; ++i){
			for(int j = 0; j < size_y; ++j){
				if(in.hasNext()){
					map[i][j] = in.nextInt();
				}
			}
		}
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// void
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.size_x);
		out.writeInt(this.size_y);
		for(int i = 0; i < size_x; ++i){
			for(int j = 0; j < size_y; ++j){
				out.writeInt(this.map[i][j]);
			}
		}
		out.flush();
	}
}