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
		System.out.print("DECISION - A pumpa el van romolva? (I/N) >");
		
		Scanner sc = new Scanner(System.in);
		String brokePump1;
		while(sc.hasNext())
		{
			brokePump1 = sc.next();
			if(brokePump1.equals("I"))
			{
				//System.out.println("1.3.A\t\t"+this.getName()+".broken=true");
				this.broken=true;
				//System.out.println("\t\t"+this.getName()+".broken=false");
				this.broken=false;
				break;
				
			}if(brokePump1.equals("N")) {
				//System.out.println("1.3.B\t\t"+this.getName()+".broken=false");
				break;
			}else {
				System.out.print("\nErvenytelen valasz! Probalkozzon ujra. (I/N)>");
			}
		}
		

		//System.out.println("\t<-"+this.getName()+".repair():void;");
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
    	//Tabulator.increaseTab();
    	
        if(containingWater && !broken){
        	//Tabulator.printTab();
        	//System.out.println("1.5 "+getName()+"->"+outputPipe.getName()+".giveWater()");
        	boolean give = outputPipe.giveWater();
        	
        	
            if(give){
                containingWater = false;
            }
            //Tabulator.printTab();
            //System.out.println(getName()+".containingWater="+containingWater);
        }
        
        //Tabulator.printTab();
        //System.out.println(getName()+".containingWater="+containingWater);
        //Tabulator.printTab();
        //System.out.println("1.6 "+getName()+"->"+inputPipe.getName()+".step()");
        
        inputPipe.step();
        if(!containingWater && !broken){
        	//Tabulator.printTab();
            //System.out.println("1.7 "+getName()+"->"+inputPipe.getName()+".waterExtraction()");
        	boolean extraction = inputPipe.waterExtraction();
        	//Tabulator.printTab();
           // System.out.println("<-"+inputPipe.getName()+".waterExtraction():"+extraction);
            
            
            if(extraction){
                containingWater = true;
            }
            //Tabulator.printTab();
            //System.out.println(getName()+".containingWater="+containingWater);
        }
        
        List<NonPipe> inputNeighbors = inputPipe.getNeighbors();
        for(NonPipe np : inputNeighbors){
        	if(this!=np)
        	{
                //Tabulator.printTab();
                //System.out.println("1.8 "+getName()+"->"+np.getName()+".step()");
        		np.step();
        	}	
        }
        //Tabulator.decreaseTab();
        //Tabulator.printTab();
       // System.out.println("<-"+getName()+".step():void");
    }

    /** 
     * Elrontja a pumpat.
     */
    public void breakPump(){ 
    	 if(broken) {
    		//System.out.println(String.format("A %spumpa mar el van romolva.",getName()));
         }
         else {
             	broken = true;
             	//System.out.println(String.format("A %s pumpa elromlott", getName()));
             }
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
