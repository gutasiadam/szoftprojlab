import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pipe extends Element implements SaboteurPointSource{
    private boolean holeOnPipe;
    private int leakedWaterAmount;
    private int sticky;
    private int slimey;
    private List<NonPipe> neighbors;
    
    
    
    /**
     * @author Szikszai Levente
     * Inicializalja a Pipe-ot
     */
    Pipe()
    {
        neighbors = new ArrayList<NonPipe>();
        leakedWaterAmount = 0;
        holeOnPipe = false;
    }

    /**
     * Beállítja a betöltés során a Pipe változóit.
     * @param hole holeOnPipe
     * @param leakedWater leakedWaterAmount
     * @param _slimey _slimey
     * @param _sticky sticky
     */
    Pipe(boolean hole,int leakedWater,int _slimey,int _sticky)
    {
        neighbors = new ArrayList<NonPipe>();
        leakedWaterAmount = leakedWater;
        holeOnPipe = hole;
        slimey = _slimey;
        sticky = _sticky;
    }
    
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
        Tabulator.increaseTab();
        Tabulator.printTab();
        System.out.println("containingWater="+containingWater);
        Tabulator.printTab();
        System.out.println("leakedWaterAmount="+leakedWaterAmount);
        Tabulator.decreaseTab();
        Tabulator.printTab();
        System.out.println("<-"+getName()+".step()");
    }

    /** 
     * Megfoltozza a csovet.
     */
    @Override
    public void repair(){
		System.out.print("DECISION - A cső ki van lyukadva? (I/N) >");
		
		Scanner sc = new Scanner(System.in);
		String brokePipe1;
		while(sc.hasNext())
		{
			brokePipe1 = sc.next();
			if(brokePipe1.equals("I"))
			{
				System.out.println("1.3.A\t\t"+this.getName()+".broken=true");
				this.holeOnPipe=true;
				System.out.println("\t\t"+this.getName()+".broken=false");
				this.holeOnPipe=false;
				break;
				
			}if(brokePipe1.equals("N")) {
				System.out.println("1.3.B\t\t"+this.getName()+".broken=false");
				break;
			}else {
				System.out.print("\nErvenytelen valasz! Probalkozzon ujra. (I/N)>");
			}
		}
		

		System.out.println("\t<-"+this.getName()+".repair():void;");
    }

    /** 
     * Kilyukasztja a csovet.
     */
    @Override
    public void damage(){
    	System.out.println("DECISION-A "+getName()+" kilyukasztasahoz irja be a 0-as szamot, majd enter! (csak szamok elfogadhatoak)");
        Scanner userInput = new Scanner(System.in);
        int input = 0;
        //if(userInput.hasNextInt())
        input= userInput.nextInt();
        
        if(holeOnPipe) {
        	System.out.println(getName()+" mar lyukas.");
        }
        else {
            if(input==0) {
            	holeOnPipe = true;
            	System.out.println("Kilyukasztottad "+getName()+"-et");
            }
            else System.out.println("Nem tortent lyukasztas.");
            //userInput.close();
        }
    }
    
    /** 
     * Letrehoz egy uj csovet, majd koze es a meglevo cso koze lehelyezi a pumpat.
     * @param holdingPump - a lehelyezni kivant pumpa
     * @return Pipe - az ujonnan letrehozott cso
     */
    @Override
    public Pipe placePump(Pump holdingPump) {
        NonPipe n;
        try{
            n = (NonPipe)getNeighbors().get(0);
        }catch(Exception e){
            n = null;
        }
        
        if(n != null){
            System.out.println(String.format("\t\t1.3 %s: %s = neighbors[0]", getName(), n.getName()));
            
            System.out.println(String.format("\t\t1.4 %s->%s.removeNeighbor(%s)", getName(), getName(), n.getName()));
            removeNeighbor(n);
            System.out.println(String.format("\t\t%s<-%s.removeNeighbor(%s)", getName(), getName(), n.getName()));
            
            System.out.println(String.format("\t\t1.5 %s->%s.removeNeighbor(%s)", getName(), n.getName(), getName()));
            n.removeNeighbor(this);
            System.out.println(String.format("\t\t%s<-%s.removeNeighbor(%s)", getName(), n.getName(), getName()));

            System.out.println(String.format("\t\t1.6 %s->%s.addNeighbor(%s)", getName(), holdingPump.getName(), getName()));
            holdingPump.addNeighbor(this);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), holdingPump.getName(), getName()));

            System.out.println(String.format("\t\t1.7 %s->%s.addNeighbor(%s)", getName(), getName(), holdingPump.getName()));
            addNeighbor(holdingPump);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), getName(), holdingPump.getName()));

            Pipe p = new Pipe();
            p.setName("p");
            System.out.println(String.format("\t\t1.8 %s: Pipe %s created!", getName(), p.getName()));

            System.out.println(String.format("\t\t1.9 %s->%s.addNeighbor(%s)", getName(), p.getName(), n.getName()));
            p.addNeighbor(n);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), p.getName(), n.getName()));

            System.out.println(String.format("\t\t1.10 %s->%s.addNeighbor(%s)", getName(), n.getName(), p.getName()));
            n.addNeighbor(p);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), n.getName(), p.getName()));

            System.out.println(String.format("\t\t1.11 %s->%s.addNeighbor(%s)", getName(), holdingPump.getName(), p.getName()));
            holdingPump.addNeighbor(p);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), holdingPump.getName(), p.getName()));

            System.out.println(String.format("\t\t1.12 %s->%s.addNeighbor(%s)", getName(), p.getName(), holdingPump.getName()));
            p.addNeighbor(holdingPump);
            System.out.println(String.format("\t\t%s<-%s.addNeighbor(%s)", getName(), p.getName(), holdingPump.getName()));

            return p;
        }
        System.out.println(String.format("\t\t1.3 %s: neighbors[0] is null", getName()));
        return null;
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
     * Uj szomszed hozzacsatlakoztatasa a csohoz.
     * @param n - a csatlakoztatni kivant szomszed
     */
    public void addNeighbor(NonPipe n){
        if(neighbors.size()<2){
        	//System.out.println("\t\t\t"+this.getName()+"->"+"neighbors.add("+n.getName()+")");
            neighbors.add(n);
            //System.out.println("\t\t\t+"+this.getName()+".neighbors.get(2)=="+n.getName()+" ?" +(this.neighbors.get(0)==n));
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
    	Tabulator.increaseTab();
    	Tabulator.printTab();
    	
    	
        if(containingWater){
            containingWater = false;
            System.out.println(getName()+".containingWater="+containingWater);
            Tabulator.decreaseTab();
            return true;
        }
        else
        {
        	System.out.println(getName()+".containingWater="+containingWater);
        	Tabulator.decreaseTab();
        	return false;
        }
        
    }

    /** 
     * Vizet probal a csobe tenni
     * @return boolean - sikerult-e bele vizet tenni
     */
    public boolean giveWater(){
    	Tabulator.increaseTab();
    	boolean out = false;
        if(containingWater)
        {
        	out = false;
        }
        else
        {
        	 containingWater = true;
        	out = true;
        }
        Tabulator.printTab();
    	System.out.println(getName()+".containingWater="+containingWater);
    	Tabulator.decreaseTab();
    	Tabulator.printTab();
    	System.out.println("<-"+getName()+".giveWater():"+out);
        return out;
       
    }
    public void stick() {};/** Ragadossa teszi az adott poziciot. */
    public void slime(){};/** Csuszossa tesz egy csovet. */

    /** 
     * Visszaadja a szomszedait
     * @return List<NonPipe> - a szomszedok
     */
    public List<NonPipe> getNeighbors(){
        return neighbors;
    }


    @Override 
    public String toString()
    {
        return "Pi "+this.getName()+" "+holeOnPipe+" "+leakedWaterAmount+" "+slimey+" "+sticky;
    }
}
