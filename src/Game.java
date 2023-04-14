
public class Game {

	//Repairman[] repairmanGroup;
	//Saboteur[] saboteurGroup;
	int repairmanPoints;
	int saboteurPoints;
	Element[] gameElements;
	Cistern[] cisterns;
	//saboteurPointSource[] saboteurPointSources;
	int remainingRounds;
	DestructionTimer timer;
	
	public Game()
	{
		
	}
	
	public void initialize()
	{
		System.out.println("inicialize");
	}
	
	public void playGame()
	{
		System.out.println("playGame");
	}
	
	public void endGame()
	{
		System.out.println("endGame");
	}
	
	public void endTurn()
	{
		System.out.println("endTurn");
	}
	
	public void SimulateWaterflow()
	{
		System.out.println("SimulateWaterFlow");
	}
	
	public void addElement(Element e)
	{
		System.out.println("addEmelent");
	}
	
	public Element[] getGameElements()
	{
		
		System.out.println("getGameElements");
		return gameElements;
		
	}
	
	public void addSaboteur()
	{
		//saboteurGroup.add
		System.out.println("addSaboteur");
	}
	
	public void addRepairman()
	{
		//repairmanGroup.add
		System.out.println("addRepairman");
	}
	
	public void addPipe(Pipe p)
	{
		System.out.println("addPipe");
		//saboteurSourcePoints.add
	}
	
	public void calculatePoints()
	{
		System.out.println("calculatePoints");
	}
	
	public void addPump(Pump p)
	{
		System.out.println("addPump");
	}
	
	public void newPipe()
	{
		System.out.println("newPipe");
	}
}
