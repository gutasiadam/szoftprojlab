import java.util.ArrayList;

public class Game {

	//A Szerelő játrékosok.
	ArrayList<Repairman> repairmanGroup;
	//A Szabotőr játékosok
	ArrayList<Saboteur> saboteurGroup;
	//A Szerelők pontja
	private int repairmanPoints;
	//A Szabotőrök pontja.
	private int saboteurPoints;
	//A játék által tárolt elemek.
	private ArrayList<Element> gameElements;
	//A játék által tárolt Ciszternák.
	private ArrayList<Cistern> cisterns;
	//A játék által tárolt elemek, melyekből tud elfolyni víz a sivatagba.
	private ArrayList<SaboteurPointSource> saboteurPointSources;
	//A hátralévő körök száma
	private int remainingRounds;
	//A Pumpákat elrontó DestructionTimer
	private DestructionTimer timer;
	
	/**
	 * Létrehozza a Game-et.
	 */
	public Game()
	{
		repairmanGroup=new ArrayList<Repairman>();
		saboteurGroup=new ArrayList<Saboteur>();
		saboteurPointSources = new ArrayList<SaboteurPointSource>();
		gameElements=new ArrayList<Element>();
		cisterns = new ArrayList<Cistern>();
		timer = new DestructionTimer();
		repairmanPoints=0;
		saboteurPoints=0;
		remainingRounds=1;//Ezt be kell állítani még valahogy!!!
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
	 * Elindítja a játékot, és menedzseli melyik karaknernek van a köre.
	 */
	public void playGame()
	{
		System.out.println("playGame");
		while(remainingRounds>0)
		{
			for(int i = 0;i<saboteurGroup.size();i++)
			{
				saboteurGroup.get(i).step();
				endTurn();
			}
			
			for(int i = 0;i<repairmanGroup.size();i++)
			{
				repairmanGroup.get(i).step();
				endTurn();
			}
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
		saboteurGroup.add(new Saboteur());
		System.out.println("addSaboteur");
	}
	
	/**
	 * Hozzáad egy Repairman-t a játékhoz.
	 */
	public void addRepairman()
	{
		repairmanGroup.add(new Repairman());
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
		timer.addPump(p);
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
