import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
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

       JSpinner sPlayerCount = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
       sPlayerCount.setBounds(700, 200, 40, 30);
       menuPanel.add(sPlayerCount);

       JButton bStart = new JButton("Start");
       bStart.setBounds(1080, 200, 100, 30);
       menuPanel.add(bStart);

        //TODO: Scoreboard hozzáadása
        
        

        //---------------MAIN GAME PANEL-----------------
        gamePanel = new JPanel();
        gamePanel.setSize(1280, 720);
        //Soronlevő játékos neve
        turn = new JLabel("NULL");
        turn.setBounds(40, 10, 0, 0);
        gamePanel.add(turn);

        endturn = new JButton("End turn");
        gamePanel.add(endturn);

        //Initialize log textfield
        log = new JTextField("Log");
        log.setEditable(false);
        log.setBounds(700, 100, 500, 500); // TODO: Pontos érték megadása ide
        gamePanel.add(log);


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
    public void drawLineBetweenButtons(ElementButton b1, ElementButton b2, Color color) {
        Graphics g = frame.getGraphics();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2)); //2px Vastag
        g2.setColor(color);
        Line2D lin = new Line2D.Float(b1.getAlignmentX(), b1.getAlignmentY(), b2.getAlignmentX(), b2.getAlignmentY());
        g2.draw(lin);
    }

    
}
