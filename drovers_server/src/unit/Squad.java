package unit;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.SQLException;

import database.DBSquads;
import database.DBUnits;

public class Squad implements Externalizable{
	private static final long serialVersionUID = 201311301514L;
	
	public int id;
	public int accountId;
	
	public Unit unit1; 
	public Unit unit2;
	public Unit unit3;
	
	public Squad(int accountId) throws SQLException{
		id = DBSquads.map.size()+1;
		this.accountId = accountId;
		setDefaultSQ(accountId);
	}
	
	public Squad(int id, int accountId, int unitId1, int unitId2, int unitId3){
		this.id = id;
		this.accountId = accountId;
		
		this.unit1 = DBUnits.map.get(unitId1);
		this.unit2 = DBUnits.map.get(unitId2);
		this.unit3 = DBUnits.map.get(unitId3);
	}
	
	public void setDefaultSQ(int playerId) throws SQLException{
		this.unit1 = new Unit(DBUnits.map.size()+1, playerId, 0, 0, "sturm"); // sturm
		this.unit2 = new Unit(DBUnits.map.size()+2, playerId, 0, 0, "scout"); // scout-marker
		this.unit3 = new Unit(DBUnits.map.size()+3, playerId, 0, 0, "art");   // art
		
		DBUnits.addUnit(playerId, "sturm");
		DBUnits.addUnit(playerId, "scout");
		DBUnits.addUnit(playerId, "art");
		DBSquads.addSquad(this);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// void
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		writeUnit(unit1, out);
		writeUnit(unit2, out);
		writeUnit(unit3, out);
		out.flush();
		System.out.println("Send SQUAD to " + this.accountId);
	}
	
	public void softUpdate(ObjectOutput out) throws IOException {
		updateLocal(unit1, out);
		updateLocal(unit2, out);
		updateLocal(unit3, out);
		out.flush();
	}
	
	private void writeUnit(Unit unit, ObjectOutput out) throws IOException{
		out.writeUTF(unit.name);
		out.writeUTF(unit.type);
		out.writeInt(unit.areaX);
		out.writeInt(unit.areaY);
		out.writeInt(unit.hp);
	}
	
	private void updateLocal(Unit unit, ObjectOutput out) throws IOException{
		out.writeInt(unit.areaX);
		out.writeInt(unit.areaY);
		out.writeInt(unit.hp);
	}
}