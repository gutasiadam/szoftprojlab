import java.util.HashMap;
import java.util.Scanner;
import java.lang.reflect.*;

/**
 * A programváz tesztek futtatására szolgáló osztály.
 */
class Skeleton {
	/*
	 * Játék objektum (singleton class)
	 */
	Game game;

	/*
	 * A tesztek tárolására és futtatására szolgál
	 */
	HashMap<Integer, Pair<String, Runnable>> tests = new HashMap<>(); /** A tesztek tárolása. A sám egyértelműen azonosit egy teszt nevet, és a hozzá tartozó metódust */
	private int selectedMenuItem; /** A kivalasztott teszt szama */

	// Konstruktor
	Skeleton() {
		System.out.println("Skeleton created!");
		game = new Game(); // Game metodus letrehozasa, konsturktorhivas
		tests.put(1, new Pair<>("RepairmanStandingOnCisternAndLiftsPumpTest",
				() -> RepairmanStandingOnCisternAndLiftsPumpTest()));
		tests.put(2, new Pair<>("RepairmanCannotLiftPumpTest", () -> RepairmanCannotLiftPumpTest()));
		tests.put(3, new Pair<>("RepairmanCannotPlacePumpTest", () -> RepairmanCannotPlacePumpTest()));
		tests.put(4, new Pair<>("RepairmanPlacesPumpTest", () -> RepairmanPlacesPumpTest()));
		tests.put(5, new Pair<>("RepairmanCannotPlacePumpOnZeroEndPipeTest",
				() -> RepairmanCannotPlacePumpOnZeroEndPipeTest()));
		tests.put(6, new Pair<>("PlaceNewPipeTest", () -> PlaceNewPipeTest()));
		tests.put(7, new Pair<>("RepairPumpTest", () -> RepairPumpTest()));
		tests.put(8, new Pair<>("RepairPipeTest", () -> RepairPipeTest()));
		tests.put(9, new Pair<>("SimulateWaterflow", () -> SimulateWaterflow()));
		tests.put(10, new Pair<>("WaterSourceHandlingAPipe", () -> WaterSourceHandlingAPipe()));
		tests.put(11, new Pair<>("RepairmanLiftsPipeTest", () -> RepairmanLiftsPipeTest()));
		tests.put(12, new Pair<>("RepairmanPlacingPipeTest", () -> RepairmanPlacingPipeTest()));
		//tests.put(13, new Pair<>("PumpOrCisternHandlingAPipeTest", () -> PumpOrCisternHandlingAPipeTest()));
		tests.put(13, new Pair<>("CalculatePointsTest", () -> CalculatePointsTest()));
		tests.put(14, new Pair<>("SaboteurDealsDamageOnPipeTest", () -> SaboteurDealsDamageOnPipeTest()));
		tests.put(15, new Pair<>("SaboteurDealsDamageOnCisternTest", () -> SaboteurDealsDamageOnCisternTest()));
		tests.put(16, new Pair<>("CharacterMovesFromPipeToCistern", () -> CharacterMovesFromPipeToCistern()));
		tests.put(17, new Pair<>("CharacterMovesFromCisternToPipe", () -> CharacterMovesFromCisternToPipe()));
		tests.put(18, new Pair<>("DestructionTimerDestructsPumps", () -> DestructionTimerDestructsPumps()));
		tests.put(19, new Pair<>("DestructionTimerRecievesNewPump", () -> DestructionTimerRecievesNewPump()));
	}

	//A tesztelesi menu kiirasa
	public void showTestMenu() {
		System.out.println("--- Tesztek ----");
		 /** A tesztek felsorola*/
		for (int n = 1; n < this.tests.size() + 1; n++) {
			System.out.println(n + " - " + tests.get(n).getFirst());
		}
		System.out.println("----------------");
		Scanner userInput = new Scanner(System.in);
		System.out.print("Ird be a futtatando teszt szamat (q: kilepes) >");
		while (userInput.hasNext()) {

			String input = userInput.next();

			try {
				this.selectedMenuItem = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				/** Kilepes */
				if (input.equals("q"))
					break;
				System.out.println("Ismeretlen parancs.");
			}

			if (tests.get(this.selectedMenuItem) != null) {
				/** Kivalasztott teszt futtatasa */
				tests.get(selectedMenuItem).getSecond().run();
				System.out.println("\n----------------\nIrd be a futtatando teszt szamat (q: kilepes) >");

			} else {
				/** Nem letezik a teszt, nem futtatunk */
				System.out.println("Nem talalhato a megadott szamu teszt.");

			}

		}
		userInput.close();

	}

	/**
	 * 1. Teszt
	 * A szerelo felvesz egy pumpot esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanStandingOnCisternAndLiftsPumpTest() {
		/** Inicializalas */
		System.out.println("1. Repairman Standing On Cistern And Lifts Pump");
		Repairman r = new Repairman();
		r.setName("r");
		Cistern c = new Cistern();
		c.setName("c");
		r.setPosition(c);

		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.liftPump()", r.getName()));
		r.LiftPump();
		System.out.println(String.format("END<-%s.liftPump()", r.getName()));
	}

	/**
	 * 2.
	 * A szerelo nem kap pumpat esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanCannotLiftPumpTest() {
		/** Inicializalas */
		System.out.println("2. Repairman Cannot Lift Pump");
		Repairman r = new Repairman();
		r.setName("r");
		WaterSource ws = new WaterSource();
		ws.setName("ws");
		r.setPosition(ws);

		/** Teszt futtataa */
		System.out.println(String.format("1.1 BEGIN->%s.liftPump()", r.getName()));
		r.LiftPump();
		System.out.println(String.format("END<-%s.liftPump()", r.getName()));
	}

	/**
	 * 3. Teszt
	 * A szerelo nem tudja elhelyezni a pumpat esethez tartozo teszt.
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanCannotPlacePumpTest() {
		/** Inicializalas */
		System.out.println("3. Repairman Cannot Place Pump");
		Repairman r = new Repairman();
		r.setName("r");
		WaterSource ws = new WaterSource();
		ws.setName("ws");
		r.setPosition(ws);
		Pump holdingPump = new Pump();
		holdingPump.setName("holdingPump");
		r.setHoldingPump(holdingPump);

		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}

	/**
	 * 4. Teszt
	 * A szerelo sikeresen lehelyezi a pumpat esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanPlacesPumpTest() {
		/** Inicializalas */
		System.out.println("4. Repairman Places Pump");

		Repairman r = new Repairman();
		r.setName("r");

		Pipe position = new Pipe();
		position.setName("position");
		r.setPosition(position);

		Pump holdingPump = new Pump();
		holdingPump.setName("holdingPump");
		r.setHoldingPump(holdingPump);

		Pump n1 = new Pump();
		n1.setName("n1");
		Pump n2 = new Pump();
		n2.setName("n2");

		position.addNeighbor(n1);
		n1.addNeighbor(position);
		position.addNeighbor(n2);
		n2.addNeighbor(position);

		//Game game = new Game();
		//r.setGame(game);
		//game.addPump(n1);
		//game.addPipe(position);
		//game.addPump(n2);

		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}

	/**
	 * 5. Teszt
	 * A szerelo nem tudja lehelyezi a pumpat egy olyan csovon, amely sehova nincs
	 * bekotve esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanCannotPlacePumpOnZeroEndPipeTest() {
		/** Inicializalas */
		System.out.println("5. Repairman Cannot Place Pump On Zero End Pipe");

		Repairman r = new Repairman();
		r.setName("r");

		Pipe position = new Pipe();
		position.setName("position");
		r.setPosition(position);

		Pump holdingPump = new Pump();
		holdingPump.setName("holdingPump");
		r.setHoldingPump(holdingPump);

		//Game game = new Game();
		//r.setGame(game);
		//game.addPipe(position);

		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}

	/**
	 * 6. Teszt
	 * A Controller egy csovet hoz letre a ciszternan
	 */
	public void PlaceNewPipeTest() {
		/** Inicializalas */
		System.out.println("6. Place new Pipe");
		//this.game=new Game();
		Cistern cistern1 = new Cistern();
		cistern1.setName("cistern1");
		//this.game.addCistern(cistern1);
		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->game.newPipe()"));
		//game.newPipe();
		System.out.println(String.format("END<-game.newPipe()"));
	}

	/**
	 * 7. Teszt
	 * A szerelo karakter megprobal megszerelni egy pumpat esethez tartozo teszt
	 * 
	 * @author Gutasi Adam
	 */
	public void RepairPumpTest() {
		/** Inicializalas */
		System.out.println("7. Repair Pump");

		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pump p1 = new Pump();
		p1.setName("p1");

		r1.setPosition(p1);

		/** Teszt futtatasa */
		System.out.println("1.1 BEGIN->" + r1.getName() + ".repairElement()");
		r1.RepairElement();
		System.out.print("END<-" + p1.getName() + ".repairElement()");

	}

	/**
	 * 8. Teszt
	 * A szerelo karakter megprobal megszerelni egy csovet esethez tartozo teszt
	 * 
	 * @author Gutasi Adam
	 */
	public void RepairPipeTest() {
		/** Inicializalas */
		System.out.println("8. Repair Pipe");

		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pipe p1 = new Pipe();
		p1.setName("p1");

		r1.setPosition(p1);

		/** Teszt futtatasa */
		System.out.println("1.1 BEGIN->" + p1.getName() + ".repairElement()\n\t");
		r1.RepairElement();
		System.out.print("END<-" + p1.getName() + ".repairElement()");
	}

	/**
	 * 9. Teszt
	 * A viz folyik a Forrastoll a Ciszternaiig a vizhalozaton keresztul.
	 * @author Szikszai Levente
	 */
	public void SimulateWaterflow() {
		// Setup
		System.out.println("9. SimulateWaterflow");
		System.out.println("BEGIN->Skeleton.SimulateWaterflow()");
		WaterSource Source1 = new WaterSource();
		Cistern Cistern1 = new Cistern();
		Pump Pump1 = new Pump();
		Pipe Pipe2 = new Pipe();
		Pipe Pipe1 = new Pipe();
		Pump1.setName("Pump1");
		Pipe1.setName("Pipe1");
		Pipe2.setName("Pipe2");
		Cistern1.setName("Cistern1");
		Source1.setName("Source1");
		Cistern1.addNeighbor(Pipe1);
		Pipe1.addNeighbor(Cistern1);
		Pipe1.addNeighbor(Pump1);
		Pump1.addNeighbor(Pipe1);
		Pump1.addNeighbor(Pipe2);
		Pump1.adjust(1, 0);
		Pipe2.addNeighbor(Source1);
		Pipe2.addNeighbor(Pump1);
		Source1.addNeighbor(Pipe2);
		game = new Game(); // Valahogy resetelni kell a Game-et tesztenkent
		game.addCistern(Cistern1);

		// Input
		Pump1.breakPump();
		Pipe1.damage();
		Pipe2.damage();

		// Teszt
		System.out.print("Skeleton->");
		game.SimulateWaterflow();
		System.out.print("END<-Skeleton.SimulateWaterflow()");
	}

	/**
	 * 10. Teszt
	 * Az egyik Szerelo megprobal bekotni egy csovet a forrasba
	 * @author Szikszai Levente
	 */
	public void WaterSourceHandlingAPipe() 
	{
		Tabulator.reset();
		System.out.println("10 WaterSourceHandlingAPipe");
		System.out.println("Begin->Skeleton.WaterSourceHandlingAPipe()");

		// Setup
		Repairman Repairman1 = new Repairman();
		Pipe holdingPipe = new Pipe();
		WaterSource position = new WaterSource();
		Pump Pump1 = new Pump();
		Repairman1.setPosition(position);
		Repairman1.setHoldingPipe(holdingPipe);

		// Input
		Pump1.setName("Pump1");
		holdingPipe.setName("holdingPipe");
		position.setName("position");
		Repairman1.setName("Repairman1");
		
		//Input
		Scanner sc = new Scanner(System.in);
		System.out.print("DECISION-Connect Source to holdingPipe? (I/N)>");
		String isConnectedToSource = sc.next();
		while (!(isConnectedToSource.equals("I") || isConnectedToSource.equals("N"))) {
			isConnectedToSource = sc.next();
			System.out.print("Ervenytelen valasz! Probalkozzon ujra. (I/N)>");
		}
		if (isConnectedToSource.equals("I")) {
			Tabulator.printTab();
			System.out.println("Skeleton->" + holdingPipe.getName() + ".addNeighbor(" + position.getName() + ")");
			holdingPipe.addNeighbor(position);
			Tabulator.printTab();
			System.out.println("<-" + holdingPipe.getName() + ".addNeighbor(" + position.getName() + ")");

			Tabulator.printTab();
			System.out.println("Skeleton->" + position.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
			position.addNeighbor(holdingPipe);
			Tabulator.printTab();
			System.out.println("<-" + position.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
		} else {
			Tabulator.printTab();
			System.out.println("Skeleton->" + holdingPipe.getName() + ".addNeighbor(" + Pump1.getName() + ")");
			holdingPipe.addNeighbor(Pump1);
			Tabulator.printTab();
			System.out.println("<-" + holdingPipe.getName() + ".addNeighbor(" + Pump1.getName() + ")");

			Tabulator.printTab();
			System.out.println("Skeleton->" + Pump1.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
			Pump1.addNeighbor(holdingPipe);
			Tabulator.printTab();
			System.out.println("<-" + Pump1.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
		}
		sc.close();

		// Teszt

		Tabulator.printTab();
		System.out.println("Skeleton->"+Repairman1.getName()+".PlacePipe()");
		Repairman1.PlacePipe();

		System.out.println("END<-Skeleton.WaterSourceHandlingAPipe()");
	}

	/**
	 * 11. Teszt
	 * A szerelo megprobalja felemelni az egyik cso veget.
	 * @author Vajda Andras
	 *         
	 */
	public void RepairmanLiftsPipeTest() {
		/** Inicializalas */
		System.out.println("11. RepairmanLiftsPipe");
		Pump position = new Pump();
		position.setName("position");
		Pipe n = new Pipe();
		n.setName("n");
		position.addNeighbor(n);
		n.addNeighbor(position);
		Repairman r = new Repairman();
		r.setPosition(position);
		r.setName("r");
		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.liftpipe()", r.getName()));
		r.LiftPipe(0);
		System.out.println(String.format("END<-%s.liftpipe()", r.getName()));
	}

	/**
	 * 12. Teszt
	 * Szerelo lehelyezi a nala levo csovet
	 * @author Vajda Andras
	 */
	public void RepairmanPlacingPipeTest() {
		/** Inicializalas */
		System.out.println("12. RepairmanPlacingPipe");
		Pump position = new Pump();
		position.setName("position");
		Pipe n = new Pipe();
		n.setName("n");
		position.addNeighbor(n);
		n.addNeighbor(position);
		Repairman r = new Repairman();
		r.setPosition(position);
		r.setName("r");
		r.setHoldingPipe(n);
		// Teszt futtatĂˇsa
		System.out.println(String.format("1.1 BEGIN->%s.placepipe()", r.getName()));
		r.PlacePipe();
		System.out.println(String.format("END<-%s.placepipe()", r.getName()));
	}


	/**
	 * 13. teszt
	 * Pontok osszeszamolasa a kor vegen
	 * Csak a pontszamito mukodesenek tesztelesehez valasszuk a N opciot a vizfolyas
	 * szimulalasanal.
	 * 
	 * 1 hatralevo korrel indul, meghivja az endGame-et is.
	 * 
	 * @author Gutasi Adam
	 */
	public void CalculatePointsTest() {
		System.out.println("13. Calculate Points");
		/** Inicializalas */
		this.game = new Game();

		Cistern c1 = new Cistern();
		Cistern c2 = new Cistern();
		Pump p1 = new Pump();
		Pump p2 = new Pump();

		game.addCistern(c1);
		c1.setName("c1");
		game.addCistern(c2);
		c2.setName("c2");
		game.addPump(p1);
		p1.setName("p1");
		game.addPump(p2);
		p2.setName("p2");

		/** Teszt futtatasa */
		System.out.println("BEGIN->game.endTurn()");
		game.endTurn();
		System.out.println("<-game.endTurn()");
	}

	/**
	 * 14. Teszt
	 * Egy szabotor kilyukaszt egy csovet.
	 * @author Bodnar Mark
	 */
	public void SaboteurDealsDamageOnPipeTest(){
		/** Inicializalas */
		System.out.println("14. Saboteur Deals Damage On Pipe");
		Saboteur s = new Saboteur();
		s.setName("s");
		Pipe p = new Pipe();
		p.setName("p");
		s.setPosition(p);
		
		/** Teszt futtatasa */
		System.out.println(String.format("BEGIN->dealDemage()"));
		s.dealDamage();
		System.out.println(String.format("END<-dealDemage()"));
		
	}
	
	/**
	 * 15. Teszt
	 * Egy szabotor kilyukaszt egy Cisternat.
	 * @author Bodnar Mark
	 */
	public void SaboteurDealsDamageOnCisternTest() {
		/** Inicializalas */
		System.out.println("15. Saboteur Deals Damage On Cistern");
		Saboteur s = new Saboteur();
		s.setName("s");
		Cistern c = new Cistern();
		c.setName("c");
		s.setPosition(c);
		
		/** Teszt futtatasa */
		System.out.println(String.format("BEGIN->dealDemage()"));
		s.dealDamage();
		System.out.println(String.format("END<-dealDemage()"));
	}
	/**
	 * 16. Teszt
	 * Egy karakter a csorendszeren valo mozgatasahoz tartozo teszt.
	 * Pumparol Ciszternara.
	 * @author Bodnar Mark
	 */
	public void CharacterMovesFromPipeToCistern() {
		/** Inicializalas */
		System.out.println("16. Character Moves From Pipe To Cistern");
		Repairman rp = new Repairman();
		rp.setName("rp");
		Pipe position = new Pipe();
		position.setName("pipe");
		Cistern c = new Cistern();
		c.setName("cistern");
		position.addNeighbor(c);
		c.addNeighbor(position);
		rp.setPosition(position);
		int dir=0;
		
		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.move(%s)", rp.getName(),dir));
		rp.move(dir);
		System.out.println(String.format("END<-%s.move(%s)", rp.getName(),dir));
	}
	/**
	 * 17. Teszt
	 * Egy karakter a csorendszeren valo mozgatasahoz tartozo teszt.
	 * Ciszternarol Pumpara.
	 * @author Bodnar Mark
	 */
	public void CharacterMovesFromCisternToPipe() {
		/** Inicializalas */
		System.out.println("17. Character Moves From Cistern To Pipe ");
		Repairman rp = new Repairman();
		rp.setName("rp");
		Cistern position = new Cistern();
		position.setName("cistern");
		Pipe p = new Pipe();
		p.setName("pipe");
		position.addNeighbor(p);
		p.addNeighbor(position);
		rp.setPosition(position);
		int dir=0;
		
		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.move(%s)", rp.getName(),dir));
		rp.move(dir);
		System.out.println(String.format("END<-%s.move(%s)", rp.getName(),dir));
	}

	/**
	 * 18. Teszt
	 * Pumpak veletlenszeru elromlasa.
	 * 
	 * itt most 80%-os elromlasi aranyra lett allitva
	 * igy jobban lathato, kesobb ez kisebb lesz
	 * 
	 * @author Bodnar Mark
	 */
	public void DestructionTimerDestructsPumps() {
		System.out.println("18. DesturctionTimer Destructs Pumps");
		/** Inicializalas */
		Repairman rp = new Repairman();
		rp.setName("rp");
		Pump p = new Pump();
		p.setName("p");
		DestructionTimer t = new DestructionTimer();
		t.addPump(p);
		t.setName("dt");
		
		/** Teszt futtatasa */
		System.out.println(String.format("1.1 BEGIN->%s.tick()", t.getName()));
		t.tick();
		System.out.println(String.format("END<-%s.move()", t.getName()));
	}

	/**
	 * Uj pumpa elhelyezeskor a DestructionTimer-ben taroltt pumpak listajanak bovitese.
	 * @author Bodnar Mark
	 */
	public void DestructionTimerRecievesNewPump() {
		System.out.println("18. DesturctionTimerRecieves new pump");
		/** Inicializalas */
		Game g = new Game ();
		DestructionTimer t = new DestructionTimer();
		Pump p = new Pump();
		Repairman rp = new Repairman();
		t.setName("dt");
		p.setName("p");
		rp.setName("rp");
		rp.setHoldingPump(p);
		g.setTimer(t);
		
		System.out.println(String.format("1.1 BEGIN->%s.addPump(%s)", t.getName(),p.getName()));
		g.addPump(p);
		System.out.println(String.format("END<-%s.addPump(%s)", t.getName(),p.getName()));
		
	}

}
