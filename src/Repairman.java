import java.util.ArrayList;
import java.util.List;

/** A szerelő karaktereket reprezentáló osztály.
 * @author Bodnar Mark*/
public class Repairman extends Character {
	private RepairmanPlace position;
	private Pipe holdingPipe;
	private Pump holdingPump;
	private boolean myTurn;

    /**  Ha nincs nala pumpa vagy cso akkor annak nullnak kell lennie.*/
	Repairman() {
		holdingPipe = null;
		holdingPump = null;
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


	// -----------------------------------------
	
	/**
	 * bovulni fog meg listazassal, de most meg megkapja az iranyt
	 * egy szamot es azon elerheto szomszedjara lepteti tovabb a karaktert
	 * @param dir szam amely megadja az iranyt
	 */
	public void move(int dir) {
		
		List<? extends Element> neighbors = position.getNeighbors();
		RepairmanPlace lastPosition=position;
		if(dir<0||neighbors.size()<=dir){
			Control.getInstance().appendToLog("Failed to move, invalid index.");
			// System.out.println("Failed to move, invalid index.");
			return;
		}
		
		boolean removeSuccess=position.remove(this);
		
		
		if(removeSuccess) {
			//a szomszedok kozul a kivalsztott, dir poziciora probaljuk athelyezni a karaktert
			//fontos majd, hogy a valasztott irany es a szomszedok lista konzisztens legyen
			
			boolean acceptSuccess=neighbors.get(dir).accept(this);
			if(!acceptSuccess) lastPosition.accept(this);
				
		}
		ArrayList<Element> elements = Game.getInstance().getGameElements();
		for(int i = 0; i < elements.size(); i++){
			List<Character> chs = elements.get(i).getStandingOn();
			if(chs.contains(this)) position = elements.get(i);
		}

	}
	/**
	 * Az elem javitasa amin all a szerelo.
	 */
	public void RepairElement() {
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
			if(holdingPipe!=null){
				Control.getInstance().appendToLog("Successfully picked up "+holdingPipe.getName());
				// System.out.println("Successfully picked up "+holdingPipe.getName());
			}
		}
	}

	/** 
     * Pumpa felvevese. 
     * Csak ciszternanal teheto meg.
     */
	public void LiftPump() {
		if (holdingPump == null && holdingPipe == null) {
			holdingPump = position.givePump();
		}
	}
    /** 
     * Cso elhelyezese.
     */
	public void PlacePipe() {
		if(holdingPipe!=null) {
			position.placePipe(holdingPipe);
			String hpn = holdingPipe.getName();

			if (position.placePipe(holdingPipe)){
				holdingPipe = null;
				Control.getInstance().appendToLog("Successfully placed "+hpn);
				// System.out.println("Successfully placed "+hpn);
			}
		}else {
			Control.getInstance().appendToLog("No Pipe to place");
			// System.out.println("No Pipe to place");
		}		
	}
    /** 
     * Pumpa elhelyezese.
     */
	public void PlacePump() {
		Element end1 = position.getNeighbors().get(0);
		Element end2 = position.getNeighbors().get(1);
		Pipe createdPipe = position.placePump(holdingPump);
		if (createdPipe != null) {
			Game.getInstance().addPump(holdingPump);
			Game.getInstance().addPipe(createdPipe);
			int PuX = (Gui.getInstance().findElementButton(end1).getX()+Gui.getInstance().findElementButton(end2).getX())/2;
			int PuY = (Gui.getInstance().findElementButton(end1).getY()+Gui.getInstance().findElementButton(end2).getY())/2;
			int PiX = (PuX+Gui.getInstance().findElementButton(end2).getX())/2;
			int PiY = (PuY+Gui.getInstance().findElementButton(end2).getY())/2;
			ElementButton placeButton = Gui.getInstance().findElementButton((Element)position);
			int placeX = (PuX+Gui.getInstance().findElementButton(end1).getX())/2;
			int placeY = (PuY+Gui.getInstance().findElementButton(end1).getY())/2;
			placeButton.setLocation(placeX, placeY);
			Gui.getInstance().createElementButton(holdingPump,PuX,PuY,false);
			Gui.getInstance().createElementButton(createdPipe,PiX,PiY,true);
			holdingPump = null;
		}
	}
	public void makeSticky(){position.stick();}
	void dealDamage() {
		position.damage();
	}
	
	@Override
	public String toString()
	{
		return "R "+this.getName()+" "+position.getName()+" "+((holdingPipe!=null)?(holdingPipe.getName()):("null"))+" "+((holdingPump!=null)?(holdingPump.getName()):("null"));
	}

	public boolean hasHoldingPump(){
		return holdingPump!=null;
	}
}
