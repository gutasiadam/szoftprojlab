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

                try
                {
                    if(readPhase==0)//Declaration
                    {
                        switch(next)
                        {
                            case "C": 
                            String name = sc.next();
                            next = sc.next();
                            Cistern C = new Cistern(Integer.parseInt(next));
                            C.setName(name);
                            gameElements.add(C);
                            break;

                            case "Pu": 
                            next = sc.next();
                            Pump pu = new Pump();
                            pu.setName(next);
                            // next = sc.next();
                            
                            //next = sc.next();
                            //next = sc.next();
                            gameElements.add(pu);
                            break;

                            case "Pi": 
                            next = sc.next();
                            Pipe pi = new Pipe();
                            pi.setName(next);
                            gameElements.add(pi);
                            break;

                            case "W": 
                            next = sc.next();
                            WaterSource ws = new WaterSource();
                            ws.setName(next);
                            gameElements.add(ws);
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
                        System.err.println("Load successful");
                }
                catch(Exception e)
                {
                    System.err.println("Load failed");
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
