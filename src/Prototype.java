import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Prototype {
    Game game;
    Prototype()
    {
        game = new Game();
    }

    public void load(String file)
    {
        File f = new File(file+".txt");
        try 
        {
            int readPhase = 0;
            Scanner sc = new Scanner(f);
            ArrayList<Repairman> repairmanGroup = new ArrayList<Repairman>();
            ArrayList<Saboteur> saboteurGroup = new ArrayList<Saboteur>();
            ArrayList<Element> gameElements = new ArrayList<Element>();
            while(sc.hasNext())
            {
                String next = sc.next();
                if(readPhase==0)//Declaration
                {
                    switch(next)
                    {
                        case "C": 
                            gameElements.add(new Cistern());
                            break;
                        case "Pu": 
                            break;
                        case "Pi": 
                            break;
                        case "W": 
                            break;
                        default: ;
                    }
                }
                else if(readPhase == 1)//Players
                {

                }
                else if(readPhase == 2)//Neighbors
                {
                    
                }
            }


            sc.close();
        }
        catch (Exception e) {
            System.out.println("Load Failed");
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
            System.out.println("Save Successful");
        } catch (FileNotFoundException e) {
            System.out.println("Save Failed");
        }
    }
}
