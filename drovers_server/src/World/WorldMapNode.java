package World;

public class WorldMapNode{
	public int id;
	public String areaName;
	public int type;
	public boolean movable;
	
	WorldMapNode(String areaName, int id, int type){
		this.id = id;
		this.areaName = areaName;
		this.type = type; // 0 - null, 1 - grass, 2 - forest, 3 - deep forest, 4 - rocks
		
		switch(type){
			case 0:
				this.movable = true;
				break;
			case 1: 
				this.movable = true;
				break;
			case 2:
				this.movable = true;
				break;
			case 3:
				this.movable = true;
				break;
			case 4: 
				this.movable = true;
				break;
			case 5: 
				this.movable = true;
				break;
			case 6: 
				this.movable = true;
				break;
			case 7: 
				this.movable = true;
				break;
			default:
				this.movable = true;
		}
	}
}