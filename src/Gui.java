import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private JFrame menuFrame; // Menu Frame
    private JFrame endGameFrame; // End Game Frame
    private JFrame activeFrame; // A jelenelg aktív keret. Ez lehet a menü, a játék vagy a vége.
    private JLabel turn; //Soronlevő játékos neve
    private JLabel lSaboteurPoints;
    private JLabel lRepairmenPoints;
    private JButton endturn;
    private JTextField log;
    ArrayList<ActionButton> actionButtons;
    ArrayList<ElementButton> elementButtons;

    public Gui(){
        // ---------------MENU FRAME-----------------
        menuFrame = new JFrame("codeX-Menü");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1280, 720);
        menuFrame.setLayout(null);

       
        JLabel lRoundSettings = new JLabel("Körök száma: ");
        lRoundSettings.setBounds(600, 100, 0, 0);
        menuFrame.add(lRoundSettings);
        JLabel lPlayerCount = new JLabel("Játékosok száma: ");
        lPlayerCount.setBounds(600, 200, 0, 0);
        menuFrame.add(lPlayerCount);

       
        NumberFormatter formatter = new NumberFormatter();
        formatter.setMinimum(1);
        formatter.setMaximum(100);
        JSpinner sRoundSettings = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        sRoundSettings.setBounds(700, 100, 0, 0);
        menuFrame.add(sRoundSettings);
        JSpinner sPlayerCount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        sPlayerCount.setBounds(700, 200, 0, 0);
        menuFrame.add(sPlayerCount);

        JButton bStart = new JButton("Start");
        bStart.setBounds(1080, 200, 700, 100); //TODO: Pontos érték megadása ide
        menuFrame.add(bStart);

        //TODO: Scoreboard hozzáadása

        //Menü beállítása aktív keretnek
        activeFrame = menuFrame;

        //---------------MAIN GAME FRAME-----------------

        frame = new JFrame("codeX-Játék");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(null);

        //Soronlevő játékos neve
        turn = new JLabel("NULL");
        turn.setBounds(40, 10, 0, 0);
        turn.add(frame);

        endturn = new JButton("End turn");
        frame.add(endturn);

        //Initialize log textfield
        log = new JTextField("Log");
        log.setEditable(false);
        log.setBounds(700, 100, 500, 500); // TODO: Pontos érték megadása ide
        frame.add(log);


        // ---------------END GAME FRAME-----------------
        endGameFrame = new JFrame("codeX-Vége a játéknak");
        endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endGameFrame.setSize(1280, 720);
        endGameFrame.setLayout(null);

        lSaboteurPoints = new JLabel("0");
        lRoundSettings.setBounds(600, 100, 0, 0);
        menuFrame.add(lSaboteurPoints);
        lRepairmenPoints = new JLabel("0");
        lPlayerCount.setBounds(600, 200, 0, 0);
        menuFrame.add(lRepairmenPoints);

        //------------------------------------------------
        //Aktív keret megjelenítése
        activeFrame.setVisible(true);
    }

    public void addToView(JButton b){
        frame.add(b); //Csak a frame-hez lehet hozzáadni gombot.
    }

    public void removeFromView(JButton b){
        frame.remove(b); // Csak a frame-től lehet eltávolítani gombot.
    }

    public void updateFrame(){

        //Redraw frame
        activeFrame.repaint();
    }

    /**
     * A következő keretre lépteti a játékot.
     * menü -> játék -> vége
     */
    public void nextFrame(){
        
        activeFrame.setVisible(false);
        if(activeFrame == menuFrame){
            activeFrame = frame;
        }
        else{
            activeFrame=endGameFrame;
        }
        activeFrame.setVisible(true);
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
