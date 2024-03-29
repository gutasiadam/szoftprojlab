import java.util.List;
/**
 * Vegtelen vizforras. Eltarolja a szomszedos csoveket es belejuk vizet pumpal. Veges szamu Jatekos ra tud lepni.
 *
 */
public class WaterSource extends NonPipe {

	
	/**
     * @author Szikszai Levente
     * Forras inicializalasa
     */
    WaterSource()
    {
        super();
        setAvailableActions(true, false, true, false, false, true, false, false, false, false);
    }
    /** @author Bodnar Mark
     * Visszaadja a szomszedait.
     */
    public List<Pipe> getNeighbors(){
        return neighbors;
    }

	/** 
     * Minden belecsatlakoztatott csovet megtolt vizzel.
     */
    @Override
    public void step() {
        for(Pipe p : neighbors){
            p.giveWater();
        }
    }

    
    /** 
     * Ezen keresztul lehet bele csovet kotni.
     * @param holdingPipe - belekotni kivant cso
     * @return boolean - sikeres-e a csobekotes
     */
    @Override
    public boolean placePipe(Pipe holdingPipe){
    	boolean out;
    	
        NonPipe n = holdingPipe.getNeighbors().get(0);
        
        if(n != this){
            addNeighbor(holdingPipe);
            holdingPipe.addNeighbor(this);
            out =  true;
            Control.getInstance().appendToLog("Successfully placed"+holdingPipe.getName());
            // System.out.println("Successfully placed"+holdingPipe.getName());
        }else{
            out =  false;
        }
        return out;
    }

    /**
	 * Az osztály fontosabb attribútumait összefűzve adja vissza egy String-gé
	 * @return String
	 */
    @Override
    public String toString()
    {
        return "W "+this.getName();
    }
}
