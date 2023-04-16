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
   /** @author Bodnar Mark
    * Visszaadja a szomszedait.
    */
   public List<Pipe> getNeighbors(){
       return neighbors;
   }

    
    /** 
     * Szimulalja a viz folyasat.
     */
    @Override
    public void step() {
    	System.out.println("1.1 Game->"+getName()+".step()");
    	Tabulator.increaseTab();
		Tabulator.printTab();
        for (Pipe p : neighbors) {
        	System.out.println("1.2 "+getName()+"->"+p.getName()+".step()");
            p.step();
            Tabulator.printTab();
            System.out.println("1.3 "+getName()+"->"+p.getName()+".waterExtraction()");
            boolean extraction = p.waterExtraction();
            if(extraction) waterFlown++;
            Tabulator.printTab();
            System.out.println("<-"+p.getName()+".waterExtraction():"+extraction);
            Tabulator.printTab();
            System.out.println(getName()+".waterFlown="+waterFlown);
            
            List<NonPipe> pipeNeighbors = p.getNeighbors();
            for(NonPipe np : pipeNeighbors){
            	if(this!=np)
            	{
            		Tabulator.printTab();
            		System.out.println("1.4 "+getName()+"->"+np.getName()+".step()");
            		np.step();
            	}	
            }
        }
        Tabulator.decreaseTab();
        Tabulator.printTab();
        System.out.println("<-"+getName()+".step():void");
    }

    
    /** 
     * Visszaadja a ciszternaba befolyt viz mennyiseget, es nullaza a szamlalot.
     * @return int - a befolyt viz mennyisege
     */
    public int measureAndResetWaterFlown(){
    	System.out.println("\t\t\t->"+this.getName()+".measureAndResetWaterFlown()");
        int wf = waterFlown;
        waterFlown = 0;
        System.out.println("\t\t\t<-"+this.getName()+".measureAndResetWaterFlown(): "+wf);
        return wf;
    }

    
    /** 
     * Letrehoz egy uj csovet, ami felig szabad, es felig a ciszternaba van kotve, ha nem letezik masik ilyen cso.
     * @return Pipe - az ujonnan letrehozott cso
     */
    public Pipe newPipe(){
        boolean letezik = false;
        /** Ha létezik ilyen félig szabad cső, nem hoz létre újat. */
        for(Pipe p : neighbors){
            if(p.getNeighbors().get(1) == null){
                letezik = true;
            }
        }
        /** Ha nem létezik a cső, létrehoz egyet */
        if(!letezik){
        	
            Pipe createdPipe = new Pipe();
            createdPipe.setName("createdPipe1");
            System.out.println("1.3.1\t\t"+this.getName()+"-> <<create>>: "+createdPipe.getName());
            System.out.println("\t\t"+this.getName()+"->"+createdPipe.getName()+".addNeighbor("+this.getName()+")");
            createdPipe.addNeighbor(this);
            System.out.println("1.4.1\t\t"+this.getName()+"<-"+createdPipe.getName()+".addNeighbor("+this.getName()+"): void");
            
            return createdPipe;
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
