import java.util.ArrayList;
import java.util.List;

public abstract class NonPipe extends Element {
    protected List<Pipe> neighbors;

    /**
     * @author Szikszai Levente
     *         Nonpipe inicializalasa
     */
    NonPipe() {
        super();
        neighbors = new ArrayList<Pipe>();
    }

    /**
     * Ezen keresztul lehet hozza csovet csatlakoztatni.
     * 
     * @param n - a csatlakoztatni kivant cso
     */
    public void addNeighbor(Pipe n) {
        neighbors.add(n);
    }

    /**
     * Ezen keresztul lehet rola csovet lecsatlakoztatni.
     * 
     * @param n - a lecsatlakoztatni kivant cso
     */
    public void removeNeighbor(Pipe n) {
        neighbors.remove(n);
    }

    /**
     * Ezen keresztul lehet valamelyik belecsatlakoztatott csovet felemelni.
     * 
     * @param dir - a felemelni kivant cso iranya
     * @return Pipe - a felemelt cso
     */
    @Override
    public Pipe lift(int dir) {
        try{
        	Pipe n = neighbors.get(dir);
            if (n != null) {
                removeNeighbor(n);
                n.removeNeighbor(this);
                return n;
            }
    	}catch(IndexOutOfBoundsException e) {
    		//ha ervenytelen az index
    		System.out.println("Invalid object to pick up");
    		return null;
    	}
        System.out.println("Invalid object to pick up");
        return null;
    }

    /**
     * Ezen keresztul lehet csovet lehelyezni.
     * 
     * @param holdingPipe - a tartott cso
     * @return boolean - a lehelyezes sikeressege
     */
    @Override
    public boolean placePipe(Pipe holdingPipe) {
        if (holdingPipe != null) {
            addNeighbor(holdingPipe);
            holdingPipe.addNeighbor(this);
            return true;
        }
        return false;
    }
}
