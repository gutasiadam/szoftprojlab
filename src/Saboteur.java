import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Szabotőr karaktereket reprezentáló osztály.
 * @author Bodnar Mark*/
public class Saboteur extends Character {
	private SaboteurPlace position;
	private boolean myTurn;
	
	/**
	 * Konstruktor.
	 */
	Saboteur(){
		myTurn=false;
	}

	/**
	 * Betöltéskor létrehoz egy megfelelő állapotú Szabotőrt
	 * @param pos position
	 */
	Saboteur(SaboteurPlace pos)
	{
		position = pos;
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
		if(dir<0||neighbors.size()<=dir){
			Control.getInstance().appendToLog("Failed to move, invalid index.");
			// System.out.println("Failed to move, invalid index.");
			return;
		}
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
		ArrayList<Element> elements = Game.getInstance().getGameElements();
		for(int i = 0; i < elements.size(); i++){
			List<Character> chs = elements.get(i).getStandingOn();
			if(chs.contains(this)) position = elements.get(i);
		}
		//System.out.println(String.format("\t%s<-%s.accept(%s):%s",this.getName(), position.getName(),this.getName(),success));	
	}

	/**
	 * pozíció ragadósra állítása
	 */
	public void makeSticky(){position.stick();}

	/**
	 * pozíció csúszóssá állítása
	 */
	public void putSlime() {
		position.slime();
	}

	/**
	 * Pumpa ki és bemeneti csövének beállítása
	 */
	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}

	/**
	 * Osztály kiírása stringént
	 */
	@Override
	public String toString()
	{
		return "S "+this.getName()+" "+position.getName();
	}
}
