public class WaterSource extends NonPipe {

	
	/**
     * @author Szikszai Levente
     * Forras inicializalasa
     */
    WaterSource()
    {
        super();
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
        NonPipe n = holdingPipe.getNeighbors().get(0);
        if(n != this){
            addNeighbor(holdingPipe);
            holdingPipe.addNeighbor(this);
            return true;
        }else{
            return false;
        }
    }
}
