import java.util.List;

public class Cistern extends NonPipe {
    private int waterFlown;

    /** 
     * Szimulalja a viz folyasat.
     */
    @Override
    public void step() {
        for (Pipe p : neighbors) {
            p.step();
            if(p.waterExtraction()) waterFlown++;
            List<NonPipe> pipeNeighbors = p.getNeighbors();
            for(NonPipe np : pipeNeighbors){
                np.step();
            }
        }
    }

    
    /** 
     * Visszaadja a ciszternaba befolyt viz mennyiseget, es nullaza a szamlalot.
     * @return int - a befolyt viz mennyisege
     */
    public int measureAndResetWaterFlown(){
        int wf = waterFlown;
        waterFlown = 0;
        return wf;
    }

    
    /** 
     * Letrehoz egy uj csovet, ami felig szabad, es felig a ciszternaba van kotve, ha nem letezik masik ilyen cso.
     * @return Pipe - az ujonnan letrehozott cso
     */
    public Pipe newPipe(){
        boolean letezik = false;
        for(Pipe p : neighbors){
            if(p.getNeighbors().get(1) == null){
                letezik = true;
            }
        }
        if(!letezik){
            Pipe createdPipe = new Pipe();
            createdPipe.addNeighbor(this);
            return createdPipe; //TODO: kell returnolni?
        }
        return null;
    }

    
    /** 
     * Letrehoz egy pumpat, es visszadja azt.
     * @return Pump - az uj pumpa
     */
    @Override
    public Pump givePump(){
        return new Pump();
    }
}
