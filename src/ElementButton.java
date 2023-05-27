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
