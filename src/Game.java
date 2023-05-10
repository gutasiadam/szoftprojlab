import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Szikszai Levente
 *	Az elemek tarolasaert es a jatek mukodeseert felelos osztaly
 */
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
		remainingRounds=1;
	}
	
	/**
	 * Beállítja a Game megfelelő változóit.
	 * @param elements A csőhálózatot alkotó objektumok.
	 * @param repairmans A Szerelő játékosok
	 * @param saboteurs A Szabotőr játékosok
	 */
	public void load(ArrayList<Element> elements, ArrayList<Repairman> repairmans,ArrayList<Saboteur> saboteurs)
	{
		repairmanGroup=repairmans;
		saboteurGroup=saboteurs;
		gameElements=elements;
	}

	/**
	 * Visszaadja a Szerelő járékosokat
	 * @return repairmanGroup: A szerelő játékosokat tartalmazó ArrayList
	 */
	public ArrayList<Repairman> getRepairmanGroup()
	{
		return repairmanGroup;
	}

	/**
	 * Visszaadja a Szabotőr járékosokat
	 * @return saboteurGroup: A szabotőr játékosokat tartalmazó ArrayList
	 */
	public ArrayList<Saboteur> getSaboteurGroup()
	{
		return saboteurGroup;
	}

	/**
	 * Létrehozza a pályát.
	 */
	public void initialize()
	{
		//Create Map
		//System.out.println("inicialize");
	}
	
	/**
	 * Elindítja a játékot, és menedzseli melyik karaknernek van a köre.
	 */
	public void playGame()
	{
		//System.out.println("playGame");
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
		//System.out.println("endGame");
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
	public void setTimer(DestructionTimer dt) {
		timer=dt;
	}
	public void endTurn()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("DECISION - Szimuláljuk a vifolyást? (I/N) >"); 
		String brokePipe1;
		while(sc.hasNext())
		{
			brokePipe1 = sc.next();
			if(brokePipe1.equals("I"))
			{
				SimulateWaterflow();
				break;
				
			}if(brokePipe1.equals("N")) {
				break;
			}else {
				System.out.print("\nErvenytelen valasz! Probalkozzon ujra. (I/N)>");
			}
		}
		
		int repairmanWater = 0;
		int saboteurWater = 0;
		System.out.println("\t\tremainingRounds="+this.remainingRounds);
		for(int i=0;i<cisterns.size();i++) {
			int ciWater=cisterns.get(i).measureAndResetWaterFlown();
			repairmanWater+=ciWater;
		}
		
		for(int i=0;i<saboteurPointSources.size();i++) {
			System.out.println("\t\t\t->"+saboteurPointSources.get(i).getName()+".measureAndResetWaterFlown()");
			int sWater=saboteurPointSources.get(i).measureAndResetLeakedWaterAmount();
			System.out.println("\t\t\t<-"+saboteurPointSources.get(i).getName()+".measureAndResetWaterFlown():"+sWater);
			saboteurWater+=sWater;
		}
		System.out.println("\t\tSaboteur points this round:"+repairmanWater);
		System.out.println("\t\tRepairmen points this round:"+saboteurWater);
		this.repairmanPoints+=repairmanWater;
		this.saboteurPoints+=saboteurWater;
		System.out.println("\t\tSaboteur points in total:"+this.repairmanPoints);
		System.out.println("\t\tRepairmen points in total:"+this.saboteurPoints);
		
		

		
		
		this.remainingRounds--;
		if(remainingRounds==0) {
			System.out.println("\t->game.endGame()");
			endGame();
			System.out.println("\t<-game.endGame():void");
		}
	}
	
	/**
	 * Elindítja a víz szimulációt a Ciszternáktól.
	 */
	public void SimulateWaterflow()
	{
		System.out.println("Game.SimulateWaterflow()");
		Tabulator.increaseTab();
		Tabulator.printTab();
		for(int i = 0;i<cisterns.size();i++)
		{
			
			cisterns.get(i).step();
			
			
		}
		System.out.println("<-Game.SimulateWaterflow():void");
	}
	
	/**
	 * Hozzáad egy Ciszternát a játékhoz
	 * @param c Ciszterna
	 */
	public void addCistern(Cistern c)
	{
		//System.out.println("addCistern");
		cisterns.add(c);
	}
	
	/**
	 * Hozzáad egy Element-et a játékhoz.
	 * @param e Element
	 */
	public void addElement(Element e)
	{
		//System.out.println("addEmelent");
		gameElements.add(e);
	}
	
	/**
	 * Visszaadja a tárolt elemeket a Game.
	 * @return gameElements
	 */
	public ArrayList<Element> getGameElements()//Jó lesz-e ArrayList??
	{
		//System.out.println("getGameElements");
		return gameElements;
	}
	
	/**
	 * Hozzáad egy Saboteur-t a játékhoz.
	 */
	public void addSaboteur()
	{
		saboteurGroup.add(new Saboteur());
		//System.out.println("addSaboteur");
	}
	
	/**
	 * Hozzáad egy Repairman-t a játékhoz.
	 */
	public void addRepairman()
	{
		repairmanGroup.add(new Repairman());
		//System.out.println("addRepairman");
	}
	
	/**
	 * Hozzáad egy Csövet a játékhoz.
	 * @param p Cső
	 */
	public void addPipe(Pipe p)
	{
		//System.out.println("addPipe");
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
	}
	
	/**
	 * Növeli a pontokat a Ciszternába befolyt meg a Csövekből és Pumpákból elfolyt víz mennyisége alapján.
	 */
	public void calculatePoints()
	{
		//System.out.println("calculatePoints");
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
		//System.out.println("addPump");
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
		System.out.println(String.format("\t1.2 g->%s.addPump(%s)", timer.getName(),p.getName()));
		timer.addPump(p);
	}
	
	/**
	 * Minden Ciszternának szól hogy létrehozhatnak új csövet.
	 * Ha létrejön új cső azt a Game eltárolja.
	 */
	public void newPipe()
	{
		
		for(int i =0;i<cisterns.size();i++)
		{
			System.out.println("1.2\tgame->"+cisterns.get(i).getName()+".newPipe()");
			Pipe p = cisterns.get(i).newPipe();
			p.setName("createdPipe"+(i+1));
			System.out.println("\tgame<-"+cisterns.get(i).getName()+".newPipe():"+p.getName());
			if(p!=null)
			{
				System.out.println("1.4.2\tgame.addPipe("+p.getName()+")");
				addPipe(p);
			}
		}
	}
}
