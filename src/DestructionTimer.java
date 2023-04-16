import java.util.ArrayList;

public class DestructionTimer {

	//A tárolt pumpák, melyeket el tud rontani.
	private ArrayList<Pump> pumps;
	
	
	/**
	 * Létrehoz egy DestructionTimer-t.
	 */
	public DestructionTimer()
	{
		pumps=new ArrayList<Pump>();
	}
	
	/**
	 * Bizonyos eséllyel elrontja a pumpákat egyesével.
	 */
	public void tick()
	{
		//System.out.println("tick");
		
		for(int i = 0; i<pumps.size();i++)
		{
			double ran = Math.random();
			if(ran<0.2)//20% hogy elrontja.
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
		//System.out.println("addPump");
		pumps.add(p);
	}
}
