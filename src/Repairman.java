import java.util.ArrayList;
import java.util.List;
/** @authorBodnar Mark*/
public class Repairman extends Character {
	private RepairmanPlace position;
	private Pipe holdingPipe;
	private Pump holdingPump;
	private Game game;
	private boolean myTurn;

    /**  Ha nincs nala pumpa vagy cso akkor annak nullnak kell lennie.*/
	Repairman() {
		holdingPipe = null;
		holdingPump = null;
		game = null;
		myTurn=false;
	}

	/**
	 * Betöltésnél létrehozza a megfelelő állapotú Szerelő játékost
	 * @param pos position
	 * @param hPipe holdingPipe
	 * @param hPump holdingPump
	 */
	public Repairman(RepairmanPlace pos,Pipe hPipe, Pump hPump) {
		position = pos;
		holdingPipe = hPipe;
		holdingPump = hPump;
	}

    /** Aktualis pozicio.*/
	public void setPosition(RepairmanPlace pos) {
		position = pos;
	}

	public Place getPosition() {
		return position;
	}
	/** Nala levo pumpa.*/
	public Pump getHoldingPump() {
		return holdingPump;
	}

	public void setHoldingPump(Pump p) {
		holdingPump = p;
	}
	/** Nala levo cso.*/
	public Pipe getHoldingPipe() {
		return holdingPipe;
	}

	public void setHoldingPipe(Pipe p) {
		holdingPipe = p;
	}

	public void setGame(Game g) {
		game = g;
	}

	// -----------------------------------------
	
	/**
	 * bovulni fog meg listazassal, de most meg megkapja az iranyt
	 * egy szamot es azon elerheto szomszedjara lepteti tovabb a karaktert
	 * @param dir szam amely megadja az iranyt
	 */
	public void move(int dir) {
		//System.out.println(String.format("Aktualis pozicio:"+position.getName()));
		List<? extends Element> neighbors = position.getNeighbors();
		RepairmanPlace lastPosition=position;
		if(dir<0||neighbors.size()<=dir){
			System.out.println("Failed to move, invalid index.");
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
		ArrayList<Element> elements = game.getGameElements();
		for(int i = 0; i < elements.size(); i++){
			List<Character> chs = elements.get(i).getStandingOn();
			if(chs.contains(this)) position = elements.get(i);
		}
		//System.out.println(String.format("\t%s<-%s.accept(%s):%s",this.getName(), position.getName(),this.getName(),success));	
	}
	/**
	 * Az elem javitasa amin all a szerelo.
	 */
	public void RepairElement() {
		System.out.println("1.2 "+this.getName() + "->" + position.getName() + ".repair();"); 
		position.repair();
	}
    /** 
     * Ezen keresztul lehet beallitani a pumpalasi iranyt.
     * @param src - input irany
     * @param dest - output irany
     */
	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}
	/** 
     * Megadott dir szerinti cso felemelese.
     * Egyszerre csak egy csot emelhet fel, ezert is kell
     * tesztelni nullra.
     * @param dir - input irany
     */
	public void LiftPipe(int dir) {
		if (holdingPipe == null) {
			holdingPipe = position.lift(dir);
			System.out.println("Successfully picked up "+holdingPipe.getName());
		}
	}

	/** 
     * Pumpa felvevese. 
     * Csak ciszternanal teheto meg.
     */
	public void LiftPump() {
		if (holdingPump == null) {
			holdingPump = position.givePump();
		}
	}
    /** 
     * Cso elhelyezese.
     */
	public void PlacePipe() {
		position.placePipe(holdingPipe);
		String hpn = holdingPipe.getName();

		if (position.placePipe(holdingPipe)){
			holdingPipe = null;
			System.out.println("Successfully placed "+hpn);
		}
			
	}
    /** 
     * Pumpa elhelyezese.
     */
	public void PlacePump() {
		Pipe createdPipe = position.placePump(holdingPump);
		if (createdPipe != null) {
			game.addPump(holdingPump);
			game.addPipe(createdPipe);
			holdingPump = null;
		}
	}
	public void makeSticky(){position.stick();}
	void dealDamage() {
		position.damage();
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

	@Override
	public String toString()
	{
		return "R "+this.getName()+" "+position.getName()+" "+((holdingPipe!=null)?(holdingPipe.getName()):("null"))+" "+((holdingPump!=null)?(holdingPump.getName()):("null"));
	}
}
