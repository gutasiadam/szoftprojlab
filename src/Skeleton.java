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
}
