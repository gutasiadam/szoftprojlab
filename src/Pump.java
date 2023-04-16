import java.util.List;
import java.util.Scanner;

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
		System.out.println("DECISION - A pumpa el van romolva? (I/N) >");
		
		Scanner sc = new Scanner(System.in);
		String brokePump1;
		while(sc.hasNext())
		{
			brokePump1 = sc.next();
			if(brokePump1.equals("I"))
			{
				System.out.println("broken=true");
				this.broken=true;
				System.out.println("broken=false");
				this.broken=false;
				break;
				
			}if(brokePump1.equals("N")) {
				System.out.println("broken=false");
				break;
			}else {
				System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
			}
		}
		

		System.out.println("<-.repair():void;"); //TODO: valahogy printelődjön ki, hogy ki hivta meg, es az objektum neve is latszodjon
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
