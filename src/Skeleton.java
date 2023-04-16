import java.util.HashMap;
import java.util.Scanner;
import java.lang.reflect.*;

class Skeleton {
	/*
	 * Játék objektum (singleton class)
	 */
	Game game;
	
	/*
	 * Tesztek tárolására szolgál
	 *  Itt Object helyett Runnable fog szerepelni, csak a tesztelés erejéig van benne az Object.
	 */
	HashMap<Integer, Pair<String, Runnable>> tests= new HashMap<>(); 
	private int selectedMenuItem; //A kiválasztott teszt száma.
	
	//Konstruktor
	Skeleton(){
		System.out.println("Skeleton created!");
		game=new Game(); // Game metodus letrehozasa, konsturktorhivas
		tests.put(1, new Pair<>("RepairmanStandingOnCisternAndLiftsPumpTest", () -> RepairmanStandingOnCisternAndLiftsPumpTest()));
		tests.put(2, new Pair<>("RepairmanCannotLiftPumpTest", () -> RepairmanCannotLiftPumpTest()));
		tests.put(3, new Pair<>("RepairmanCannotPlacePumpTest", () -> RepairmanCannotPlacePumpTest()));
		tests.put(4, new Pair<>("RepairmanPlacesPumpTest", () -> RepairmanPlacesPumpTest()));
		tests.put(5, new Pair<>("RepairmanCannotPlacePumpOnZeroEndPipeTest", () -> RepairmanCannotPlacePumpOnZeroEndPipeTest()));
		tests.put(6, new Pair<>("PlaceNewPipeTest", () -> PlaceNewPipeTest()));
		tests.put(7, new Pair<>("RepairPumpTest", () -> RepairPumpTest()));
		tests.put(8, new Pair<>("RepairPipeTest", () -> RepairPipeTest()));
		tests.put(9, new Pair<>("SimulateWaterflow", () -> SimulateWaterflow()));
		tests.put(10, new Pair<>("WaterSourceHandlingAPipe", () -> WaterSourceHandlingAPipe()));
		tests.put(11, new Pair<>("RepairmanLiftsPipeTest", () -> RepairmanLiftsPipeTest()));
		tests.put(12, new Pair<>("RepairmanPlacingPipeTest", () -> RepairmanPlacingPipeTest()));
		tests.put(13, new Pair<>("PumpOrCisternHandlingAPipeTest", () -> PumpOrCisternHandlingAPipeTest()));
		tests.put(14, new Pair<>("CalculatePointsTest", () -> CalculatePointsTest()));
		tests.put(15, new Pair<>("SaboteurDealsDamageOnPipeTest", () -> SaboteurDealsDamageOnPipeTest()));
		tests.put(16, new Pair<>("SaboteurDealsDamageOnCisternTest", () -> SaboteurDealsDamageOnCisternTest()));
		tests.put(17, new Pair<>("CharacterMovesTest", () -> CharacterMovesTest()));
		tests.put(18, new Pair<>("DestructionTimeDestructsPumps", () -> DestructionTimeDestructsPumps()));
		tests.put(19, new Pair<>("DestructionTimerRecievesNewPump", () -> DestructionTimerRecievesNewPump()));
	}
	//A tesztelési főmenü kiirása, tesztek inditasa
	public void showTestMenu() {
		System.out.println("--- Tesztek ----");
		for(int n=1;n<this.tests.size()+1;n++) {
			System.out.println(n+" - "+tests.get(n).getFirst());
		}
		System.out.println("----------------");
		Scanner userInput = new Scanner(System.in);
		System.out.print("Ird be a futtatandó teszt számát (q: kilépés)\n>");
        while (userInput.hasNext()) {
        	
            
            String input = userInput.next();
			
			try {
				this.selectedMenuItem = Integer.parseInt(input);
			}catch (NumberFormatException e) {
				if(input.equals("q")) break;
				System.out.println("Ismeretlen parancs.");
			}
			
			if(tests.get(this.selectedMenuItem) !=null) {
				//Teszt futtatasa
				tests.get(selectedMenuItem).getSecond().run();
				System.out.println("\n----------------\nIrd be a futtatandó teszt számát (q: kilépés)\\n>");
				
			}
			else {
				//Nem letezik a teszt, nem futtatunk
				System.out.println("Nem talalhato a megadott szamu teszt.");
				
			}
			
		}
        userInput.close();
		
	}
	
	/**
	 * A szerelő felvesz egy pumpát esethez tartozó teszt
	 * @author Andai Roland
	 */
	public void RepairmanStandingOnCisternAndLiftsPumpTest(){
		/** Inicializálás */
		System.out.println("1. Repairman Standing On Cistern And Lifts Pump");
		Repairman r = new Repairman();
		r.setName("r");
		Cistern c = new Cistern();
		c.setName("c");
		r.setPosition(c);

		/** Teszt futtatása */
		System.out.println(String.format("1.1 BEGIN->%s.liftPump()", r.getName()));
		r.LiftPump();
		System.out.println(String.format("END<-%s.liftPump()", r.getName()));
	}
	
	/**
	 * A szerelő nem kap pumpát esethez tartozó teszt
	 * @author Andai Roland
	 */
	public void RepairmanCannotLiftPumpTest() {
		/** Inicializálás */
		System.out.println("2. Repairman Cannot Lift Pump");
		Repairman r = new Repairman();
		r.setName("r");
		WaterSource ws = new WaterSource();
		ws.setName("ws");
		r.setPosition(ws);

		/** Teszt futtatása */
		System.out.println(String.format("1.1 BEGIN->%s.liftPump()", r.getName()));
		r.LiftPump();
		System.out.println(String.format("END<-%s.liftPump()", r.getName()));
	}
	
	/**
	 * A szerelő nem tudja elhelyezni a pumpát esethez tartozó teszt.
	 * @author Andai Roland
	 */
	public void RepairmanCannotPlacePumpTest() {
		/** Inicializálás */
		System.out.println("3. Repairman Cannot Place Pump");
		Repairman r = new Repairman();
		r.setName("r");
		WaterSource ws = new WaterSource();
		ws.setName("ws");
		r.setPosition(ws);
		Pump holdingPump = new Pump();
		holdingPump.setName("holdingPump");
		r.setHoldingPump(holdingPump);

		/** Teszt futtatása */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}
	
	/**
	 * A szerelő sikeresen lehelyezi a pumpát esethez tartozü teszt
	 */
	public void RepairmanPlacesPumpTest() {}
	
	/**
	 * A szerelő nem tudja lehelyezi a pumpát egy olyan csövön, amely sehová nincs bekötve esethez tartozü teszt
	 */
	public void RepairmanCannotPlacePumpOnZeroEndPipeTest() {}
	
	/**
	 * A Controller új csövet hoz létre egy ciszternán
	 */
	public void PlaceNewPipeTest() {}
	
	/**
	 * 7. Teszt
	 * A szerelő karakter megpróbál megszerelni egy pumpát esethez tartozó teszt
	 * @author Gutási Ádám
	 */
	public void RepairPumpTest() {
		//Inicializálás
		System.out.println("Repair Pump");
	
		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pump p1 = new Pump();
		p1.setName("p1");
		
		r1.setPosition(p1);
		
		/** Teszt futtatása */
		System.out.print("1.1 BEGIN->"+p1.getName()+".repairElement()\n\t");
		r1.RepairElement();
		System.out.print("END<-"+p1.getName()+".repairElement()");
		
	}
	
	/**
	 * 8. Teszt
	 * "A szerelő karakter megpróbál megszerelni egy csövet" esethez tartozó teszt
	 * @author Gutási Ádám
	 */
	public void RepairPipeTest() {
		//Inicializálás
		System.out.println("Repair Pipe");
	
		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pipe p1 = new Pipe();
		p1.setName("p1");
		
		r1.setPosition(p1);
		
		/** Teszt futtatása */
		System.out.print("1.1 BEGIN->"+p1.getName()+".repairElement()\n\t");
		r1.RepairElement();
		System.out.print("END<-"+p1.getName()+".repairElement()");
	}
	
	
	/**
     * @author Szikszai Levente
     * A víz folyik a Forrástól a Ciszternáig a vízhálózaton keresztül.
     */
    public void SimulateWaterflow()
    {
        //Setup
        //System.out.println("BEGIN");
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
        game = new Game();      //Valahogy resetelni kell a Game-et tesztenként
        game.addCistern(Cistern1);
        
        //Input
        Scanner sc = new Scanner(System.in);
        System.out.println("DECISION Pump1 el van romolva? (I/N)");
        
        String brokePump1 = "";
        if(sc.hasNext())
            brokePump1 = sc.next();
        
        
        while(!(brokePump1.equals("I")||brokePump1.equals("N")))
        {
            brokePump1 = sc.next();
            System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
        }
        if(brokePump1.equals("I"))
        {
            Pump1.breakPump();
        }
        
        System.out.println("DECISION Pipe1 lyukas? (I/N)>");
        String damagePipe1 = "";
        if(sc.hasNext())
            damagePipe1 = sc.next();
        
        while(!(damagePipe1.equals("I")||damagePipe1.equals("N")))
        {
            damagePipe1 = sc.next();
            System.out.println("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
        }
        if(damagePipe1.equals("I"))
        {
            Pipe1.damage();
        }
        
        System.out.print("DECISION Pipe2 lyukas? (I/N)>");
        String damagePipe2 = "";
        if(sc.hasNext())
            damagePipe2 = sc.next();
        
        while(!(damagePipe2.equals("I")||damagePipe2.equals("N")))
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

	
	/**
	 * Az egyik Szerelő megpróbál bekötni egy csövet a Forrásba.
	 */
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
	
	/**
	 * A szerelő megpróbálja felemeli az egyik cső végét 
	 */
	public void RepairmanLiftsPipeTest() {}
	
	/**
	 * Szerelő lehelyezi a nála lévő csövet
	 */
	public void RepairmanPlacingPipeTest() {}
	
	/**
	 * Szerelő lehelyez egy nála lévő csöve egy pumpára vagy cisternára
	 */
	public void PumpOrCisternHandlingAPipeTest() {}
	
	/**
	 * Pontok összeszámolása a kör végén
	 */
	public void CalculatePointsTest() {}
	
	/**
	 * Egy szabotőr kilyukaszt egy Csövet.
	 */
	public void SaboteurDealsDamageOnPipeTest(){
		Saboteur s = new Saboteur();
		Pipe p = new Pipe();
		s.setPosition(p);
		s.dealDamage();
	}
	/**
	 * Egy szabotőr kilyukaszt egy Ciszternát.
	 */
	public void SaboteurDealsDamageOnCisternTest() {
		Saboteur s = new Saboteur();
		Cistern p = new Cistern();
		s.setPosition(p);
		s.dealDamage();
	}
	
	/**
	 * Egy karakter a csőrendszeren történő mozgásához tartozó teszt.
	 */
	public void CharacterMovesTest() {}
	
	/**
	 * Pumpák véletlenszerű elromlása.
	 */
	public void DestructionTimeDestructsPumps() {}
	
	/**
	 * Új pumpa elhelyezésekor a DestructionTimer-ben tárolt pumpák listájának bővítése.
	 */
	public void DestructionTimerRecievesNewPump() {}
	
}
