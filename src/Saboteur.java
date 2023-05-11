import java.util.List;
import java.util.Scanner;
/** @authorBodnar Mark*/
public class Saboteur extends Character {
	private SaboteurPlace position;
	private boolean myTurn;
	
	Saboteur(){
		myTurn=false;
	}
	
    /** Aktualis pozicio.*/
	public void setPosition(SaboteurPlace pos) {
		position=pos;
	}
	public Place getPosition() {
		return position;
	}
	/** Az elem amin aktualisan all, kilyukasztasa.*/
	void dealDamage() {
		position.damage();
	}
	/**
	 * bovulni fog meg listazassal, de most meg megkapja az iranyt
	 * egy szamot es azon elerheto szomszedjara lepteti tovabb a karaktert
	 * @param dir szam amely megadja az iranyt
	 */
	public void move(int dir) {
		//System.out.println(String.format("Aktualis pozicio:"+position.getName()));
		List<? extends Element> neighbors = position.getNeighbors();
		SaboteurPlace lastPosition=position;
		//System.out.println(String.format("\t1.1 %s->%s.remove(%s)",this.getName(), position.getName(),this.getName()));
		boolean removeSuccess=position.remove(this);
		//System.out.println(String.format("\t1.1 %s->%s.accept(%s): Nem tortent lepes.",this.getName(), position.getName(),this.getName()));
		
		if(removeSuccess) {
			//a szomszedok kozul a kivalsztott, dir poziciora probaljuk athelyezni a karaktert
			//fontos majd, hogy a valasztott irany es a szomszedok lista konzisztens legyen
			//System.out.println(String.format("\t\t1.2 %s->%s.accept(%s)",this.getName(), position.getName(),this.getName()));
			boolean acceptSuccess=neighbors.get(dir).accept(this);
			if(!acceptSuccess) lastPosition.accept(this);
				//System.out.println(String.format("\t\t1.2 Sikeres lepes, uj pozicio:"+position.getName()));
		}
		//System.out.println(String.format("\t%s<-%s.accept(%s):%s",this.getName(), position.getName(),this.getName(),success));	
	}
	public void makeSticky(){position.stick();}
	public void putSlime() {
		position.slime();
	}
	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}
	public void step() {
		myTurn=true;
		while(myTurn) {
			//ezt meg meg kell beszelni
			//lastInput=input
			//lastInputSuccess=input()
			//if(lastInputSuccess && lastInput!=move) myTurn=false;
		}
	}
}
