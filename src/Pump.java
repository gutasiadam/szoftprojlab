import java.util.List;

public class Pump extends NonPipe implements SaboteurPointSource {
    private Pipe inputPipe;
    private Pipe outputPipe;
    //TODO: private DestructionTimer destructionTimer

    private boolean broken;
    private int leakedWaterAmount;
    
    /** 
     * Ezen keresztul lehet megjavitani.
     */
    @Override
    public void repair(){
        boolean userInput = false; //TODO: stdinrol
        if(userInput){
            broken = false;
        }
    }

    
    /** 
     * Ezen keresztul lehet beallitani a pumpalasi iranyt.
     * @param src - szivasi irany
     * @param dest - nyomasi irany
     */
    @Override
    public void adjust(int src, int dest){
        inputPipe = neighbors.get(src);
        outputPipe = neighbors.get(dest);
    }

    /** 
     * A vizfolyast szimulalja.
     */
    @Override
    public void step(){
        //TODO leakedWaterIncrease ha nincs semmibe kötve
        if(containingWater && !broken){
            if(outputPipe.giveWater()){
                containingWater = false;
            }
        }
        inputPipe.step();
        if(!containingWater && !broken){
            if(inputPipe.waterExtraction()){
                containingWater = true;
            }
        }
        List<NonPipe> inputNeighbors = inputPipe.getNeighbors();
        for(NonPipe np : inputNeighbors){
            np.step();
        }
    }

    /** 
     * Elrontja a pumpat.
     */
    public void breakPump(){ //itt muszaj valami mas nevet hasznalni mert a break kulcsszo
        broken = true;
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
}
