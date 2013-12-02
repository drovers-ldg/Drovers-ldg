package player_data;

public class Squad{
	public Unit unit1; 
	public Unit unit2;
	public Unit unit3;
	public boolean isLoades = false;
	
	public Squad(){
		unit1 = new Unit();
		unit2 = new Unit();
		unit3 = new Unit();
	}
}