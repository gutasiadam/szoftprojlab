import java.util.List;
import java.util.Scanner;
/** @authorBodnar Mark*/
public class Saboteur extends Character {
	private SaboteurPlace position;
    /** Aktualis pozicio.*/
	public void setPosition(SaboteurPlace pos) {
		position=pos;
	}
	public Place getPosition() {
		return position;
	}
	/** Az elem amin aktualisan all, kilyukasztasa.*/
	void dealDamage() {
    	//System.out.println("DECISION-A "+getName()+" elrontasahoz irja be a 0-as szamot, majd enter! (csak szamok elfogadhatoak)");
        //Scanner userInput = new Scanner(System.in);
        //int input = 0;
        //input= userInput.nextInt();
        //System.out.printf("%s",this.getName());
        //if(input==0) position.damage();
		position.damage();
     
	}
	/**
	 * bovulni fog meg listazassal, de most meg megkapja az iranyt
	 * egy szamot es azon elerheto szomszedjara lepteti tovabb a karaktert
	 * @param dir szam amely megadja az iranyt
	 */
	public void move(int dir) {
		System.out.println(String.format("Aktualis pozicio:"+position.getName()));
		List<? extends Element> neighbors = position.getNeighbors();
		boolean success=false;
		System.out.println(String.format("\t1.1 %s->%s.remove(%s)",this.getName(), position.getName(),this.getName()));
		position.remove(this);
		if(neighbors!=null) {
			for (int i = 0; i < neighbors.size(); i++) {
				if(dir==i) {
					success=neighbors.get(i).accept(this);
					position=neighbors.get(i);
					System.out.println(String.format("\t\t1.2 %s->%s.accept(%s)",this.getName(), position.getName(),this.getName()));
					System.out.println(String.format("\t\t1.2 Sikeres lepes, uj pozicio:"+position.getName()));
					System.out.println(String.format("\t%s<-%s.accept(%s):%s",this.getName(), position.getName(),this.getName(),success));
				}
			}
		} 	
		if(!success) {
			System.out.println(String.format("\t1.1 %s->%s.accept(%s): Nem tortent lepes.",this.getName(), position.getName(),this.getName()));
        	position.accept(this);
        }
	}
	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}
	public void step() {}
}
