import java.util.ArrayList;

public class Game {

	//ArrayList<Repairman> repairmanGroup;
	//ArrayList<Saboteur> saboteurGroup;
	private int repairmanPoints;
	private int saboteurPoints;
	private ArrayList<Element> gameElements;
	private ArrayList<Cistern> cisterns;
	private ArrayList<SaboteurPointSource> saboteurPointSources;
	private int remainingRounds;
	private DestructionTimer timer;
	
	/**
	 * Létrehozza a Game-et.
	 */
	public Game()
	{
		saboteurPointSources = new ArrayList<SaboteurPointSource>();
		gameElements=new ArrayList<Element>();
		cisterns = new ArrayList<Cistern>();
		timer = new DestructionTimer();
		repairmanPoints=0;
		saboteurPoints=0;
		remainingRounds=1;
	}
	
	/**
	 * Létrehozza a pályát.
	 */
	public void initialize()
	{
		//Create Map
		System.out.println("inicialize");
	}
	
	/**
	 * Elindítja a játékot.
	 */
	public void playGame()
	{
		System.out.println("playGame");
		while(remainingRounds>0)
		{
			//Emberek megkaplyák a körüket.
			endTurn();
			remainingRounds--;
		}
		endGame();
	}
	/**
	 * Játék befejezése, a győztes kihirdetése.
	 */
	public void endGame()
	{
		System.out.println("endGame");
		if(saboteurPoints>repairmanPoints)
		{
			System.out.println("Saboteur Team Won!");
		}
		else if(repairmanPoints>saboteurPoints)
		{
			System.out.println("Repairman Team Won!");
		}else
		{
			System.out.println("It's a Draw!");
		}
	}
	
	/**
	 * Adott kör véget ér, elindul a Víz Szimuláció
	 */
	public void endTurn()
	{
		System.out.println("endTurn");
		SimulateWaterflow();
	}
	
	/**
	 * Elindítja a víz szimulációt a Ciszternáktól.
	 */
	public void SimulateWaterflow()
	{
		System.out.println("SimulateWaterFlow");
		for(int i = 0;i<cisterns.size();i++)
		{
			cisterns.get(i).step();
		}
	}
	
	/**
	 * Hozzáad egy Element-et a játékhoz.
	 * @param e Element
	 */
	public void addElement(Element e)
	{
		System.out.println("addEmelent");
		gameElements.add(e);
	}
	
	/**
	 * Visszaadja a tárolt elemeket a Game.
	 * @return gameElements
	 */
	public ArrayList<Element> getGameElements()//Jó lesz-e ArrayList??
	{
		System.out.println("getGameElements");
		return gameElements;
	}
	
	/**
	 * Hozzáad egy Saboteur-t a játékhoz.
	 */
	public void addSaboteur()
	{
		//saboteurGroup.add
		System.out.println("addSaboteur");
	}
	
	/**
	 * Hozzáad egy Repairman-t a játékhoz.
	 */
	public void addRepairman()
	{
		//repairmanGroup.add
		System.out.println("addRepairman");
	}
	
	/**
	 * Hozzáad egy Csövet a játékhoz.
	 * @param p Cső
	 */
	public void addPipe(Pipe p)
	{
		System.out.println("addPipe");
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
	}
	
	/**
	 * Növeli a pontokat a Ciszternába befolyt meg a Csövekből és Pumpákból elfolyt víz mennyisége alapján.
	 */
	public void calculatePoints()
	{
		System.out.println("calculatePoints");
		for(int i = 0;i<cisterns.size();i++)
		{
			repairmanPoints+= cisterns.get(i).measureAndResetWaterFlown();
		}
		
		for(int i = 0; i<saboteurPointSources.size();i++)
		{
			saboteurPoints+=saboteurPointSources.get(i).measureAndResetLeakedWaterAmount();
		}
	}
	
	/**
	 * Hozzáad egy pumpát a játékhoz.
	 * @param p Pumpa
	 */
	public void addPump(Pump p)
	{
		System.out.println("addPump");
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
	}
	
	/**
	 * Minden Ciszternának szól hogy létrehozhatnak új csövet.
	 * Ha létrejön új cső azt a Game eltárolja.
	 */
	public void newPipe()
	{
		System.out.println("newPipe");
		for(int i =0;i<cisterns.size();i++)
		{
			Pipe p = cisterns.get(i).newPipe();
			if(p!=null)
			{
				addPipe(p);
			}
		}
	}
}
