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

        update();

        addActionListener(e -> update()); // TODO: itt fog megjelenni az actionbutton
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
}
