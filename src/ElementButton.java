import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

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
                    throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
                }
        });
    }

    String getImageName(){
        String className = element.getClass().getName();
        switch(className){
            case "Cistern": return "bucket.png";
            case "WaterSource": return "fountain.png";
            case "Pump": return "";
            case "Pipe": return "";
        }
        return "";
    }
}
