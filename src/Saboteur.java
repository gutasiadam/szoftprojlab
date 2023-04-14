
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
}
