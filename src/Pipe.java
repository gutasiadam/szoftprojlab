import java.util.List;

public class Pipe extends Element implements SaboteurPointSource{
    private boolean holeOnPipe;
    private int leakedWaterAmount;
    private List<NonPipe> neighbors;
    
    
    /** 
     * Ot kell meghivni ha ra szeretnek lepni a csore.
     * @param c - a karakterunk
     * @return boolean - a ralepes sikeressege
     */
    @Override
    public boolean accept(Character c){
        if(standingOn.size()<1){
            standingOn.add(c);
            return true;
        }
        return false;
    }

    /** 
     * Ha lyukas, kifolyatja magabol a vizet, es noveli a kifolyt viz mennyiseget.
     */
    @Override
    public void step() {
        if(holeOnPipe){
            containingWater = false;
            leakedWaterAmount++;
        }
    }

    /** 
     * Megfoltozza a csovet.
     */
    @Override
    public void repair(){
        boolean userInput = false; //TODO: stdinrol
        if(userInput){
            holeOnPipe = false;
        }
    }

    /** 
     * Kilyukasztja a csovet.
     */
    @Override
    public void damage(){
        boolean userInput = false; //TODO: stdinrol
        if(userInput) holeOnPipe = true;
    }
    
    /** 
     * Letrehoz egy uj csovet, majd koze es a meglevo cso koze lehelyezi a pumpat.
     * @param holdingPump - a lehelyezni kivant pumpa
     * @return Pipe - az ujonnan letrehozott cso
     */
    @Override
    public Pipe placePump(Pump holdingPump) {
        NonPipe n = (NonPipe)getNeighbors().get(0);
        if(n != null){
            removeNeighbor(n);
            n.removeNeighbor(this);
            holdingPump.addNeighbor(this);
            addNeighbor(holdingPump);
            Pipe p = new Pipe();
            p.addNeighbor(n);
            n.addNeighbor(p);
            holdingPump.addNeighbor(p);
            p.addNeighbor(holdingPump);
            return p;
        }else{
            return null;
        }
    }
    
    /** 
     * Olyan cso felemelesenel hasznaljuk, amelyiknek az egyik fele nincs sehova bekotve.
     * @param dir - nem hasznaljuk ebben a megvalositsban
     * @return Pipe - a cso, amit felemelunk
     */
    @Override
    public Pipe lift(int dir){
        NonPipe n1 = neighbors.get(0);
        NonPipe n2 = neighbors.get(1);
        if(n1 == null || n2 == null){
            return this;
        }
        return null;
    }

    /** 
     * Ezen keresztul lehet hozza szomszedot csatlakoztatni.
     * @param n - a csatlakoztatni kivant szomszed
     */
    public void addNeighbor(NonPipe n){
        if(neighbors.size()<2){
            neighbors.add(n);
        }
    }

    /** 
     * Ezen keresztul lehet rola szomszedot lecsatlakoztatni.
     * @param n - a lecsatlakoztatni kivant szomszed
     */
    public void removeNeighbor(NonPipe n) {
        neighbors.remove(n);
    }

    /** 
     * Visszadja a kifolyott viz mennyiseget, majd nullara allitja
     * @return int - a kifolyott viz mennyisege
     */
    @Override
    public int measureAndResetLeakedWaterAmount(){
        int lkdwtramt = leakedWaterAmount;
        leakedWaterAmount = 0;
        return lkdwtramt;
    }

    /** 
     * Kiszivja az adott csobol a vizet
     * @return boolean - volt-e benne viz
     */
    public boolean waterExtraction(){
        if(containingWater){
            containingWater = false;
            return true;
        }
        return false;
    }

    /** 
     * Vizet probal a csobe tenni
     * @return boolean - sikerult-e bele vizet tenni
     */
    public boolean giveWater(){
        if(containingWater) return false;
        containingWater = true;
        return true;
    }

    /** 
     * Visszaadja a szomszedait
     * @return List<NonPipe> - a szomszedok
     */
    public List<NonPipe> getNeighbors(){
        return neighbors;
    }
}
