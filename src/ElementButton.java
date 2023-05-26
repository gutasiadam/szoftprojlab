import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ElementButton extends JButton{
    private ArrayList<ImageIcon> statusimages = new ArrayList<ImageIcon>();
    private Element element;

    ElementButton(Element element)
    {
        this.element=element;
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
}
