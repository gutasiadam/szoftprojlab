import java.util.List;
import java.util.Scanner;

/**
 * A Pumpákat reprezentáló osztály.
 */
public class Pump extends NonPipe implements SaboteurPointSource {
    private Pipe inputPipe;
    private Pipe outputPipe;
    private boolean broken;
    private int leakedWaterAmount;
    private int capacity = (int)(Math.random() * ((10 - 4) + 1)) + 4;
    
    /**
     * @author Szikszai Levente
     * Inicializalja a Pumpat
     */
    Pump()
    {
        super();
        broken = false;
        leakedWaterAmount=0;

        if(neighbors.size()>=1)
        {
            inputPipe=neighbors.get(0);
            outputPipe = neighbors.get(0);
        }

        if(neighbors.size()>=2)
        {
            outputPipe = neighbors.get(1);
        }
        setAvailableActions(true, false, true, false, false, true, true, false, false, true);
    }

    /**
     * Betöltésnél beállítja a Pumpa állapoát
     * Ha a pumpának van legalább 1 szomszédja arra állítja a ki és bemeneti csövét,
     *  ha van legalább 2 szomszédja akkor a kimeneti cső a 2. lesz
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
        setAvailableActions(true, false, true, false, false, true, true, false, false, true);
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
            Control.getInstance().appendToLog("Successfully repaired " +this.getName());
            //System.out.println( "Successfully repaired " +this.getName());
        }else{
            Control.getInstance().appendToLog(this.getName()+" not broken");
            //System.out.println(this.getName()+" not broken");
        }
    }


    /**
     * Meghívja az ős addNeighbor függvényét és
     * beállítja a pumpa alap ki és bemeneti csövét.
     * Betöltés miatt szükséges.
     * @param pipe a hozzáadandó szomszéd
     */
    @Override
    public void addNeighbor(Pipe pipe)
    {
        if(neighbors.size()<capacity){
            super.addNeighbor(pipe);
            if(neighbors.size()>=1)
            {
                inputPipe=neighbors.get(0);
                outputPipe = neighbors.get(0);
            }

            if(neighbors.size()>=2)
            {
                outputPipe = neighbors.get(1);
            }
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
            try {
                Control.getInstance().appendToLog("Input set " + neighbors.get(src).getName() + " Output set " + neighbors.get(dest).getName());
            } catch (Exception e) {
                //
            }
            
            //System.out.println("Input set "+neighbors.get(src).getName()+" Output set "+ neighbors.get(dest).getName());
        }else{
            Control.getInstance().appendToLog("Invalid parameters, nothing changed");
            //System.out.println("Invalid parameters, nothing changed");
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

    /**
	 * Az osztály fontosabb attribútumait összefűzve adja vissza egy String-gé
	 * @return String
	 */
    @Override
    public String toString()
    {
        return "Pu "+this.getName()+" "+broken+" "+containingWater+" "+leakedWaterAmount;
    }

    /**
	 * Lekérdezi, hogy el van-e romolva
	 * @return boolean - elromlott-e
	 */
    public boolean getBroken(){
        return broken;
    }

    /**
	 * Lekérdezi, hogy tartalmaz-e vizet
	 * @return boolean - van-e benne víz
	 */
    public boolean getContainingWater(){
        return containingWater;
    }

    /**
     * Visszaadja a bemeneti csövet
     * @return
     */
    public Pipe getSrc(){
        return inputPipe; 
    }

    /**
     * Visszaadja a kimeneti csövet
     * @return
     */
    public Pipe getDest(){
        return outputPipe; 
    }

    /**
     * Visszaadja a pumpa kapaicitását
     * @return
     */
    public int getCapacity(){
        return capacity;
    }
}
