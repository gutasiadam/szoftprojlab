import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
 * Kozos absztrakt ose a palyaelemeknek. Megvalositja a karakterek szamara szukseges fuggvenyeket, 
 * azok alap implementaciot megvalositja.
 * Last modified: @author Gutasi Adam
 * @author Szikszai Levente
 * Inicializalja az Element-et
 */
public abstract class Element implements RepairmanPlace, SaboteurPlace{
    protected boolean containingWater; /** Eltarolja, hogy van-e benne viz. */
    protected List<Character> standingOn; /** Eltarolja, hogy kik allnak rajta  */
    private String Name; /** Az objektum neve a Jatek soran */

    //Tárolja, hogy adott action-t végre tud-e hajtani
    protected HashMap<String,Boolean> canPerformAction = new HashMap<String,Boolean>();

    Element()
    {
        containingWater = false;
        standingOn= new ArrayList<Character>();
    }

    /**
     * Beállítja milyen action-t lehet végrehajtani az adott Element-en. True ha végre lehet rajta hajtani, false ha nem.
     * @param move Rálépés
     * @param stab Lyukasztás
     * @param pickuppipe Cső felvétele róla
     * @param pickuppump Pumpa felvétele róla
     * @param placepump Pumpa elhelyezése rá
     * @param placepipe Cső bekötése
     * @param repair Megjavítás
     * @param slime Csúszóssá tétel
     * @param stick Ragadóssá tétel
     * @param adjust Be és kimenet állítása
     */
    protected void setAvailableActions(boolean move,boolean stab,boolean pickuppipe, boolean pickuppump,
    boolean placepump, boolean placepipe, boolean repair, boolean slime, boolean stick, boolean adjust)
    {
        canPerformAction.put("move", move);
        canPerformAction.put("stab", stab);
        canPerformAction.put("pickuppipe", pickuppipe);
        canPerformAction.put("pickuppump", pickuppump);
        canPerformAction.put("placepump", placepump);
        canPerformAction.put("placepipe", placepipe);
        canPerformAction.put("repair", repair);
        canPerformAction.put("slime", slime);
        canPerformAction.put("stick", stick);
        canPerformAction.put("adjust", adjust);
    }

/**
 * Visszaadja, hogy a paraméterül adott Action véggrehajtható-e az adott Element-en
 * @param actionName Az Action neve amire kíváncsiak vagyunk
 * @return true ha végrehajtható, false ha nem, null ha nincs ilyen action
 */
    public boolean canPerformAction(String actionName)
    {
        return canPerformAction.get(actionName.toLowerCase());
    }

    
    
    public String getName() {
    	return Name;
    }
    
    void setName(String name) {
    	this.Name=name;
    }
    
    public abstract void step();

    
    /** 
     * Ezen keresztul lehet beallitani a pumpalasi iranyt.
     * A pumpa valositja csak meg. A tobbi osztaly nem csinal semmit.
     * @param src - input irany
     * @param dest - output irany
     */
    @Override
    public void adjust(int src, int dest) { 
    	System.out.println(this.Name+" is not adjustable.");
    }

    
    /** 
     * Ot kell meghivni ha ra szeretnek lepni az elemre.
     * @param c - a karakterunk
     * @return boolean - a ralepes sikeressege
     */
    @Override
    public boolean accept(Character c) {
        standingOn.add(c);
        System.out.println("Successfully moved to "+ this.getName());
        return true;
    }

    
    /** 
     * Ot kell meghivni ha le szeretnenk lepni a mezorol.
     * @param c - a karakterunk
     */
    @Override
    public boolean remove(Character c) {
        return standingOn.remove(c);
    }

    /** 
     * Ezen keresztul lehet elrontani. Alapertelmezetten nem csinal semmit.
     */
    @Override
    public void damage() {
    	System.out.println("Ilyen elemet nem lehet kilyukasztani!");
    }

    /** 
     * Ezen keresztul lehet megjavitani. Alapertelmezetten nem csinal semmit.
     */
    @Override
    public void repair() { 
    	System.out.println(this.Name+" unrepairable");
    }

    
    /** 
     * Ezen keresztul lehet uj pumpat felvenni. Alapertelmezetten nem csinal semmit.
     * @return Pump - az uj pumpa
     */
    @Override
    public Pump givePump() {
        System.out.println("Can't pick up Pump here");
        return null;
    }

    
    /** 
     * Ezen keresztul lehet csovet lehelyezni.
     * @param p - a lehelyezni kivant cso
     * @return boolean - a lehelyezes sikeressege
     */
    @Override
    public boolean placePipe(Pipe p) {
    	System.out.println("Can't place"+p.getName()+" on "+this.Name);
        return false;
    }

    /** 
     * Ezen keresztul lehet pumpat lehelyezni.
     * @param p - a lehelyezni kivant pumpa
     * @return Pipe - az ezzel letrehozott cso
     */
    @Override
    public Pipe placePump(Pump p) {
    	System.out.println("Can't place"+p.getName()+" on "+this.Name);
        return null;
    }
    
    public void stick() {
    	System.out.println(this.Name+" can't be sticky");
    }
    
    public void slime() {
    	System.out.println(this.Name+" can't be slimey");
    }
    
    /**
     * Az ott irányban belekötött csövet adja vissza, és lecsatlakoztatja magáról.
     * Castolgatásokkal lehet hogy még baj lesz
     *  */
    public Pipe lift(int dir) {
    	try {
    		Pipe n =(Pipe) this.getNeighbors().get(dir);
        	if(n!=null) {
        		this.getNeighbors().remove(dir);
        		n.removeNeighbor((NonPipe) this); // n. removeNeighbor(onmaga)?
            	System.out.println("Successfully picked up "+n.getName());
        		return n;
        	}else {
        		System.out.println("Invalid object to pick up");
        		return null;
        	}
    	}catch(IndexOutOfBoundsException e) {
    		//ha ervenytelen az index
    		System.out.println("Invalid object to pick up");
    		return null;
    	}

    }

    public List<Character> getStandingOn(){
        return standingOn;
    }

    public void addStandingOn(Character c){
        standingOn.add(c);
    }
}
