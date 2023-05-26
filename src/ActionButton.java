import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class ActionButton extends JButton{
    private ArrayList<Integer> actions = new ArrayList<Integer>();

    ActionButton(ArrayList<Integer> actions)
    {
        this.actions=actions;
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
