
public class Repairman extends Character {
	private RepairmanPlace position;
	private Pipe holdingPipe;
	private Pump holdingPump;
	private Game game;
	
	//mindig null kell legyen amikor nincs nala 
	Repairman(){
		holdingPipe=null;
		holdingPump=null;
	}
	//getterek, setterek
	//a teszteknel hasznaltunk setPosit, de meg a 4.heten nem volt
//----------------------------------------------------------------	
	public void setPosition(RepairmanPlace pos) {
		position=pos;
	}
	public Place getPosition() {
		return position;
	}
	public Pump getHoldingPump() {
		return holdingPump;
	}
	public void setHoldingPump(Pump p) {
		holdingPump=p;
	}
	public Pipe getHoldingPipe() {
		return holdingPipe;
	}
	public void setHoldingPipe(Pipe p) {
		holdingPipe=p;
	}
//-----------------------------------------	
	public void RepairElement() {
		position.repair();
	}
	public void LiftPipe(int dir) {
		if(holdingPipe==null) holdingPipe=position.lift(dir);
	}
	public void LiftPump() {
		if(holdingPump==null) holdingPump=position.givePump();
	}
	public void PlacePipe() {
		position.placePipe(holdingPipe);
	}
	public void PlacePump() {
		position.placePump(holdingPump);
	}
}
