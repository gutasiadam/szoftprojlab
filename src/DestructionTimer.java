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
	private boolean randomEnabled;
	
	/**
	 * Létrehoz egy DestructionTimer-t.
	 */
	public DestructionTimer()
	{
		pumps=new ArrayList<Pump>();
		randomEnabled = true;
	}
	/**
	 * A teszthez lesz a nevre szukseg
	 */
    String getName() {
    	return Name;
    }
    
	/**
	 * Beállítja a randomEnabled értékét a paraméternek megfelelően.
	 * @param random Legyenek-e engedélyezve a random események a DEstructionTimer-en belül.
	 */
	public void setRandomEnabled(boolean random)
	{
		randomEnabled = random;
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
	 * Ha randomEnabled értéke igaz akkor biztosan elront minden pumpát.
	 */
    public void tick()
	{	
		for(int i = 0; i<pumps.size();i++)
		{
			double ran = Math.random();
			if(!randomEnabled||ran<0.1)/**10% hogy elrontja*/
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

	/**
	 * Kiüríti az eltárolt pumpákat.
	 */
	public void resetPumps()
	{
		pumps.clear();
	}
}
