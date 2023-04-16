import java.util.List;

public abstract class Element implements RepairmanPlace, SaboteurPlace{
    protected boolean containingWater;
    protected List<Character> standingOn;
    private String Name;

    String getName() {
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
    public void adjust(int src, int dest) { }

    
    /** 
     * Ot kell meghivni ha ra szeretnek lepni az elemre.
     * @param c - a karakterunk
     * @return boolean - a ralepes sikeressege
     */
    @Override
    public boolean accept(Character c) {
        standingOn.add(c);
        return true;
    }

    
    /** 
     * Ot kell meghivni ha le szeretnenk lepni a mezorol.
     * @param c - a karakterunk
     */
    @Override
    public void remove(Character c) {
        standingOn.remove(c);
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
    public void repair() { }

    
    /** 
     * Ezen keresztul lehet uj pumpat felvenni. Alapertelmezetten nem csinal semmit.
     * @return Pump - az uj pumpa
     */
    @Override
    public Pump givePump() {
        return null;
    }

    
    /** 
     * Ezen keresztul lehet csovet lehelyezni.
     * @param p - a lehelyezni kivant cso
     * @return boolean - a lehelyezes sikeressege
     */
    @Override
    public boolean placePipe(Pipe p) {
        return false;
    }

    /** 
     * Ezen keresztul lehet pumpat lehelyezni.
     * @param p - a lehelyezni kivant pumpa
     * @return Pipe - az ezzel letrehozott cso
     */
    @Override
    public Pipe placePump(Pump p) {
        return null;
    }
}
