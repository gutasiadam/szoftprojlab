import java.util.HashMap;
import java.util.Scanner;


class Skeleton {
	/*
	 * Játék objektum (singleton class)
	 */
	Game game;
	
	/*
	 * Tesztek tárolására szolgál
	 *  Itt Object helyett Runnable fog szerepelni, csak a tesztelés erejéig van benne az Object.
	 */
	HashMap<Integer, Pair<String, Object>> tests= new HashMap<>(); 
	private int selectedMenuItem; //A kiválasztott teszt száma.
	
	//Konstruktor
	Skeleton(){
		System.out.println("Skeleton created!");
		game=new Game(); // Game metodus letrehozasa, konsturktorhivas
		tests.put(1, new Pair<>("Example test", new Object()));
	}
	//A tesztelési főmenü kiirása, tesztek inditasa
	public void showTestMenu() {
		System.out.println("--- Tesztek ----");
		for(int n=1;n<this.tests.size()+1;n++) {
			System.out.println(n+"\t-\t"+tests.get(n).getFirst());
		}
		System.out.println("----------------");
		Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println("Ird be a futtatandó teszt számát (q: kilépés)\n>");
            String input = userInput.nextLine();
			
			try {
				this.selectedMenuItem = Integer.parseInt(input);
			}catch (NumberFormatException e) {
				if(input.equals("q")) break;
				System.out.println("Ismeretlen parancs.");
			}
			
			System.out.println("MenuItem is "+this.selectedMenuItem);
			if(tests.get(this.selectedMenuItem) !=null) {
				//Teszt futtatasa
				System.out.println(tests.get(selectedMenuItem).getSecond());
			}
			else {
				//Nem letezik a teszt, nem futtatunk
				System.out.println("Nem talalhato a megadott szamu teszt.");
			}
		}
		userInput.close();
	}
	
	
	public void SaboteurDealsDamageOnPipeTest(){
		Saboteur s = new Saboteur();
		Pipe p = new Pipe();
		s.setPosition(p);
		s.dealDamage();
	}
	public void SaboteurDealsDamageOnCisternTest() {
		Saboteur s = new Saboteur();
		Cistern p = new Cistern();
		s.setPosition(p);
		s.dealDamage();
	}
	public void CharacterMovesFromPipeToCistern() {}
	public void CharacterMovesFromCisternToPipe() {}
	public void DestructionTimeDestructsPumps() {}
	public void DestructionTimerRecievesNewPump() {}
	
	
	public void SimulateWaterflow()
	{
		//Setup
		WaterSource Source1 = new WaterSource();
		Cistern Cistern1 = new Cistern();
		Pump Pump1 = new Pump();
		Pipe Pipe2 = new Pipe();
		Pipe Pipe1 = new Pipe();
		Cistern1.addNeighbor(Pipe1);
		Pipe1.addNeighbor(Cistern1);
		Pipe1.addNeighbor(Pump1);
		Pump1.addNeighbor(Pipe1);
		Pump1.addNeighbor(Pipe2);
		Pump1.adjust(1, 0);
		Pipe2.addNeighbor(Source1);
		Pipe2.addNeighbor(Pump1);
		Source1.addNeighbor(Pipe2);
		game = new Game();		//Valahogy resetelni kell a Game-et tesztenként
		game.addCistern(Cistern1);
		
		//Input
		Scanner sc = new Scanner(System.in);
		System.out.println("DECISION Pump1 el van romolva? (I/N)");
		String brokePump1 = sc.next();
		while(brokePump1.equals("I")||brokePump1.equals("N"))
		{
			brokePump1 = sc.next();
			System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
		}
		if(brokePump1.equals("I"))
		{
			Pump1.breakPump();
		}
		
		System.out.println("DECISION Pipe1 lyukas? (I/N)>");
		String damagePipe1 = sc.next();
		while(damagePipe1.equals("I")||damagePipe1.equals("N"))
		{
			damagePipe1 = sc.next();
			System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
		}
		if(damagePipe1.equals("I"))
		{
			Pipe1.damage();
		}
		
		System.out.print("DECISION Pipe2 lyukas? (I/N)>");
		String damagePipe2 = sc.next();
		while(damagePipe2.equals("I")||damagePipe2.equals("N"))
		{
			damagePipe2 = sc.next();
			System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
		}
		if(damagePipe2.equals("I"))
		{
			Pipe2.damage();
		}
		sc.close();
		
		//Teszt
		
		System.out.print("Begin->");
		game.SimulateWaterflow();
		System.out.println("END<-Game.SimulateWaterflow()");
	}
	
	public void WaterSourceHandlingAPipe()
	{
		//Setup
		Repairman Repairman1 = new Repairman();
		Pipe holdingPipe = new Pipe();
		WaterSource position = new WaterSource();
		Pump Pump1 = new Pump();
		Repairman1.setPosition(position);
		Repairman1.setHoldingPipe(holdingPipe);
		
		//Input
		Scanner sc = new Scanner(System.in);
		String isConnectedToSource = sc.next();
		while(isConnectedToSource.equals("I")||isConnectedToSource.equals("N"))
		{
			isConnectedToSource = sc.next();
			System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)");
		}
		if(isConnectedToSource.equals("I"))
		{
			holdingPipe.addNeighbor(position);
			position.addNeighbor(holdingPipe);
		}
		else
		{
			holdingPipe.addNeighbor(Pump1);
			Pump1.addNeighbor(holdingPipe);
		}
		
		//Teszt
		System.out.println("Begin->Repairman1.PlacePipe()");
		Repairman1.PlacePipe();
		System.out.println("END<-Repairman1.PlacePipe()");
	}
}
