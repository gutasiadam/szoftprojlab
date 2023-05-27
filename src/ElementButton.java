import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
            case "Pump": return "";
            case "Pipe": return "";
        }
        return "";
    }
    
    private void showActionButtonWindow() {
        // Letrehozunk egy JDialog objektumot és beallitjuk a tulajdonságait
        JDialog dialog = new JDialog();
        dialog.setTitle("Valassz a muveletek kozul");
        dialog.setModal(true); // Beallitjuk modalis ablaknak, amig bezarodik, a fiablak nem erheto el
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Letrehozunk egy JPanel objektumot az ActionButton-ok tarolasara
        JPanel buttonPanel = new JPanel();

        // ActionButton-ok letrehozasa es hozzaadasa a panelhoz
        ActionButton actionButton1 = new ActionButton(element);
        actionButton1.setActionCommand("Repair");
        actionButton1.setText("Repair");
        buttonPanel.add(actionButton1);

        ActionButton actionButton2 = new ActionButton(element);
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
}
