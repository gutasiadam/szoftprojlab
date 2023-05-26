import java.util.List;

/**
 * A viz szimulacioja innen indul. A belefolyt viz mennyiseget tarolja.
 * A jatekosok veges szamban ra tudnak lepni. Uj csovek jonnek letre rajta, melzek egzik vege bele, masik vege
 * a sivatagba vezet. A Szerelo jatekosok felvehetnek innen uj pumpat.
 * Last modified: @author Gutasi Adam
 *
 */
public class Cistern extends NonPipe {
    private int waterFlown;

    
   /** @author Szikszai Levente
    * Inicializalja a Ciszternat
    */
   Cistern()
   {
       super();
       waterFlown = 0;
       setAvailableActions(true, false, true, true, false, true, false, false, false, false);
   }

   /**
    * Betöltésnél beállítja waterFlown-értékét.
    * @param wf waterFlown új értéke
    */
   Cistern(int wf)
   {
       super();
       waterFlown = wf;
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
        for (Pipe p : neighbors) {
            p.step();
            boolean extraction = p.waterExtraction();
            if(extraction) waterFlown++;
            
            List<NonPipe> pipeNeighbors = p.getNeighbors();
            for(NonPipe np : pipeNeighbors){
            	if(this!=np)
            	{
            		np.step();
            	}	
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
            createdPipe.addNeighbor(this);
            
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
        int rnd = (int)(Math.random() * 1000) + 100;
        p.setName("p"+rnd);
        System.out.println("Successfully picked up "+p.getName());
        return p;
    }

    /**
	 * Az osztály fontosabb attribútumait összefűzve adja vissza egy String-gé
	 * @return String
	 */
    @Override
    public String toString()
    {
        return "C "+this.getName()+" "+waterFlown;
    }
}
