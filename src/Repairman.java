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
			System.out.println(String.format("\t1.2 %s->%s.lift(dir)", getName(), position.getName()));
			holdingPipe = position.lift(dir);
			System.out.println(
					String.format("\t%s<-%s.lift(dir): %s", getName(), position.getName(), holdingPipe.getName()));
		}
	}

	/** 
     * Pumpa felvevese. 
     * Csak ciszternanal teheto meg.
     */
	public void LiftPump() {
		if (holdingPump == null) {
			System.out.println(String.format("\t1.2 %s->%s.givePump()", getName(), position.getName()));
			holdingPump = position.givePump();
			if (holdingPump != null) {
				System.out.println(
						String.format("\t%s<-%s.givePump(): %s", getName(), position.getName(), holdingPump.getName()));
			} else {
				System.out.println(String.format("\t%s<-%s.givePump(): null", getName(), position.getName()));
			}
		}
	}
    /** 
     * Cso elhelyezese.
     */
	public void PlacePipe() {
		System.out.println(String.format("\t1.2 %s->%s.placePipe()", getName(), position.getName()));
		position.placePipe(holdingPipe);
		System.out.println(String.format("\t%s<-%s.placePipe() : %s", getName(), position.getName(),
				position.placePipe(holdingPipe)));
		Tabulator.increaseTab();
		Tabulator.printTab();
		System.out.println("1.1 " + getName() + "->" + position.getName() + "placePipe(" + holdingPipe.getName() + ")");

		if (position.placePipe(holdingPipe))
			holdingPipe = null;

		Tabulator.printTab();
		if (holdingPipe != null)
			System.out.println(getName() + ".holdingPipe=" + holdingPipe.getName());
		else
			System.out.println(getName() + ".holdingPipe=null");

		Tabulator.decreaseTab();
		Tabulator.printTab();
		System.out.println("<-" + getName() + ".PlacePipe():void");
	}
    /** 
     * Pumpa elhelyezese.
     */
	public void PlacePump() {
		System.out.println(
				String.format("\t1.2 %s->%s.placePump(%s)", getName(), position.getName(), holdingPump.getName()));
		Pipe createdPipe = position.placePump(holdingPump);
		if (createdPipe != null) {
			System.out.println(String.format("\t%s<-%s.placePump(%s): %s", getName(), position.getName(),
					holdingPump.getName(), createdPipe.getName()));

			System.out.println(String.format("\t1.13 %s->game.addPump(%s)", getName(), holdingPump.getName()));
			System.out.println(String.format("\t\t1.14 game->timer.addPump(%s)", holdingPump.getName()));
			game.addPump(holdingPump);
			System.out.println(String.format("\t\tgame<-timer.addPump(%s)", holdingPump.getName()));
			System.out.println(String.format("\t%s<-game.addPump(%s)", getName(), holdingPump.getName()));

			System.out.println(String.format("\t1.15 %s->game.addPipe(%s)", getName(), createdPipe.getName()));
			game.addPipe(createdPipe);
			System.out.println(String.format("\t%s<-game.addPipe(%s)", getName(), createdPipe.getName()));

			System.out.println(String.format("\t1.16 %s holdingPump := null", getName()));
			holdingPump = null;
		} else {
			System.out.println(String.format("\t%s<-%s.placePump(%s): null", getName(), position.getName(),
					holdingPump.getName()));
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
