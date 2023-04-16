import java.util.List;

public class Cistern extends NonPipe {
    private int waterFlown;

    
   /** @author Szikszai Levente
    * Inicializalja a Ciszternat
    */
   Cistern()
   {
       super();
       waterFlown = 0;
   }

    
    /** 
     * Szimulalja a viz folyasat.
     */
    @Override
    public void step() {
    	System.out.println("Game->"+getName()+".step()");
    	Tabulator.increaseTab();
		Tabulator.printTab();
        for (Pipe p : neighbors) {
        	System.out.println(getName()+"->"+p.getName()+".step()");
            p.step();
            Tabulator.printTab();
            System.out.println(getName()+"->"+p.getName()+".waterExtraction()");
            boolean extraction = p.waterExtraction();
            if(extraction) waterFlown++;
            Tabulator.printTab();
            System.out.println("<-"+p.getName()+".waterExtraction()");
            Tabulator.printTab();
            System.out.println("waterFlown="+waterFlown);
            
            List<NonPipe> pipeNeighbors = p.getNeighbors();
            for(NonPipe np : pipeNeighbors){
            	if(this!=np)
            	{
            		np.step();
            	}	
            }
        }
        System.out.print("<-"+getName()+".step()\n\t");
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
        Pump p = new Pump();
        p.setName("p");
        System.out.println(String.format("\t\t1.3 %s: Pump %s created!", getName(), p.getName()));
        return p;
    }
}
