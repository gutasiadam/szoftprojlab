import java.util.ArrayList;
/**
 * 
 * Idozito, mely minden jatekos lepese elott, veletlen esellyel elrontja az egyes pumpakat.
 * Last modified: @author Gutasi Adam
 * @author Szikszai Levente
 */
public class DestructionTimer {

	/**A tarolt pumpak, melyeket elronthat az idozito.*/
	private ArrayList<Pump> pumps;
	private String Name; /** Az idozito objektum neve a jatek soran. pl.: Timer1 */
	
	
	/**
	 * Létrehoz egy DestructionTimer-t.
	 */
	public DestructionTimer()
	{
		pumps=new ArrayList<Pump>();
	}
	/**
	 * A teszthez lesz a nevre szukseg
	 */
    String getName() {
    	return Name;
    }
    
    /**
     * Pumpa nevenek beallitasa.. String tipusu paramterkent varja a pumpa nevet.
     * @param name
     */
    void setName(String name) {
    	this.Name=name;
    }
	/**
	 * Bizonyos eséllyel elrontja a pumpákat egyesével.
	 */
    public void tick()
	{	
		for(int i = 0; i<pumps.size();i++)
		{
			double ran = Math.random();
			if(ran<0.1)/**10% hogy elrontja*/
			{
				pumps.get(i).breakPump();
			}
			
		}
	}
	
	/**
	 * Hozzáadja a paraméterül kapott pumpát a tárolt pumpákhoz.
	 * @param p Pump
	 */
	public void addPump(Pump p)
	{
		pumps.add(p);
	}
}
