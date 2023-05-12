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

	private String selectedMenuItem; /** A kivalasztott parancs */

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
                            pu.setName(next);
                            gameElements.add(pu);
                            gameNonPipes.add(pu);
                            sabPointSource.add(pu);
                            break;

                            case "Pi": 
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
                            pi.setName(next);
                            gameElements.add(pi);
                            gamePipes.add(pi);
                            sabPointSource.add(pi);
                            break;

                            case "W": 
                            next = sc.next();
                            WaterSource ws = new WaterSource();
                            ws.setName(next);
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
                            if(element!=null) // miert csak ekkor?
                            {
                                Repairman r = new Repairman((RepairmanPlace)position,(Pipe)hPipe,(Pump)hPump);
                                r.setName(name);
                                r.setGame(game);
                                characters.add(r);
                            }
                            else
                            {
                                System.err.println(name+" Could not be Created because Element not foud: "+elementName);
                            }
                            break;

                            case "S": 
                            name = sc.next();
                            elementName = sc.next();
                            element = getElementByName(elementName);
                            position = element;
                            if(element!=null)
                            {
                                Saboteur r = new Saboteur((SaboteurPlace)position);
                                r.setName(name);
                                characters.add(r);
                            }
                            else
                            {
                                System.err.println(name+" Could not be Created because Element not foud: "+elementName);
                            }
                            break;

                            case "###Neighbors###": readPhase++; break;

                            default:  sc.nextLine();
                        }
                    }
                    else if(readPhase == 2)//Neighbors
                    {
                        name = sc.next();
                        Element element = getElementByName(name);
                        String row = sc.nextLine();
                        String [] neighborNames = row.split(" ");
                        for(String neighborName : neighborNames)
                        {
                            //Element neighbor = getElementByName(neighborName);
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
                            else
                            {
                                System.err.println(name+"'sneighbor could not be added because Neighbor not foud");
                            }
                        }
                    }
                        System.out.println(file+".txt Load successful");
                }
                catch(Exception e)
                {
                    System.out.println(file+".txt Load failed");
                    System.err.println("Hiba: "+next);

                }
            }
            sc.close();
            readPhase=3;

            if(readPhase==3)//GameState
                    {
                        int round = 0;
                        int sabPoints = 0;
                        int repPoints = 0;
                        int _slimey = 0;
                        int _sticky = 0;
                        //sticky és slimey max még kell!!!!!!!!!!!!!!!!!
                        try{
                            File fPoints = new File(file+"Points.txt"); 
                            Scanner scPoints = new Scanner(fPoints);

                            round = sc.nextInt();
                            repPoints = sc.nextInt();
                            sabPoints = sc.nextInt();
                        }
                        catch(Exception e)
                        {
                            round = 0;
                            sabPoints = 0;
                            repPoints = 0;
                        }
                        game.load(gameElements, sabPointSource, cisterns, repairmanGroup, saboteurGroup, repPoints, sabPoints, round);
                        System.out.println(file+"Points.txt Load Successful");
                    }
        }
        catch (Exception e) {
            System.out.println(file+"Points.txt Load Failed");
        }
    }

    public void save(String file)
    {
        PrintWriter pw;
        try 
        {
            pw = new PrintWriter(file+".txt");
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
            
            pw = new PrintWriter(file+"Points.txt");
            pw.println(game.toString());
            pw.close();
            System.out.println("Save Successful");
        } catch (FileNotFoundException e) {
            System.out.println("Save Failed");
        }
    }

    public void showMenu() {
        System.out.println("Kilepes: q");
		Scanner userInput = new Scanner(System.in);
        System.out.print("parancs > ");
		while (userInput.hasNextLine()) {
            try{
                String input = userInput.nextLine();
                String[] args = input.split(" ");
                selectedMenuItem = args[0].toLowerCase();
                args = Arrays.copyOfRange(args, 1, args.length);
                switch(selectedMenuItem){
                    case "adjust": currentCharacter.adjustPump(Integer.parseInt(args[0]), Integer.parseInt(args[1])); break;
                    case "move": currentCharacter.move(Integer.parseInt(args[0])); break;
                    case "stab": currentCharacter.dealDamage(); break;
                    case "repair": ((Repairman)currentCharacter).RepairElement(); break;
                    case "pickuppump": ((Repairman)currentCharacter).LiftPump(); break;
                    case "pickuppipe": ((Repairman)currentCharacter).LiftPipe(Integer.parseInt(args[0])); break;
                    case "placepump": ((Repairman)currentCharacter).PlacePump(); break;
                    case "placepipe": ((Repairman)currentCharacter).PlacePipe(); break;
                    case "stick": currentCharacter.makeSticky(); break;
                    case "slime": ((Saboteur)currentCharacter).putSlime(); break;
                    case "endturn": game.endTurn(); if(++currentCharacterInt>=characters.size()){ currentCharacterInt = 0;}currentCharacter = characters.get(currentCharacterInt);  break;
                    case "save": save(args[0]); break;
                    case "load": load(args[0]); currentCharacter = characters.get(currentCharacterInt); break;
                    case "help": System.out.println("adjust int int\nmove int\nstab\nrepair\npickuppump\npickuppipe int\nplacepump\nplacepipe\nstick\nslime\nendturn\nsave int\nload int\nhelp\nq"); break;
                    case "info": for(int i = 0; i < gameElements.size(); i++){ if(gameElements.get(i).getName().equals(args[0])) System.out.println(gameElements.get(i).toString()); } break;
                    case "q - kilepes": break;
                    default: System.out.println("Nem letezik a megadott parancs");
                }
                if(selectedMenuItem.equals("q")) break;
            }catch(ClassCastException e){
                System.out.println("Ilyen muveletre nem kepes a jatekos");
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Tul keves az argumentum");
            }catch(NullPointerException e){
                System.out.println("Nincs jatekos hozzaadva");
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
}
