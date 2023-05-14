import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Prototype {
    Game game;
    ArrayList<Repairman> repairmanGroup;
    ArrayList<Saboteur> saboteurGroup;
    ArrayList<Element> gameElements;
    ArrayList<Pipe> gamePipes;
    ArrayList<NonPipe> gameNonPipes;
    ArrayList<SaboteurPointSource> sabPointSource;
    ArrayList<Cistern> cisterns;
    ArrayList<Character> characters;
    Character currentCharacter;
    int currentCharacterInt = 0;
    int ac = 0; //* ActionCounter */

	private String selectedMenuItem; /** A kivalasztott parancs */

    /**
     * Inicializálja a Prototype-ot
     */
    Prototype()
    {
        game = new Game();
        repairmanGroup = new ArrayList<Repairman>();
        saboteurGroup = new ArrayList<Saboteur>();
        gameElements = new ArrayList<Element>();
        gamePipes = new ArrayList<Pipe>();
        gameNonPipes = new ArrayList<NonPipe>();
        sabPointSource = new ArrayList<SaboteurPointSource>();
        cisterns = new ArrayList<Cistern>();
        characters = new ArrayList<Character>();
    }

    /**
     * Visszaadja az újonnan beolvasott element-ek közül a név alapján a megfelelőt
     * @param name A keresett element neve
     * @return element vagy ha nem található akkor null
     */
    private Element getElementByName(String name)
    {
        for(Element element : gameElements)
        {
            if(element.getName().equals(name))
            {
                return element;
            }
        }
        return null;
    }

    /**
     * Visszaadja az adott nevű NonPipe-ot ha létezik
     * @param name A keresett NonPipe neve
     * @return A NonPipe referenciája ha létezik, null ha nem
     */
    private NonPipe getNonPipestByName(String name)
    {
        for(NonPipe nonpipe : gameNonPipes)
        {
            if(nonpipe.getName().equals(name))
            {
                return nonpipe;
            }
        }
        return null;
    }

    /**
     * Visszaadja az adott nevű csövet ha létezik
     * @param name A keresett cső neve
     * @return A Cső referenciája ha létezik, null ha nem
     */
    private Pipe getPipesByName(String name)
    {
        for(Pipe pipe : gamePipes)
        {
            if(pipe.getName().equals(name))
            {
                return pipe;
            }
        }
        return null;
    }

    /**
     * BEtölti a mapdeclarations mappából a megadott nevű txt fájlt
     * @param file A betöltendő fájl neve
     */
    public void load(String file)
    {
        gameElements.clear();
        gameNonPipes.clear();
        gamePipes.clear();
        sabPointSource.clear();
        cisterns.clear();
        File f = new File("mapdeclarations/"+file+".txt");
        try 
        {
            int readPhase = 0;
            Scanner sc = new Scanner(f);
            sc.nextLine(); //kidobjuk az első sort
            while(sc.hasNext())
            {
                String next = sc.next();
                String name;
                boolean isBroken;
                boolean containingWater;
                boolean hasHole;
                int slimey;
                int sticky;
                int leakedWaterAmount;
                try
                {
                    if(readPhase==0)//Declaration
                    {
                        switch(next)
                        {
                            case "C": 
                            name = sc.next();
                            next = sc.next();
                            Cistern C = new Cistern(Integer.parseInt(next));
                            C.setName(name);
                            gameElements.add(C);
                            gameNonPipes.add(C);
                            cisterns.add(C);
                            break;

                            case "Pu": 
                            name = sc.next();
                            next = sc.next();
                            isBroken = Boolean.parseBoolean(next);
                            next = sc.next();
                            containingWater = Boolean.parseBoolean(next);
                            next = sc.next();
                            leakedWaterAmount = Integer.parseInt(next);
                            Pump pu = new Pump(isBroken,containingWater,leakedWaterAmount);
                            pu.setName(name);
                            gameElements.add(pu);
                            gameNonPipes.add(pu);
                            sabPointSource.add(pu);
                            break;

                            case "Pi": 
                            name = sc.next();
                            next = sc.next();
                            hasHole = Boolean.parseBoolean(next);
                            next = sc.next();
                            containingWater = Boolean.parseBoolean(next);
                            next = sc.next();
                            leakedWaterAmount = Integer.parseInt(next);
                            next = sc.next();
                            slimey = Integer.parseInt(next);
                            next = sc.next();
                            sticky = Integer.parseInt(next);
                            Pipe pi = new Pipe(hasHole,containingWater,leakedWaterAmount,slimey,sticky);
                            pi.setName(name);
                            gameElements.add(pi);
                            gamePipes.add(pi);
                            sabPointSource.add(pi);
                            break;

                            case "W": 
                            name = sc.next();
                            WaterSource ws = new WaterSource();
                            ws.setName(name);
                            gameElements.add(ws);
                            gameNonPipes.add(ws);
                            break;

                            case "###Players###": readPhase++; break;

                            default:  sc.nextLine();
                        }
                    }
                    else if(readPhase == 1)//Players
                    {
                        Element element;
                        Element hPipe;
                        Element hPump;
                        Element position;
                        String elementName;
                        switch(next)
                        {
                            case "R": 
                            name = sc.next();
                            elementName = sc.next();
                            element = getElementByName(elementName);
                            position = element;

                            elementName = sc.next();
                            element = getElementByName(elementName);
                            hPipe = element;

                            elementName = sc.next();
                            element = getElementByName(elementName);
                            hPump = element;
                            if(position!=null)
                            {
                                Repairman r = new Repairman((RepairmanPlace)position,(Pipe)hPipe,(Pump)hPump);
                                r.setName(name);
                                r.setGame(game);
                                repairmanGroup.add(r);
                                characters.add(r);
                                position.addStandingOn(r);
                            }
                            else
                            {
                                System.err.println(name+" Could not be Created because Position not foud");
                            }
                            break;

                            case "S": 
                            name = sc.next();
                            elementName = sc.next();
                            element = getElementByName(elementName);
                            position = element;
                            if(element!=null)
                            {
                                Saboteur s = new Saboteur((SaboteurPlace)position);
                                s.setName(name);
                                s.setGame(game);
                                saboteurGroup.add(s);
                                characters.add(s);
                                position.addStandingOn(s);
                            }
                            else
                            {
                                System.err.println(name+" Could not be Created because Position not foud: ");
                            }
                            break;

                            case "###Neighbors###": readPhase++; break;

                            default:  sc.nextLine();
                        }
                    }
                    else if(readPhase == 2)//Neighbors
                    {
                        name = next;
                        Element element = getElementByName(name);
                        String row = sc.nextLine();
                        String [] neighborNames = row.split(" ");

                        for(String neighborName : neighborNames)
                        {
                            Pipe pipeN = getPipesByName(neighborName);
                            NonPipe nonpipeN = getNonPipestByName(neighborName);

                            if(pipeN!=null)
                            {
                                NonPipe nonpipe = (NonPipe) element;
                                nonpipe.addNeighbor(pipeN);
                            }
                            else if(nonpipeN!=null)
                            {
                                Pipe pipe = (Pipe) element;
                                pipe.addNeighbor(nonpipeN);
                            }
                        }
                    }
                        
                }
                catch(Exception e)
                {
                    System.out.println(file+".txt Load failed");
                }
            }
            System.out.println(file+".txt Load successful");
            sc.close();
            readPhase=3;

            if(readPhase==3)//GameState
                    {
                        int round = 0;
                        int sabPoints = 0;
                        int repPoints = 0;
                        int _slimey = 0;
                        int _sticky = 0;
                        try{
                            File fPoints = new File(file+"Points.txt"); 
                            Scanner scPoints = new Scanner(fPoints);

                            round = sc.nextInt();
                            repPoints = sc.nextInt();
                            sabPoints = sc.nextInt();
                            _slimey = sc.nextInt();
                            _sticky = sc.nextInt();
                            scPoints.close();
                        }
                        catch(Exception e)
                        {
                            round = 0;
                            sabPoints = 0;
                            repPoints = 0;
                            _slimey = 0;
                            _sticky = 0;
                        }
                        game.load(gameElements, sabPointSource, cisterns, repairmanGroup, saboteurGroup, repPoints, sabPoints, round,_slimey,_sticky);
                        //System.out.println(file+"Points.txt Load Successful");
                    }
        }
        catch (Exception e) {
            System.out.println(file+".txt Load Failed");
        }
    }

    /**
     * Betölti a megadott nevű txt fájlt a mapdeclarations mappából
     * @param file A betöltendő fájl neve
     */
    public void save(String file)
    {
        PrintWriter pw;
        try 
        {
            File f = new File("mapdeclarations/"+file+".txt");
            pw = new PrintWriter(f);
            ArrayList<Element> gameElements = game.getGameElements();
            pw.println("###Declaration###");
            for(Element element:gameElements)
            {
                pw.println(element.toString());
            }
            pw.println("###Players###");
            ArrayList<Repairman> repairmanGroup = game.getRepairmanGroup();
            ArrayList<Saboteur> saboteurGroup = game.getSaboteurGroup();

            for(Repairman character:repairmanGroup)
            {
                pw.println(character.toString());
            }
            for(Saboteur character:saboteurGroup)
            {
                pw.println(character.toString());
            }
            pw.println("###Neighbors###");
            for(Element element:gameElements)
            {
                pw.print(element.getName()+" ");
                for(Element neighbor:element.getNeighbors())
                {
                    pw.print(neighbor.getName()+" ");
                }
                pw.println();
            }
            pw.close();
            
            f = new File("mapdeclarations/"+file+"Points.txt");
            pw = new PrintWriter(f);
            pw.println(game.toString());
            pw.close();
            System.out.println("Save Successful");
        } catch (FileNotFoundException e) {
            System.out.println("Save Failed");
        }
    }
    /**
     * Megjeleníti a felhasználó által használható menüt.
     */
    public void showMenu() {
        System.out.println("Kilepes: q");
        //System.out.println("Parancsok mutatasa: help");
		Scanner userInput = new Scanner(System.in);
        System.out.print("parancs > ");
		while (userInput.hasNextLine()) {
            try{
                String input = userInput.nextLine();
                String[] args = input.split(" ");
                selectedMenuItem = args[0].toLowerCase();
                args = Arrays.copyOfRange(args, 1, args.length);
                switch(selectedMenuItem){
                    case "adjust": if(dealAC()) currentCharacter.adjustPump(Integer.parseInt(args[0]), Integer.parseInt(args[1])); break;
                    case "move": if(dealAC()) currentCharacter.move(Integer.parseInt(args[0])); break;
                    case "stab": if(dealAC()) currentCharacter.dealDamage(); break;
                    case "repair": if(dealAC()) ((Repairman)currentCharacter).RepairElement(); break;
                    case "pickuppump": if(dealAC()) ((Repairman)currentCharacter).LiftPump(); break;
                    case "pickuppipe": if(dealAC()) ((Repairman)currentCharacter).LiftPipe(Integer.parseInt(args[0])); break;
                    case "placepump": if(dealAC()) ((Repairman)currentCharacter).PlacePump(); break;
                    case "placepipe": if(dealAC()) ((Repairman)currentCharacter).PlacePipe(); break;
                    case "stick": if(dealAC()) currentCharacter.makeSticky(); break;
                    case "slime": if(dealAC()) ((Saboteur)currentCharacter).putSlime(); break;
                    case "endturn": endTurn(); break;
                    case "save": save(args[0]); break;
                    case "load": load(args[0]); currentCharacter = characters.get(currentCharacterInt); break;
                    case "help":  help(); break;
                    case "info": info(args[0]); break;
                    case "togglerandom": togglerandom(args[0]); break;
                    case "q": break;
                    default: System.out.println("Nem letezik a megadott parancs");
                }
                if(selectedMenuItem.equals("q")) break;
            }catch(ClassCastException e){
                System.out.println("Ilyen muveletre nem kepes a jatekos");
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Tul keves az argumentum");
            }catch(NullPointerException e){
                if(characters.size()<=0) {
                    System.out.println("Nincs jatekos hozzaadva");
                } else {
                    System.out.println("Nullptr: " + e.toString());
                }
            }catch(NumberFormatException e){
                System.out.println("Ervenytelen argumentum");
            }catch(Exception e){
                System.out.println(e.getClass());
            }finally{
                if(!selectedMenuItem.equals("q")) System.out.print("parancs > ");
            }
		}
		userInput.close();
	}

    /**
     * Engedélyezi és letiltja a véletlen eseményeket
     * @param param I - bekapcsol, N - kikapcsol
     */
    public void togglerandom(String param)
    {
        if(param.equals("I"))
            game.setRandomEnabled(true);
        else if(param.equals("N"))
            game.setRandomEnabled(false);
        else
        System.out.println("Invalid input");
    }

    /**
     * A help függvényhez használatos segédfüggvény, mely megmondja hogy a jelenlegi játékos Szerelő-e
     * @return true - ha az aktuális játékos Szerelő, false - ha Szabotőr
     */
    private boolean isCurrentRepairman()
    {
        boolean out = false;
        ArrayList<Repairman> repairmans = game.getRepairmanGroup();
        for(Repairman repairman : repairmans)
        {
            if(repairman.getName().equals(currentCharacter.getName()))
            {
                out = true;
            }
        }
        return out;
    }

    /**
     * A help parancsot megvalósítő függvény
     */
    public void help()
    {
        if(isCurrentRepairman())
            System.out.println("adjust int int\nmove int\nstab\nrepair\npickuppump\npickuppipe int\nplacepump\nplacepipe\nstick\nendturn\nsave fileName\nload fileName\nhelp\ninfo string\ntogglerandom I/N\nq");
        else
        System.out.println("adjust int int\nmove int\nstab\nstick\nslime\nendturn\nsave fileName\nload fileName\nhelp\ninfo string\ntogglerandom I/N\nq");
        }

    public void info(String name){
        boolean found = false;
        for(int i = 0; i < gameElements.size(); i++){
            if(gameElements.get(i).getName().equals(name)){
                System.out.println(gameElements.get(i).toString());
                found = true;
            }
        }
        for(int i = 0; i < characters.size(); i++){
            if(characters.get(i).getName().equals(name)){
                System.out.println(characters.get(i).toString());
                found = true;
            }
        }
        if(!found) System.out.println("Object " + name + " not found");
    }

    public boolean dealAC(){
        if(ac>=3) System.out.println("Out of action");
        return ac++<3;
    }

    public void endTurn(){
        try{
            game.endTurn();
        }catch(Exception e){
            e.printStackTrace();
        }
        String prevname = currentCharacter.getName();
        if(++currentCharacterInt>=characters.size()){
            currentCharacterInt = 0;
        }
        currentCharacter = characters.get(currentCharacterInt);
        ac = 0;
        System.out.println(prevname + "\'s turn ended, "+currentCharacter.getName()+"\'s turn next");
    }
}
