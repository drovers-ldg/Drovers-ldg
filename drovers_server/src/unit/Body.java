package unit;

public class Body{
	public enum bodyType {
		SMALL,MEDIUM,HEAVY,
	};
	
	public enum slotType {
		FREE,SOURCECARD,ENGINE,FUEL,ARMOR,WEAPON,AMMO,SENSOR, 	
	};
	
	public int id;
	
	// Data
	public int veight;
	public BodyType deviceSet;
	
	public Body(){
		deviceSet = new BodyType(Body.bodyType.SMALL);
	}
	
	// slotId [0..15]
	public void modifySlot(slotType type, int slotId, int itemId){
		if(type.equals(slotType.FREE)){
			
		}
		else if(type.equals(slotType.SOURCECARD)){
			
		}
		else if(type.equals(slotType.ENGINE)){
			
		}
		else if(type.equals(slotType.FUEL)){
			
		}
		else if(type.equals(slotType.ARMOR)){
			
		}
		else if(type.equals(slotType.WEAPON)){
			
		}
		else if(type.equals(slotType.AMMO)){
			
		}
		else if(type.equals(slotType.SENSOR)){
			
		}
	}
}
class BodyType{
	public Body.bodyType type;
	public int size;
	public SlotType [] slots;
	
	BodyType(Body.bodyType type){
		this.type = type; 
		if(type.equals(Body.bodyType.SMALL)){
			slots = new SlotType[6];
			slots[0].type = Body.slotType.SOURCECARD;
			slots[1].type = Body.slotType.ENGINE;
			slots[2].type = Body.slotType.FUEL;
			slots[3].type = Body.slotType.SENSOR;
			slots[4].type = Body.slotType.FREE;
			slots[5].type = Body.slotType.FREE;
		}
		else if(type.equals(Body.bodyType.MEDIUM)){
			slots = new SlotType[10];
			slots[0].type = Body.slotType.SOURCECARD;
			slots[1].type = Body.slotType.ENGINE;
			slots[2].type = Body.slotType.FUEL;
			slots[3].type = Body.slotType.FUEL;
			slots[4].type = Body.slotType.SENSOR;
			slots[5].type = Body.slotType.FREE;
			slots[6].type = Body.slotType.FREE;
			slots[7].type = Body.slotType.FREE;
			slots[8].type = Body.slotType.FREE;
			slots[9].type = Body.slotType.FREE;
		}
		else if(type.equals(Body.bodyType.HEAVY)){
			slots = new SlotType[16];
			slots[0].type = Body.slotType.SOURCECARD;
			slots[1].type = Body.slotType.ENGINE;
			slots[2].type = Body.slotType.FUEL;
			slots[3].type = Body.slotType.FUEL;
			slots[4].type = Body.slotType.FUEL;
			slots[5].type = Body.slotType.FUEL;
			slots[6].type = Body.slotType.SENSOR;
			slots[7].type = Body.slotType.FREE;
			slots[8].type = Body.slotType.FREE;
			slots[9].type = Body.slotType.FREE;
			slots[10].type = Body.slotType.FREE;
			slots[11].type = Body.slotType.FREE;
			slots[12].type = Body.slotType.FREE;
			slots[13].type = Body.slotType.FREE;
			slots[14].type = Body.slotType.FREE;
			slots[15].type = Body.slotType.FREE;
		}
	}
}
class SlotType{
	public Body.slotType type;
	public int item_id;
	
	SlotType(Body.slotType type){
		this.type = type;
	}
}