import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.geom.*;

/**
 * A játék grafikus felületét megvalósító osztály.
 */
public class Gui {
    private JFrame frame; // Main Game Frame
    private JPanel menuPanel; // Menu Frame
    private JPanel endGamePanel; // End Game Frame
    private JPanel gamePanel; // End Game Frame
    private JPanel activePanel; // A jelenelg aktív keret. Ez lehet a menü, a játék vagy a vége.
    private JLabel turn; //Soronlevő játékos neve
    private JLabel lSaboteurPoints;
    private JLabel lRepairmenPoints;
    private JButton endturn;
    private JTextField log;
    JSpinner sPlayerCount;
    ArrayList<ActionButton> actionButtons;
    ArrayList<ElementButton> elementButtons;

    public Gui(){
        // ---------------FRAME INIT-----------------
        frame = new JFrame("codeX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(null); //Lehet jobb lenne, ha csak a gamePanel lenne null layouttal
        //frame.setLayout(new BorderLayout()); -- Ezzel megjelenik, de nullal nem


       // ---------------MENU PANEL-----------------
       menuPanel = new JPanel();
       menuPanel.setSize(1280, 720);

       menuPanel.setLayout(null); // Null layout beállítása a menü panelen

       JLabel lRoundSettings = new JLabel("Körök száma: ");
       Dimension size = lRoundSettings.getPreferredSize();
       lRoundSettings.setBounds(600, 100, size.width, size.height);
       menuPanel.add(lRoundSettings);

       JLabel lPlayerCount = new JLabel("Játékosok száma: ");
       size = lPlayerCount.getPreferredSize();
       lPlayerCount.setBounds(600, 200, size.width, size.height);
       menuPanel.add(lPlayerCount);

       JSpinner sRoundSettings = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
       sRoundSettings.setBounds(700, 100, 40, 30);
       menuPanel.add(sRoundSettings);
       sRoundSettings.addChangeListener(new ChangeListener()
       {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            Game.getInstance().setTurns((int)spinner.getValue());
        }
        
       });

       sPlayerCount = new JSpinner(new SpinnerNumberModel(4, 4, 8, 1));
       sPlayerCount.setBounds(700, 200, 40, 30);
       menuPanel.add(sPlayerCount);

       JButton bStart = new JButton("Start");
       bStart.setBounds(1080, 200, 100, 30);
       bStart.addActionListener(e -> nextPanel());
       menuPanel.add(bStart);

        //TODO: Scoreboard hozzáadása
        
        

        //---------------MAIN GAME PANEL-----------------
        gamePanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < elementButtons.size(); i++){
                    ElementButton eb = elementButtons.get(i);
                    ArrayList<ElementButton> neigh = eb.getNeighboursElementButton(elementButtons);
                    if(neigh != null){
                        for(ElementButton n : neigh){
                            drawLineBetweenButtons(eb, n, Color.BLACK, g);
                        }
                    }
                }
                for(int i = 0; i < elementButtons.size(); i++){
                    ElementButton eb = elementButtons.get(i);
                    eb.drawWaterFlowDirection(g, elementButtons);
                }
            }
        };
        gamePanel.setLayout(null);
        gamePanel.setSize(1280, 720);
        elementButtons = new ArrayList<ElementButton>();
        //Soronlevő játékos neve
        turn = new JLabel("NULL");
        turn.setBounds(40, 10, 0, 0);
        gamePanel.add(turn);

        endturn = new JButton("End turn");
        gamePanel.add(endturn);

        //Initialize log textfield
        log = new JTextField("Log");
        log.setEditable(false);
        log.setBounds(980, 100, 300, 500); // TODO: Pontos érték megadása ide
        gamePanel.add(log);

        // Első hat pályaelem
        Cistern c1 = new Cistern(0);
        ElementButton cistern1 = new ElementButton(c1);
        cistern1.setBounds(200, 500, 90, 90);
        elementButtons.add(cistern1);
        Game.getInstance().addCistern(c1);
        gamePanel.add(cistern1);

        WaterSource w1 = new WaterSource();
        ElementButton ws1 = new ElementButton(w1);
        ws1.setBounds(200, 100, 90, 90);
        elementButtons.add(ws1);
        Game.getInstance().addElement(w1);
        gamePanel.add(ws1);

        Pump p1 = new Pump();
        ElementButton pump1 = new ElementButton(p1);
        pump1.setBounds(200, 300, 90, 90);
        elementButtons.add(pump1);
        Game.getInstance().addPump(p1);
        gamePanel.add(pump1);

        Cistern c2 = new Cistern(0);
        ElementButton cistern2 = new ElementButton(c2);
        cistern2.setBounds(600, 500, 90, 90);
        elementButtons.add(cistern2);
        Game.getInstance().addCistern(c2);
        gamePanel.add(cistern2);

        WaterSource w2 = new WaterSource();
        ElementButton ws2 = new ElementButton(w2);
        ws2.setBounds(600, 100, 90, 90);
        elementButtons.add(ws2);
        Game.getInstance().addElement(w2);
        gamePanel.add(ws2);

        Pump p2 = new Pump(true, true, 0);
        ElementButton pump2 = new ElementButton(p2);
        pump2.setBounds(600, 300, 90, 90);
        elementButtons.add(pump2);
        Game.getInstance().addPump(p2);
        gamePanel.add(pump2);

        //----------------PIPES--------------------------
        Pipe pi1 = new Pipe(false, false, 0, 0, 0);
        pi1.addNeighbor(w1);
        w1.addNeighbor(pi1);
        pi1.addNeighbor(p1);
        p1.addNeighbor(pi1);
        ElementButton pie1 = new ElementButton(pi1);
        pie1.setBounds(215, 215, 60, 60);
        elementButtons.add(pie1);
        Game.getInstance().addPipe(pi1);
        gamePanel.add(pie1);

        Pipe pi2 = new Pipe(true, true, 0, 5, 5);
        pi2.addNeighbor(c1);
        c1.addNeighbor(pi2);
        pi2.addNeighbor(p1);
        p1.addNeighbor(pi2);
        ElementButton pie2 = new ElementButton(pi2);
        pie2.setBounds(215, 415, 60, 60);
        elementButtons.add(pie2);
        Game.getInstance().addPipe(pi2);
        gamePanel.add(pie2);

        Pipe pi3 = new Pipe(false, false, 0, 0, 0);
        pi3.addNeighbor(c2);
        c2.addNeighbor(pi3);
        pi3.addNeighbor(p2);
        p2.addNeighbor(pi3);
        ElementButton pie3 = new ElementButton(pi3);
        pie3.setBounds(615, 415, 60, 60);
        elementButtons.add(pie3);
        Game.getInstance().addPipe(pi3);
        gamePanel.add(pie3);

        Pipe pi4 = new Pipe(false, false, 0, 0, 0);
        pi4.addNeighbor(w2);
        w2.addNeighbor(pi4);
        pi4.addNeighbor(p2);
        p2.addNeighbor(pi4);
        ElementButton pie4 = new ElementButton(pi4);
        pie4.setBounds(615, 215, 60, 60);
        elementButtons.add(pie4);
        Game.getInstance().addPipe(pi4);
        gamePanel.add(pie4);

        Pipe pi5 = new Pipe(false, false, 0, 0, 0);
        pi5.addNeighbor(p1);
        p1.addNeighbor(pi5);
        pi5.addNeighbor(p2);
        p2.addNeighbor(pi5);
        ElementButton pie5 = new ElementButton(pi5);
        pie5.setBounds(415, 315, 60, 60);
        elementButtons.add(pie5);
        Game.getInstance().addPipe(pi5);
        gamePanel.add(pie5);

        p1.adjust(0, 1);
        p2.adjust(0, 1);

        // ---------------END GAME FRAME-----------------
        endGamePanel = new JPanel();
        endGamePanel.setSize(1280, 720);
        lSaboteurPoints = new JLabel("0");
        lRoundSettings.setBounds(600, 100, 0, 0);
        endGamePanel.add(lSaboteurPoints);
        lRepairmenPoints = new JLabel("0");
        lPlayerCount.setBounds(600, 200, 0, 0);
        endGamePanel.add(lRepairmenPoints);

        //------------------------------------------------
        //Keret megjelenítése az aktív panellel
        activePanel = menuPanel;
        frame.getContentPane().add(activePanel);
        frame.setVisible(true);
    }

    public void addToView(JButton b){
        frame.add(b); //Csak a frame-hez lehet hozzáadni gombot.
    }

    public void removeFromView(JButton b){
        frame.remove(b); // Csak a frame-től lehet eltávolítani gombot.
    }

    public void updateFrame(){

        //Redraw frame
        frame.repaint();
        for(ElementButton e : elementButtons){
            e.update();
        }
    }

    /**
     * A következő panelre lépteti a játékot.
     * menü -> játék -> vége
     */
    public void nextPanel(){
        
        frame.setVisible(false);
        frame.getContentPane().removeAll();
        if(activePanel == menuPanel){
            activePanel = gamePanel;
            int playerc = (Integer)sPlayerCount.getValue();
            for(int i = 0; i < playerc; i++){
                Character c;
                if(i%2==0){
                    Element e1 = Game.getInstance().getGameElements().get(0);
                    c = new Repairman(e1, null, null);
                    if(i==0) c = new Repairman(e1, null, new Pump());
                    e1.addStandingOn(c);
                    Game.getInstance().addRepairman((Repairman)c);
                }else{
                    Element e2 = Game.getInstance().getGameElements().get(2);
                    c = new Saboteur(e2);
                    e2.addStandingOn(c);
                    Game.getInstance().addSaboteur((Saboteur)c);
                }
            }
            updateFrame();
            Thread gameThread = new Thread()
            {
                @Override
                public void run()
                {
                    Game.getInstance().playGame();
                }
            };
            gameThread.start();
        }
        else{
            activePanel=endGamePanel;
        }
        frame.getContentPane().add(activePanel);
        frame.setVisible(true);
    }

    /**
     * Pálya feltöltése az elemekkel
     * 
     * Ezt az után lehet meghívni, hogy a Game-ben már létrehoztuk a pálya elemeit.
     */
    public void initLevel(){
        ArrayList<Element> gameElements=Game.getInstance().getGameElements();
        for (Element element : gameElements) {
            ElementButton button = new ElementButton(element);
            elementButtons.add(button);
            frame.add(button);
            //TODO: Hogyan számoljuk ki, hol legyen a gomb?
        }
    }

    //Soronlevő játékos nevének beállítása
    public void setTurn(String name){
        turn.setText(name);
    }

    //Vonal rajzolása két gomb között, a megadott színnel
    public void drawLineBetweenButtons(ElementButton b1, ElementButton b2, Color color, Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2)); //2px Vastag
        g2.setColor(color);
        g2.drawLine(b1.getBounds().x + b1.getWidth()/2, b1.getBounds().y + b1.getHeight()/2, b2.getBounds().x + b2.getWidth()/2, b2.getBounds().y + b2.getHeight()/2);
    }

    
}
