import java.util.List;
import java.util.Scanner;

public class Pump extends NonPipe implements SaboteurPointSource {
    private Pipe inputPipe;
    private Pipe outputPipe;
    //TODO: private DestructionTimer destructionTimer

    private boolean broken;
    private int leakedWaterAmount;
    
    /**
     * @author Szikszai Levente
     * Inicializalja a Pumpat
     */
    Pump()
    {
        super();
        broken = false;
        leakedWaterAmount=0;
    }

    /**
     * Betöltésnél beállítja a Pumpa állapoát
     * @param isBroken broken
     * @param isContainingWater containingWater
     * @param leakedWater leakedWaterAmount
     */
    Pump(boolean isBroken, boolean isContainingWater, int leakedWater)
    {
        super();
        containingWater=isContainingWater;
        broken = isBroken;
        leakedWaterAmount = leakedWater;
    }

    /** @author Bodnar Mark
     * Visszaadja a szomszedait.
     */
    public List<Pipe> getNeighbors(){
        return neighbors;
    }
    
    /** 
     * Pumpa megjavitása
     */
    @Override
    public void repair(){
        if(this.broken){
            this.broken=false;
            System.out.println( "Successfully repaired " +this.getName());
        }else{
            System.out.println(this.getName()+" not broken");
        }
    }

    
    /** 
     * Ezen keresztul lehet beallitani a pumpalasi iranyt.
     * @param src - szivasi irany
     * @param dest - nyomasi irany
     */
    @Override
    public void adjust(int src, int dest){
        if(src<neighbors.size() && dest<neighbors.size()){
            inputPipe = neighbors.get(src);
            outputPipe = neighbors.get(dest);
            System.out.println("Input set "+neighbors.get(src).getName()+" Output set "+ neighbors.get(dest).getName());
        }else{
            System.out.println("Invalid parameters, nothing changed");
        }
    }

    /** 
     * A vizfolyast szimulalja.
     */
    @Override
    public void step(){
        if(containingWater && !broken){
            if(outputPipe.giveWater()){
                containingWater=false;
            }
        }
        if(!containingWater && !broken){
            if(inputPipe.waterExtraction()){
                containingWater=true;
            }
        }
    }

    /** 
     * Elrontja a pumpat.
     */
    public void breakPump(){ 
    	 broken=true;
    }
    public void stick() {};/** Ragadossa teszi az adott poziciot. */
    public void slime(){};/** Csuszossa tesz egy csovet. */

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

    @Override
    public String toString()
    {
        return "Pu "+this.getName()+" "+broken+" "+containingWater+" "+leakedWaterAmount;
    }
}
