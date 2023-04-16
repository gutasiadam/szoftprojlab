
public class Repairman extends Character {
	private RepairmanPlace position;
	private Pipe holdingPipe;
	private Pump holdingPump;
	private Game game;

	// mindig null kell legyen amikor nincs nala
	Repairman() {
		holdingPipe = null;
		holdingPump = null;
		game = null;
	}

	// getterek, setterek
	// a teszteknel hasznaltunk setPosit, de meg a 4.heten nem volt
	// ----------------------------------------------------------------
	public void setPosition(RepairmanPlace pos) {
		position = pos;
	}

	public Place getPosition() {
		return position;
	}

	public Pump getHoldingPump() {
		return holdingPump;
	}

	public void setHoldingPump(Pump p) {
		holdingPump = p;
	}

	public Pipe getHoldingPipe() {
		return holdingPipe;
	}

	public void setHoldingPipe(Pipe p) {
		holdingPipe = p;
	}

	public void setGame(Game g) {
		game = g;
	}

	// -----------------------------------------
	/*
	 * Ha kesz a getNeighbors
	 * public void move(int dir) {
	 * Element[] neighbors = position.getNeighbors();
	 * boolean success = neighbors[dir].accept(this);
	 * if(success) position.remove(this);
	 * }
	 */
	public void RepairElement() {
		System.out.println(this.getName() + "->" + position.getName() + ".repair();"); // TODO: Irja ki a nevet annak az
																						// objektumnak, aki hiv, es
																						// annak, akin hivnak
		position.repair();
	}

	public void adjustPump(int src, int dest) {
		position.adjust(src, dest);
	}

	public void LiftPipe(int dir) {
		if (holdingPipe == null) {
			System.out.println(String.format("\t1.2 %s->%s.lift(dir)", getName(), position.getName()));
			holdingPipe = position.lift(dir);
			System.out.println(
					String.format("\t%s<-%s.lift(dir): %s", getName(), position.getName(), holdingPipe.getName()));
		}
	}

	public void LiftPump() {
		if (holdingPump == null) {
			System.out.println(String.format("\t1.2 %s->%s.givePump()", getName(), position.getName()));
			holdingPump = position.givePump();
			if (holdingPump != null) {
				System.out.println(
						String.format("\t%s<-%s.givePump(): %s", getName(), position.getName(), holdingPump.getName()));
			} else {
				System.out.println(String.format("\t%s<-%s.givePump(): null", getName(), position.getName()));
			}
		}
	}

	public void PlacePipe() {
		System.out.println(String.format("\t1.2 %s->%s.placePipe()", getName(), position.getName()));
		position.placePipe(holdingPipe);
		System.out.println(String.format("\t%s<-%s.placePipe() : %s", getName(), position.getName(),
				position.placePipe(holdingPipe)));
	}

	public void PlacePump() {
		System.out.println(
				String.format("\t1.2 %s->%s.placePump(%s)", getName(), position.getName(), holdingPump.getName()));
		Pipe createdPipe = position.placePump(holdingPump);
		if (createdPipe != null) {
			System.out.println(String.format("\t%s<-%s.placePump(%s): %s", getName(), position.getName(),
					holdingPump.getName(), createdPipe.getName()));

			System.out.println(String.format("\t1.13 %s->game.addPump(%s)", getName(), holdingPump.getName()));
			System.out.println(String.format("\t\t1.14 game->timer.addPump(%s)", holdingPump.getName()));
			game.addPump(holdingPump);
			System.out.println(String.format("\t\tgame<-timer.addPump(%s)", holdingPump.getName()));
			System.out.println(String.format("\t%s<-game.addPump(%s)", getName(), holdingPump.getName()));

			System.out.println(String.format("\t1.15 %s->game.addPipe(%s)", getName(), createdPipe.getName()));
			game.addPipe(createdPipe);
			System.out.println(String.format("\t%s<-game.addPipe(%s)", getName(), createdPipe.getName()));

			System.out.println(String.format("\t1.16 %s holdingPump := null", getName()));
			holdingPump = null;
		} else {
			System.out.println(String.format("\t%s<-%s.placePump(%s): null", getName(), position.getName(),
					holdingPump.getName()));
		}
	}
	// step
}
