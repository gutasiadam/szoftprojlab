import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ElementButton extends JButton{
    private ArrayList<ImageIcon> statusimages = new ArrayList<ImageIcon>();
    private Element element;

    ElementButton(Element element)
    {
        this.element=element;

        try {
            Image img = ImageIO.read(getClass().getResource("img/" + getImageName()));
            Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
            setIcon(new ImageIcon(newimg));
        } catch (Exception ex) {
            System.out.println(ex);
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
    
    private void showActionButtonWindow() {
        // Letrehozunk egy JDialog objektumot �s beallitjuk a tulajdons�gait
        JDialog dialog = new JDialog();
        dialog.setTitle("Valassz a muveletek kozul");
        dialog.setModal(true); // Beallitjuk modalis ablaknak, amig bezarodik, a fiablak nem erheto el
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Letrehozunk egy JPanel objektumot az ActionButton-ok tarolasara
        JPanel buttonPanel = new JPanel();

        // ActionButton-ok letrehozasa es hozzaadasa a panelhoz
        ActionButton actionButton1 = new ActionButton(null);
        actionButton1.setActionCommand("Repair");
        actionButton1.setText("Repair");
        buttonPanel.add(actionButton1);

        ActionButton actionButton2 = new ActionButton(null);
        actionButton2.setActionCommand("Stab");
        actionButton2.setText("Stab");
        buttonPanel.add(actionButton2);
        
        // Panel hozzaadasa a dialogushoz
        dialog.add(buttonPanel);

        // A dialogus meretenek beallitasa a tartalomhoz
        dialog.pack();

        // A dialogus megjelenitese
        dialog.setVisible(true);
    }

    public void update(){
        // Characterek az adott elemen
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

        // Állapotok ha pumpa
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
            // Max csövek száma
            JLabel b = new JLabel("Max: " + e.getCapacity());
            b.setBounds(45, 70, 50, 20);
            add(b);
        }

        // Állapotok ha cső
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
            if(e.getSticky() > 0){
                JLabel b = new JLabel();
                try {
                    Image img = ImageIO.read(getClass().getResource("img/sticky.png"));
                    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                    b.setIcon(new ImageIcon(newimg));
                    b.setBounds(0, 40, 20, 20);
                    add(b);
                    JLabel n = new JLabel(String.valueOf(e.getSticky()));
                    n.setBounds(20, 40, 20, 20);
                    add(n);
                } catch (Exception ex) {System.out.println(ex);}
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
