import java.util.ArrayList;
/**
 * 
 * A Pumpak veletlenszeru elromlasaert felelos osztaly.
 * @author Szikszai Levente
 */
public class DestructionTimer {

	//A tárolt pumpák, melyeket el tud rontani.
	private ArrayList<Pump> pumps;
	private String Name;
	
	
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
			if(ran<0.8)//80% hogy elrontja, a latavanyossag kedveert kesobb majd le kell venni
			{
				System.out.println(String.format("\t1.2 %s->%s-pumps.get(%s).breakPump()", this.getName(),this.getName(),i));
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
		System.out.println(String.format("\t\t1.3 %s->pumps.add(%s)", this.getName(),p.getName()));
		pumps.add(p);
	}
}
