import java.util.List;

public class WaterSource extends NonPipe {

	
	/**
     * @author Szikszai Levente
     * Forras inicializalasa
     */
    WaterSource()
    {
        super();
    }
    /** @author Bodnar Mark
     * Visszaadja a szomszedait.
     */
    public List<Pipe> getNeighbors(){
        return neighbors;
    }

	
	
    /** 
     * Minden belecsatlakoztatott csovet megtolt vizzel.
     */
    @Override
    public void step() {
        for(Pipe p : neighbors){
        	Tabulator.printTab();
        	System.out.println("1.9 "+getName()+"->"+p.getName()+".giveWater()");
            p.giveWater();
        }
        Tabulator.printTab();
        System.out.println("<-"+getName()+".step():void");
    }

    
    /** 
     * Ezen keresztul lehet bele csovet kotni.
     * @param holdingPipe - belekotni kivant cso
     * @return boolean - sikeres-e a csobekotes
     */
    @Override
    public boolean placePipe(Pipe holdingPipe){
    	boolean out;
    	Tabulator.increaseTab();
    	Tabulator.printTab();
    	System.out.println(getName()+"->"+holdingPipe.getName()+".getNeighbors().get(0)");
    	
        NonPipe n = holdingPipe.getNeighbors().get(0);
        
        Tabulator.printTab();
        System.out.println("<-"+holdingPipe.getName()+".getNeighbors().get(0):"+n.getName());
        if(n != this){
        	Tabulator.printTab();
        	System.out.println("1.2A "+getName()+"->"+getName()+".addNeighbor("+holdingPipe.getName()+")");
            addNeighbor(holdingPipe);
            Tabulator.printTab();
            System.out.println("<-"+getName()+".addNeighbor("+holdingPipe.getName()+")");
            
            Tabulator.printTab();
            System.out.println(getName()+"->"+holdingPipe.getName()+".addNeighbor("+getName()+")");
            holdingPipe.addNeighbor(this);
            Tabulator.printTab();
            System.out.println("<-"+holdingPipe.getName()+".addNeighbor("+getName()+")");
            out =  true;
        }else{
        	Tabulator.printTab();
        	System.out.println("1.2B");
            out =  false;
        }
        Tabulator.decreaseTab();
        Tabulator.printTab();
        System.out.println("<-"+getName()+".placePipe():"+out);
        return out;
    }
}
