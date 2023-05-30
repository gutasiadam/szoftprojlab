import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ElementButton extends JButton{
    private ArrayList<ImageIcon> statusimages = new ArrayList<ImageIcon>();
    private Element element;
    public static ArrayList<Pipe> holdingPipes = new ArrayList<Pipe>();

    ElementButton(Element element)
    {
        this.element=element;
        if(getImageName()!=""){
            try {
                Image img = ImageIO.read(getClass().getResource("img/" + getImageName()));
                Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(newimg));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }


        addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                	showActionButtonWindow();
                	//throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
                }
        });
    }

    String getImageName(){
        String className = element.getClass().getName();
        switch(className){
            case "Cistern": return "bucket.png";
            case "WaterSource": return "fountain.png";
            case "Pump": return "pump.png";
            case "Pipe": return "";
        }
        return "";
    }

    /**
     * Visszaadja az Elementet amin a CurrentCharacter all
     * @return Element, amin a CurrentCharacter talalhato
     */
    private Element getCurrentCharacterPlace()
    {
        Character currenCharacter = Game.getInstance().getCurrentCharacter();
        for(Element element : Game.getInstance().getGameElements())
        {
            for(Character c : element.getStandingOn())
            {
                if(c==currenCharacter)
                {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * Megtalalhato-e a tartalmazott element-e a parameter szomszedjai kozott
     * @param e Element, aminek a szomszedjai kozott keresunk
     * @return True, ha szomszedok, false ha nem
     */
    private boolean findElementInNeighbors(Element e)
    {
        boolean out = false;
        for(Element neighbor : e.getNeighbors())
        {
            if(neighbor==element)
                out = true;
        }
        return out;
    }

    /**
     * Visszaadja, hogy a parameterul kapott element hanyadik szomszedja a tartalmazott.
     * @param e Element, aminek a szomszedai kozottkeressuk a tartalmazott element-et
     * @param searchInParam a parameter szomszedjai kozott keressunk-e
     * @return index, ha megtalaltuk, -1 ha nem
     */
    private int getElementIndexInNeighbors(Element e, boolean searchInParam)
    {
        int index = 0;
        if(searchInParam)
        {
            for(Element neighbor : e.getNeighbors())
            {
                if(neighbor==element)
                return index;
                index++;
            } 
        }
        else
        {
            for(Element neighbor : element.getNeighbors())
            {
                if(neighbor==e)
                return index;
                index++;
            } 
        }
        
        return -1;
    }
    
    private void showActionButtonWindow() {
    	/** Letrehozunk egy JDialog objektumot es beallitjuk a tulajdonsagait.*/
    	/** */
        JDialog dialog = new JDialog();
        dialog.setTitle("Valassz a muveletek kozul");
        dialog.setModal(true);/** Beallitjuk modalis ablaknak, amig bezarodik, a foablak nem erheto el*/ 
        dialog.setLocation(400, 200);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        /** Csak azokat a muveleteket jelenitjuk meg amit az adott karakterrel lehet vegezni.*/ 
        boolean isRepairman = Game.getInstance().getCurrentCharacter().getClass().getName().equals("Repairman");

        /** Letrehozunk egy JPanel objektumot az ActionButton-ok tarolasara*/
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional: Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0); // 10px gap between buttons


        ActionListener closeButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        };
        
        /** ActionButton-ok letrehozasa es hozzaadasa a panelhoz*/
        /** Igy beszeltuk meg,de ugye a harom muveleten kivul egyebkent lehetne optimalizalni, hogy ne legyen ilyen brute force*/
        Element place = getCurrentCharacterPlace();
        if(findElementInNeighbors(place))
        {
            if(element.canPerformAction("Move")) 
            {
                ArrayList<Integer> params = new ArrayList<Integer>();
                params.add(getElementIndexInNeighbors(place,true));
                ActionButton moveButton = new ActionButton(params);
                moveButton.setActionCommand("Move");
                moveButton.setText("Move");
                moveButton.addActionListener(closeButtonListener);
                buttonPanel.add(moveButton, gbc);
                gbc.gridy++;
            }
        }
        
        
        if(element.canPerformAction("Stab")&&place==element) {
            ActionButton stabButton = new ActionButton(null);
            stabButton.setActionCommand("Stab");
            stabButton.setText("Stab");
            stabButton.addActionListener(closeButtonListener);
            buttonPanel.add(stabButton, gbc);
            gbc.gridy++;
        }
        
        if(isRepairman && element.canPerformAction("PlacePump")&&place==element) {
            ActionButton placePumpButton = new ActionButton(null);
            placePumpButton.setActionCommand("PlacePump");
            placePumpButton.setText("PlacePump");
            placePumpButton.addActionListener(closeButtonListener);
            buttonPanel.add(placePumpButton, gbc);
            gbc.gridy++;
        }
        
        if(isRepairman && element.canPerformAction("PlacePipe")&&place==element) {
            ActionButton placePipeButton = new ActionButton(null);
            placePipeButton.setActionCommand("PlacePipe");
            placePipeButton.setText("PlacePipe");
            placePipeButton.addActionListener(closeButtonListener);
            buttonPanel.add(placePipeButton, gbc);
            gbc.gridy++;
        }
        
        if(isRepairman && element.canPerformAction("PickupPump")&&place==element) {
            ActionButton pickupPumpButton = new ActionButton(null);
            pickupPumpButton.setActionCommand("PickupPump");
            pickupPumpButton.setText("PickupPump");
            pickupPumpButton.addActionListener(closeButtonListener);
            buttonPanel.add(pickupPumpButton, gbc);
            gbc.gridy++;
        }
        
        if(isRepairman && element.canPerformAction("PickUpPipe")&&place==element) {
            for(Element e : element.getNeighbors())
            {
                ArrayList<Integer> params = new ArrayList<Integer>();
                params.add(getElementIndexInNeighbors(e, false));
                ActionButton pickUpPipe = new ActionButton(params);
                pickUpPipe.setActionCommand("PickUpPipe");
                pickUpPipe.setText("PickUpPipe "+e.getName());
                pickUpPipe.addActionListener(closeButtonListener);
                buttonPanel.add(pickUpPipe, gbc);
                gbc.gridy++;
            }
        }
        
        if(isRepairman && element.canPerformAction("Repair")&&place==element && element.getClass().getName().equals("Pump") && ((Pump)element).getBroken()) {
            ActionButton repairButton = new ActionButton(null);
            repairButton.setActionCommand("Repair");
            repairButton.setText("Repair");
            repairButton.addActionListener(closeButtonListener);
            buttonPanel.add(repairButton, gbc);
            gbc.gridy++;
        }
        
        if(element.canPerformAction("Stick")&&place==element) {
            ActionButton stickButton = new ActionButton(null);
            stickButton.setActionCommand("Stick");
            stickButton.setText("Stick");
            stickButton.addActionListener(closeButtonListener);
            buttonPanel.add(stickButton, gbc);
            gbc.gridy++;
        }
        
        if(!isRepairman && element.canPerformAction("Slime")&&place==element) {
            ActionButton slimeButton = new ActionButton(null);
            slimeButton.setActionCommand("Slime");
            slimeButton.setText("Slime");
            slimeButton.addActionListener(closeButtonListener);
            buttonPanel.add(slimeButton, gbc);
            gbc.gridy++;
        }
        
        if(element.canPerformAction("Adjust")&&place==element) {
            for(Element e:element.getNeighbors())
            {
                ArrayList<Integer> params1 = new ArrayList<Integer>();
                Pump p = (Pump) element;
                params1.add(getElementIndexInNeighbors(e,false));
                params1.add(getElementIndexInNeighbors(p.getDest(),false));
                ActionButton adjustButton1 = new ActionButton(params1);
                adjustButton1.setActionCommand("Adjust");
                adjustButton1.setText("Adjust Input to "+e.getName());
                adjustButton1.addActionListener(closeButtonListener);
                buttonPanel.add(adjustButton1, gbc);
                gbc.gridy++;

                ArrayList<Integer> params2 = new ArrayList<Integer>();
                params2.add(getElementIndexInNeighbors(p.getSrc(),false));
                params2.add(getElementIndexInNeighbors(e,false));
                ActionButton adjustButton2 = new ActionButton(params2);
                adjustButton2.setActionCommand("Adjust");
                adjustButton2.setText("Adjust Output to "+e.getName());
                adjustButton2.addActionListener(closeButtonListener);
                buttonPanel.add(adjustButton2, gbc);
                gbc.gridy++;
            }
        }
        
        ActionButton endMoveButton = new ActionButton(null);
        endMoveButton.setActionCommand("EndMove");
        endMoveButton.setText("EndMove");
        endMoveButton.addActionListener(closeButtonListener);
        buttonPanel.add(endMoveButton, gbc);  
        
        
        /**Panel hozzaadasa a dialogushoz */
        dialog.add(buttonPanel);

        /** A dialogus meretenek beallitasa a tartalomhoz*/
        dialog.pack();

        /** A dialogus megjelenitese */
        dialog.setVisible(true);
    }

    public void update(){
        //* Törlés */
        removeAll();
        //* Characterek az adott elemen */
        setLayout(null);
        List<Character> cs = element.getStandingOn();
        int offset = 0;
        for(Character c : cs){
            JLabel b = new JLabel();
            String imgname;
            if(c.getClass().getName().equals("Repairman")){
                imgname = "man-mechanic.png";
                if(((Repairman)c).hasHoldingPump()){
                    imgname = "man-with-pump.png";
                }
                for(int i = 0; i < 4 && i < holdingPipes.size(); i++){
                    if(((Repairman)c).getHoldingPipe()!=null && ((Repairman)c).getHoldingPipe().equals(holdingPipes.get(i))){
                        switch(i){
                            case 0: imgname = "man-with-blue-pipe.png"; break;
                            case 1: imgname = "man-with-red-pipe.png"; break;
                            case 2: imgname = "man-with-green-pipe.png"; break;
                            case 3: imgname = "man-with-yellow-pipe.png"; break;
                        }
                    }
                }
            }else{
                imgname = "man-wearing-turban.png";
            }
            try {
                Image img = ImageIO.read(getClass().getResource("img/" + imgname));
                Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                b.setIcon(new ImageIcon(newimg));
                if(element.getClass().getName().equals("Pipe")){
                    b.setBounds(20, 20, 20, 20);
                }else{
                    b.setBounds(offset, 0, 20, 20);
                }
                offset += 20;
                add(b);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        // Ă�llapotok ha pumpa
        if(element.getClass().getName().equals("Pump")){
            Pump e = (Pump)element;
            if(e.getBroken()){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/high-voltage.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(0, 70, 20, 20);
                    add(b);
                } catch (Exception ex) {System.out.println(ex);}
            }
            if(e.getContainingWater()){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/droplets.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(20, 70, 20, 20);
                    add(b);
                } catch (Exception ex) {System.out.println(ex);}
            }
            // Max csĂ¶vek szĂˇma
            JLabel b = new JLabel("Max: " + e.getCapacity());
            b.setBounds(45, 70, 50, 20);
            add(b);
        }

        // Ă�llapotok ha csĹ‘
        if(element.getClass().getName().equals("Pipe")){
            Pipe e = (Pipe)element;
            if(e.getHoleOnPipe()){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/hole.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(40, 0, 20, 20);
                    add(b);
                } catch (Exception ex) {System.out.println(ex);}
            }
            if(e.getContainingWater()){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/droplets.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(40, 40, 20, 20);
                    add(b);
                } catch (Exception ex) {System.out.println(ex);}
            }
            if(e.getSlimey() > 0){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/wavy-dash.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(0, 0, 20, 20);
                    add(b);
                    JLabel n = new JLabel(String.valueOf(e.getSlimey()));
                    n.setBounds(20, 0, 20, 20);
                    add(n);
                } catch (Exception ex) {System.out.println(ex);}
            }
            if(e.getSticky() != 0){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/sticky.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(0, 40, 20, 20);
                    add(b);
                    JLabel n = new JLabel(String.valueOf(Math.abs(e.getSticky())));
                    n.setBounds(20, 40, 20, 20);
                    add(n);
                } catch (Exception ex) {System.out.println(ex);}
            }
            for(int i = 0; i < 4 && i < holdingPipes.size(); i++){
                String imgname = "";
                if(e.equals(holdingPipes.get(i))){
                    switch(i){
                        case 0: imgname = "blue-pipe.png"; break;
                        case 1: imgname = "red-pipe.png"; break;
                        case 2: imgname = "green-pipe.png"; break;
                        case 3: imgname = "yellow-pipe.png"; break;
                    }
                }
                JLabel b = new JLabel();
                if(imgname!=""){
                    try {
                        Image img = ImageIO.read(getClass().getResource("img/" + imgname));
                        Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                        b.setIcon(new ImageIcon(newimg));
                        b.setBounds(0, 20, 20, 20);
                        add(b);
                    } catch (Exception ex) {System.out.println(ex);}
                }
            }
        }
    }

    public ArrayList<ElementButton> getNeighboursElementButton(ArrayList<ElementButton> eb){
        if(element.getClass().getName().equals("Pipe")){
            List<Element> es = (List<Element>) element.getNeighbors();
            ArrayList<ElementButton> toReturn = new ArrayList<ElementButton>();
            for(Element eiter : es){
                for(ElementButton ebiter : eb){
                    if(ebiter.element.equals(eiter)){
                        toReturn.add(ebiter);
                    }
                }
            }
            return toReturn;
        }else{
            return null;
        }
    }

    public void drawWaterFlowDirection(Graphics g, ArrayList<ElementButton> eb){
        if(element.getClass().getName().equals("Pump")){
            Pump p = (Pump)element;
            Element src = p.getSrc();
            Element dest = p.getDest();
            ElementButton pe = p.getElementButton(eb);
            ElementButton srce = src.getElementButton(eb);
            ElementButton deste = dest.getElementButton(eb);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.GREEN);
            g2.fillOval((pe.getBounds().x + pe.getWidth()/2) + (int)(0.5*((srce.getBounds().x + srce.getWidth()/2)-(pe.getBounds().x + pe.getWidth()/2)))-10, (pe.getBounds().y + pe.getHeight()/2) + (int)(0.5*((srce.getBounds().y + srce.getHeight()/2)-(pe.getBounds().y + pe.getHeight()/2)))-10, 20, 20);
            g2.setColor(Color.RED);
            g2.fillOval((pe.getBounds().x + pe.getWidth()/2) + (int)(0.5*((deste.getBounds().x + deste.getWidth()/2)-(pe.getBounds().x + pe.getWidth()/2)))-10, (pe.getBounds().y + pe.getHeight()/2) + (int)(0.5*((deste.getBounds().y + deste.getHeight()/2)-(pe.getBounds().y + pe.getHeight()/2)))-10, 20, 20);
        }
    }

    public Element getElement(){
        return element;
    }
}
