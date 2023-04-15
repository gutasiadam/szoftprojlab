
public class Saboteur extends Character {
	private SaboteurPlace position;
	
	public void setPosition(SaboteurPlace pos) {
		position=pos;
	}
	public Place getPosition() {
		return position;
	}
	void dealDamage() {
		position.damage();
	}
/*	Ha kesz a getNeighbors
	public void move(int dir) {
		Element[] neighbors = position.getNeighbors();
		boolean success = neighbors[dir].accept(this);
		if(success) position.remove(this);
	}
	*/
	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}
	//step
}
