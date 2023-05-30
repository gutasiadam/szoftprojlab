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
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A játék grafikus felületét megvalósító osztály.
 */
public class Gui {
    private JFrame frame; // Main Game Frame
    private JPanel menuPanel; // Menu Frame
    private JPanel endGamePanel; // End Game Frame
    private JPanel gamePanel; // End Game Frame
    private JPanel activePanel; // A jelenelg aktív keret. Ez lehet a menü, a játék vagy a vége.
    private String turn; //Soronlevő játékos neve
    private JLabel lResult;
    private JLabel lSaboteurPoints;
    private JLabel lRepairmenPoints;
    private JLabel lgSaboteurPoints;
    private JLabel lgRepairmenPoints;
    private JLabel lWhosTurn; // Soronlevő játékost kiíró JLabel a GUI-n
    private JLabel lTurnPoints; // Hátralevő körk száma
    private JButton endturn;
    private JTextArea log;
    private int repairmanNum = 1;
    private int saboteurNum = 1;
    JSpinner sPlayerCount;
    ArrayList<ActionButton> actionButtons;
    ArrayList<ElementButton> elementButtons;
    private Pipe pi6;

    // Az egyetlen Gui objektum
    private static Gui instance = new Gui();;

    /**
     * Visszaadja a Singleton Gui objektumot
     * 
     * @return Singleton Gui objektum
     */
    public static Gui getInstance() {
        return instance;
    }

    private Gui(){
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

       JLabel lRoundSettings = new JLabel("Körök száma");
       lRoundSettings.setBounds(450, 100, 300, 30);
       lRoundSettings.setFont(new Font("Arial", Font.PLAIN, 30)); // Creating an Arial Font Style with size 30
       menuPanel.add(lRoundSettings);

       JLabel lPlayerCount = new JLabel("Játékosok száma");
       lPlayerCount.setBounds(450, 300, 300, 30);
       lPlayerCount.setFont(new Font("Arial", Font.PLAIN, 30)); // Creating an Arial Font Style with size 30
       menuPanel.add(lPlayerCount);

       JSpinner sRoundSettings = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
       sRoundSettings.setBounds(700, 90, 50, 50);
       sRoundSettings.setFont(new Font("Arial", Font.PLAIN, 30));
       menuPanel.add(sRoundSettings);
       sRoundSettings.addChangeListener(new ChangeListener()
       {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            Game.getInstance().setTurns((int)spinner.getValue());
        }
        
       });

       sPlayerCount = new JSpinner(new SpinnerNumberModel(4, 4, 8, 2));
       sPlayerCount.setBounds(700, 290, 50, 50);
       sPlayerCount.setFont(new Font("Arial", Font.PLAIN, 30));
       menuPanel.add(sPlayerCount);

       JButton bStart = new JButton("Start");
       bStart.setBounds(500, 600, 200, 50);
       bStart.setFont(new Font("Arial", Font.PLAIN, 30));
       bStart.setBackground(Color.GRAY);
       bStart.setForeground(Color.BLACK);
       bStart.addActionListener(e -> nextPanel());
       menuPanel.add(bStart);
        
        
        //---------------MAIN GAME PANEL-----------------
        gamePanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < elementButtons.size(); i++){
                    ElementButton eb = elementButtons.get(i);
                    ArrayList<ElementButton> neigh = eb.getNeighboursElementButton(elementButtons);
                    if(neigh != null){
                        if(neigh.size()==2 && neigh.get(0).equals(neigh.get(1))){
                            drawLineBetweenButtons(eb, neigh.get(0), Color.BLACK, g, true);
                        }else{
                            for(ElementButton n : neigh){
                                drawLineBetweenButtons(eb, n, Color.BLACK, g, false);
                            }
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
        // turn = new JLabel("NULL");
        // turn.setBounds(40, 10, 0, 0);
        // gamePanel.add(turn);

        endturn = new JButton("End turn");
        gamePanel.add(endturn);

        //Initialize log textfield
        log = new JTextArea("Log");
        log.setEditable(false);
        log.setBounds(980, 50, 300, 500); // TODO: Pontos érték megadása ide
        JScrollPane scroll = new JScrollPane (log);
        gamePanel.add(log);
        gamePanel.add(scroll);

        //Main Game Panel - ScoreBoard
        JPanel jPanelScoreBoard = new JPanel();
        jPanelScoreBoard.setLayout(null);
        jPanelScoreBoard.setBounds(20, 20, 120, 90);
        jPanelScoreBoard.setBorder(BorderFactory.createLineBorder(Color.black));

        //Scoreboard label
        JLabel lScoreBoard = new JLabel("ScoreBoard");
        lScoreBoard.setFont(new Font("Arial", Font.BOLD, 20));
        lScoreBoard.setBounds(5, 0, 200, 20);
        jPanelScoreBoard.add(lScoreBoard);

        //Saboteurs label
        JLabel lSaboteurs = new JLabel("Saboteurs");
        lSaboteurs.setFont(new Font("Arial", Font.BOLD, 15));
        lSaboteurs.setBounds(5, 25, 200, 20);
        jPanelScoreBoard.add(lSaboteurs);

        //Saboteurs points label
        lgSaboteurPoints = new JLabel("0");
        lgSaboteurPoints.setFont(new Font("Arial", Font.PLAIN, 15));
        lgSaboteurPoints.setBounds(100, 25, 200, 20);
        jPanelScoreBoard.add(lgSaboteurPoints);

        //Repairmen label
        JLabel lRepairmen = new JLabel("Repairmen");
        lRepairmen.setFont(new Font("Arial", Font.BOLD, 15));
        lRepairmen.setBounds(5, 45, 200, 20);
        jPanelScoreBoard.add(lRepairmen);
        

        //Repairmen points label
        lgRepairmenPoints = new JLabel("0");
        lgRepairmenPoints.setFont(new Font("Arial", Font.PLAIN, 15));
        lgRepairmenPoints.setBounds(100, 45, 200, 20);
        jPanelScoreBoard.add(lgRepairmenPoints);


        //Turn label
        JLabel lTurn = new JLabel("Turns: ");
        lTurn.setFont(new Font("Arial", Font.BOLD, 15));
        lTurn.setBounds(5, 65, 200, 20);
        jPanelScoreBoard.add(lTurn);

        //Turn points label
        lTurnPoints = new JLabel("0");
        lTurnPoints.setFont(new Font("Arial", Font.PLAIN, 15));
        lTurnPoints.setBounds(100, 65, 200, 20);
        jPanelScoreBoard.add(lTurnPoints);

        //Add scoreboard to main game panel
        gamePanel.add(jPanelScoreBoard);


        //Who`s turn label
        lWhosTurn = new JLabel("...-s turn");
        lWhosTurn.setFont(new Font("Arial", Font.BOLD, 25));
        lWhosTurn.setBounds(980, 20, 300, 20);
        gamePanel.add(lWhosTurn);

        //End Move button
        JButton bEndMove = new JButton("End Move");
        bEndMove.setBounds(980, 550, 300, 50);
        bEndMove.setFont(new Font("Arial", Font.PLAIN, 30));
        bEndMove.setBackground(Color.WHITE);
        bEndMove.setForeground(Color.BLACK);
        bEndMove.addActionListener(e -> Control.getInstance().EndMove());
        gamePanel.add(bEndMove);
        // Első hat pályaelem
        Cistern c1 = new Cistern(0);
        c1.setName("Cistern1");
        ElementButton cistern1 = new ElementButton(c1);
        cistern1.setBounds(200, 500, 90, 90);
        elementButtons.add(cistern1);
        Game.getInstance().addCistern(c1);
        gamePanel.add(cistern1);

        WaterSource w1 = new WaterSource();
        w1.setName("WaterSource1");
        ElementButton ws1 = new ElementButton(w1);
        ws1.setBounds(200, 100, 90, 90);
        elementButtons.add(ws1);
        Game.getInstance().addElement(w1);
        gamePanel.add(ws1);

        Pump p1 = new Pump();
        p1.setName("Pump1");
        ElementButton pump1 = new ElementButton(p1);
        pump1.setBounds(200, 300, 90, 90);
        elementButtons.add(pump1);
        Game.getInstance().addPump(p1);
        gamePanel.add(pump1);

        Cistern c2 = new Cistern(0);
        c2.setName("Cistern2");
        ElementButton cistern2 = new ElementButton(c2);
        cistern2.setBounds(600, 500, 90, 90);
        elementButtons.add(cistern2);
        Game.getInstance().addCistern(c2);
        gamePanel.add(cistern2);

        WaterSource w2 = new WaterSource();
        w2.setName("WaterSource2");
        ElementButton ws2 = new ElementButton(w2);
        ws2.setBounds(600, 100, 90, 90);
        elementButtons.add(ws2);
        Game.getInstance().addElement(w2);
        gamePanel.add(ws2);

        Pump p2 = new Pump(true, true, 0);
        p2.setName("Pump2");
        ElementButton pump2 = new ElementButton(p2);
        pump2.setBounds(600, 300, 90, 90);
        elementButtons.add(pump2);
        Game.getInstance().addPump(p2);
        gamePanel.add(pump2);

        //----------------PIPES--------------------------
        Pipe pi1 = new Pipe(false, false, 0, 0, 0);
        pi1.setName("Pipe1");
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
        pi2.setName("Pipe2");
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
        pi3.setName("Pipe3");
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
        pi4.setName("Pipe4");
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
        pi5.setName("Pipe5");
        pi5.addNeighbor(p1);
        p1.addNeighbor(pi5);
        pi5.addNeighbor(p2);
        p2.addNeighbor(pi5);
        ElementButton pie5 = new ElementButton(pi5);
        pie5.setBounds(415, 315, 60, 60);
        elementButtons.add(pie5);
        Game.getInstance().addPipe(pi5);
        gamePanel.add(pie5);

        pi6 = new Pipe(false, false, 0, 0, 0);
        pi6.setName("Pipe6");
        pi6.addNeighbor(p2);
        p2.addNeighbor(pi6);
        ElementButton pie6 = new ElementButton(pi6);
        pie6.setEnabled(true);
        pie6.setBounds(815, 315, 60, 60);
        elementButtons.add(pie6);
        Game.getInstance().addPipe(pi6);
        gamePanel.add(pie6);

        p1.adjust(0, 1);
        p2.adjust(0, 1);

        JButton bEnd = new JButton("End Game");
        bEnd.setBounds(980, 600, 300, 50);
        bEnd.addActionListener(e -> nextPanel());
        gamePanel.add(bEnd);

        // ---------------END GAME FRAME-----------------
        endGamePanel = new JPanel();
        endGamePanel.setLayout(null);
        endGamePanel.setSize(1280, 720);

        //* Label of result */
        lResult = new JLabel();
        lResult.setFont(new Font(lResult.getFont().getName(), Font.PLAIN, 64));
        lResult.setBounds(300, 100, 800, 100);
        endGamePanel.add(lResult);

        lSaboteurPoints = new JLabel("0");
        lSaboteurPoints.setFont(new Font(lSaboteurPoints.getFont().getName(), Font.PLAIN, 32));
        lSaboteurPoints.setBounds(500, 200, 400, 50);
        endGamePanel.add(lSaboteurPoints);

        lRepairmenPoints = new JLabel("0");
        lRepairmenPoints.setFont(new Font(lRepairmenPoints.getFont().getName(), Font.PLAIN, 32));
        lRepairmenPoints.setBounds(500, 250, 400, 50);
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
        ArrayList<Repairman> rs = Game.getInstance().getRepairmanGroup();
        ElementButton.holdingPipes.clear();
        for(Repairman r : rs){
            if(r.getHoldingPipe()!=null){
                if(!ElementButton.holdingPipes.contains(r.getHoldingPipe())) {
                    ElementButton.holdingPipes.add(r.getHoldingPipe());
                }
            }
        }
        for(ElementButton e : elementButtons){
           e.update();
        }
        /** Kiírjuk a GUI-ra, hogy melyik játékos van soorn (...-s next) */
        try {
            this.lWhosTurn.setForeground(Color.BLACK);
            setTurn(Game.getInstance().getCurrentCharacter().getName());
        } catch (Exception e) {
            System.out.println("Couldn't display current player name!");
        }
        
        //Update Scoreboard
        
        //Update repairmen Points
        lgRepairmenPoints.setText(String.valueOf(Game.getInstance().getRepairmanPoints()));
        //Update saboteur Points
        lgSaboteurPoints.setText(String.valueOf(Game.getInstance().getSaboteurPoints()));
        try {
            //TODO: Update turn label
            lTurnPoints.setText(String.valueOf(Game.getInstance().getTurns()));
        } catch (Exception e) {
            System.out.println("Couldn't display current Turn #");
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
                if(i%2==0){
                    Repairman c;
                    Element e1 = Game.getInstance().getGameElements().get(3);
                    c = new Repairman(e1, null, null);
                    if(i==0) c = new Repairman(e1, null, new Pump());
                    if(i==2) c = new Repairman(e1, pi6, null);
                    c.setName("Repairman"+repairmanNum++);
                    e1.addStandingOn(c);
                    Game.getInstance().addRepairman((Repairman)c);
                }else{
                    Saboteur c;
                    Element e2 = Game.getInstance().getGameElements().get(4);
                    c = new Saboteur(e2);
                    c.setName("Saboteur"+saboteurNum++);
                    e2.addStandingOn(c);
                    Game.getInstance().addSaboteur((Saboteur)c);
                }
            }
            Game.getInstance().setCurrentCharacter(Game.getInstance().getSaboteurGroup().get(0));
            //CurrentCharacter inicializálása, hogy helyesen jelenjen meg kezdéskor
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
            //* Determine the points and the result */
            int saboteurPoints = Game.getInstance().getSaboteurPoints();
            int repairmanPoints = Game.getInstance().getRepairmanPoints();
            String result;
            if(saboteurPoints>repairmanPoints) result = "Saboteur Team Won!";
    		else if(repairmanPoints>saboteurPoints) result = "Repairman Team Won!";
            else result = "It's a Draw!";

            /** Set the label's content */
            lResult.setText(result);
            lResult.setBounds(640 - lResult.getPreferredSize().width/2, 100, lResult.getPreferredSize().width, lResult.getPreferredSize().height);
            lSaboteurPoints.setText("Saboteurs' points: " + String.valueOf(saboteurPoints));
            lSaboteurPoints.setBounds(640 - lSaboteurPoints.getPreferredSize().width/2, 200, lSaboteurPoints.getPreferredSize().width, lSaboteurPoints.getPreferredSize().height);
            lRepairmenPoints.setText("Repairmen's points: " + String.valueOf(repairmanPoints));
            lRepairmenPoints.setBounds(640 - lRepairmenPoints.getPreferredSize().width/2, 250, lRepairmenPoints.getPreferredSize().width, lRepairmenPoints.getPreferredSize().height);
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
        turn=name;
        this.lWhosTurn.setText(turn+"-s Turn");
    }

    //Vonal rajzolása két gomb között, a megadott színnel
    public void drawLineBetweenButtons(ElementButton b1, ElementButton b2, Color color, Graphics g, boolean loop) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2)); //2px Vastag
        g2.setColor(color);
        if(loop){
            g2.drawLine(b1.getBounds().x + b1.getWidth()/2 + 10, b1.getBounds().y + b1.getHeight()/2 + 10, b2.getBounds().x + b2.getWidth()/2 + 10, b2.getBounds().y + b2.getHeight()/2 + 10);
            g2.drawLine(b1.getBounds().x + b1.getWidth()/2 - 10, b1.getBounds().y + b1.getHeight()/2 - 10, b2.getBounds().x + b2.getWidth()/2 - 10, b2.getBounds().y + b2.getHeight()/2 - 10);
        }else{
            g2.drawLine(b1.getBounds().x + b1.getWidth()/2, b1.getBounds().y + b1.getHeight()/2, b2.getBounds().x + b2.getWidth()/2, b2.getBounds().y + b2.getHeight()/2);
        }
    }

    public void showOutOfActionWarning(){
        this.log.setText(this.log.getText()+System.lineSeparator()+this.turn+" is out of action!");
    }

    
}
