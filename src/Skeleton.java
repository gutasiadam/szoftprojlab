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
	 * Itt Object helyett Runnable fog szerepelni, csak a tesztelés erejéig van
	 * benne az Object.
	 */
	HashMap<Integer, Pair<String, Runnable>> tests = new HashMap<>();
	private int selectedMenuItem; // A kiválasztott teszt száma.

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
		tests.put(13, new Pair<>("PumpOrCisternHandlingAPipeTest", () -> PumpOrCisternHandlingAPipeTest()));
		tests.put(14, new Pair<>("CalculatePointsTest", () -> CalculatePointsTest()));
		tests.put(15, new Pair<>("SaboteurDealsDamageOnPipeTest", () -> SaboteurDealsDamageOnPipeTest()));
		tests.put(16, new Pair<>("SaboteurDealsDamageOnCisternTest", () -> SaboteurDealsDamageOnCisternTest()));
		tests.put(17, new Pair<>("CharacterMovesTest", () -> CharacterMovesTest()));
		tests.put(18, new Pair<>("DestructionTimeDestructsPumps", () -> DestructionTimeDestructsPumps()));
		tests.put(19, new Pair<>("DestructionTimerRecievesNewPump", () -> DestructionTimerRecievesNewPump()));
	}

	// A tesztelési főmenü kiirása, tesztek inditasa
	public void showTestMenu() {
		System.out.println("--- Tesztek ----");
		for (int n = 1; n < this.tests.size() + 1; n++) {
			System.out.println(n + " - " + tests.get(n).getFirst());
		}
		System.out.println("----------------");
		Scanner userInput = new Scanner(System.in);
		System.out.print("Ird be a futtatandó teszt számát (q: kilépés)\n>");
		while (userInput.hasNext()) {

			String input = userInput.next();

			try {
				this.selectedMenuItem = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				if (input.equals("q"))
					break;
				System.out.println("Ismeretlen parancs.");
			}

			if (tests.get(this.selectedMenuItem) != null) {
				// Teszt futtatasa
				tests.get(selectedMenuItem).getSecond().run();
				System.out.println("\n----------------\nIrd be a futtatandó teszt számát (q: kilépés)\\n>");

			} else {
				// Nem letezik a teszt, nem futtatunk
				System.out.println("Nem talalhato a megadott szamu teszt.");

			}

		}
		userInput.close();

	}

	/**
	 * A szerelő felvesz egy pumpát esethez tartozó teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanStandingOnCisternAndLiftsPumpTest() {
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
	 * 
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
	 * 
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
	 * A szerelő sikeresen lehelyezi a pumpát esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanPlacesPumpTest() {
		/** Inicializálás */
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

		Game game = new Game();
		r.setGame(game);
		game.addPump(n1);
		game.addPipe(position);
		game.addPump(n2);

		/** Teszt futtatása */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}

	/**
	 * A szerelő nem tudja lehelyezi a pumpát egy olyan csövön, amely sehová nincs
	 * bekötve esethez tartozo teszt
	 * 
	 * @author Andai Roland
	 */
	public void RepairmanCannotPlacePumpOnZeroEndPipeTest() {
		/** Inicializálás */
		System.out.println("5. Repairman Cannot Place Pump On Zero End Pipe");

		Repairman r = new Repairman();
		r.setName("r");

		Pipe position = new Pipe();
		position.setName("position");
		r.setPosition(position);

		Pump holdingPump = new Pump();
		holdingPump.setName("holdingPump");
		r.setHoldingPump(holdingPump);

		Game game = new Game();
		r.setGame(game);
		game.addPipe(position);

		/** Teszt futtatása */
		System.out.println(String.format("1.1 BEGIN->%s.placePump()", r.getName()));
		r.PlacePump();
		System.out.println(String.format("END<-%s.placePump()", r.getName()));
	}

	/**
	 * A Controller új csövet hoz létre egy ciszternán
	 */
	public void PlaceNewPipeTest() {
	}

	/**
	 * 7. Teszt
	 * A szerelő karakter megpróbál megszerelni egy pumpát esethez tartozó teszt
	 * 
	 * @author Gutási Ádám
	 */
	public void RepairPumpTest() {
		// Inicializálás
		System.out.println("Repair Pump");

		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pump p1 = new Pump();
		p1.setName("p1");

		r1.setPosition(p1);

		/** Teszt futtatása */
		System.out.print("1.1 BEGIN->" + p1.getName() + ".repairElement()\n\t");
		r1.RepairElement();
		System.out.print("END<-" + p1.getName() + ".repairElement()");

	}

	/**
	 * 8. Teszt
	 * "A szerelő karakter megpróbál megszerelni egy csövet" esethez tartozó teszt
	 * 
	 * @author Gutási Ádám
	 */
	public void RepairPipeTest() {
		// Inicializálás
		System.out.println("Repair Pipe");

		Repairman r1 = new Repairman();
		r1.setName("r1");
		Pipe p1 = new Pipe();
		p1.setName("p1");

		r1.setPosition(p1);

		/** Teszt futtatása */
		System.out.print("1.1 BEGIN->" + p1.getName() + ".repairElement()\n\t");
		r1.RepairElement();
		System.out.print("END<-" + p1.getName() + ".repairElement()");
	}

	/**
	 * @author Szikszai Levente
	 *         A víz folyik a Forrástól a Ciszternáig a vízhálózaton keresztül.
	 */
	public void SimulateWaterflow() {
		// Setup
		System.out.println("SimulateWaterflow");
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
		game = new Game(); // Valahogy resetelni kell a Game-et tesztenként
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
	 * @author Szikszai Levente
	 *         Az egyik Szerelő megpróbál bekötni egy csövet a Forrásba.
	 */
	public void WaterSourceHandlingAPipe() {

	public void WaterSourceHandlingAPipe()
	{
		Tabulator.reset();
		System.out.println("WaterSourceHandlingAPipe");
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
		if(isConnectedToSource.equals("I"))
		{
			Tabulator.printTab();
			System.out.println("Skeleton->"+holdingPipe.getName()+".addNeighbor("+position.getName()+")");
			holdingPipe.addNeighbor(position);
			Tabulator.printTab();
			System.out.println("<-" + holdingPipe.getName() + ".addNeighbor(" + position.getName() + ")");

			Tabulator.printTab();
			System.out.println("Skeleton->" + position.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
			position.addNeighbor(holdingPipe);
		} else {
			Tabulator.printTab();
	        System.out.println("<-"+position.getName()+".addNeighbor("+holdingPipe.getName()+")");
		}
		else
		{
			Tabulator.printTab();
			System.out.println("Skeleton->"+holdingPipe.getName()+".addNeighbor("+Pump1.getName()+")");
			holdingPipe.addNeighbor(Pump1);
			Tabulator.printTab();
			System.out.println("<-" + holdingPipe.getName() + ".addNeighbor(" + Pump1.getName() + ")");

			Tabulator.printTab();
			System.out.println("Skeleton->" + Pump1.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
			Pump1.addNeighbor(holdingPipe);
			Tabulator.printTab();
			System.out.println("<-" + Pump1.getName() + ".addNeighbor(" + holdingPipe.getName() + ")");
		}

		// Teszt
		
		//Teszt
		Tabulator.printTab();
		System.out.println("Skeleton->"+Repairman1.getName()+".PlacePipe()");
		Repairman1.PlacePipe();

		System.out.println("END<-Skeleton.WaterSourceHandlingAPipe()");
	}

	/**
	 * @author Vajda András
	 *         A szerelő megpróbálja felemeli az egyik cső végét
	 */
	public void RepairmanLiftsPipeTest() {
		// Inicializálás
		System.out.println("RepairmanLiftsPipe");
		Pump position = new Pump();
		position.setName("position");
		Pipe n = new Pipe();
		n.setName("n");
		position.addNeighbor(n);
		n.addNeighbor(position);
		Repairman r = new Repairman();
		r.setPosition(position);
		r.setName("r");
		// Teszt futása
		System.out.println(String.format("1.1 BEGIN->%s.liftpipe()", r.getName()));
		r.LiftPipe(0);
		System.out.println(String.format("END<-%s.liftpipe()", r.getName()));
	}

	/**
	 * @author Vajda András
	 *         Szerelő lehelyezi a nála lévő csövet
	 */
	public void RepairmanPlacingPipeTest() {
		// Inicializálás
		System.out.println("RepairmanPlacingPipe");
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
		// Teszt futtatása
		System.out.println(String.format("1.1 BEGIN->%s.placepipe()", r.getName()));
		r.PlacePipe();
		System.out.println(String.format("END<-%s.placepipe()", r.getName()));
	}

	/**
	 * @Author Vajda András
	 *         Szerelő lehelyez egy nála lévő csöve egy pumpára vagy cisternára
	 */
	public void PumpOrCisternHandlingAPipeTest() {

	}

	/**
	 * Pontok összeszámolása a kör végén
	 * Csak a pontszámito mukodesenek tesztelesehez valasszuk a N opciot a vizfolyas
	 * szimulalasanal.
	 * 
	 * 1 hátralveő körrel indul, meghivja az endGame-et is.
	 * 
	 * @author Gutási Ádám
	 */
	public void CalculatePointsTest() {
	}

	public void CalculatePointsTest() {
		System.out.println("Calculate Points");
		/** Inicializálás */
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

		/** Teszt futtatása */
		System.out.println("BEGIN->game.endTurn()");
		game.endTurn();
		System.out.println("<-game.endTurn()");
	}

	/**
	 * Egy szabotőr kilyukaszt egy Csövet.
	 */
	public void SaboteurDealsDamageOnPipeTest() {
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
	public void CharacterMovesTest() {
	}

	/**
	 * Pumpák véletlenszerű elromlása.
	 */
	public void DestructionTimeDestructsPumps() {
	}

	/**
	 * Új pumpa elhelyezésekor a DestructionTimer-ben tárolt pumpák listájának
	 * bővítése.
	 */
	public void DestructionTimerRecievesNewPump() {
	}

}
