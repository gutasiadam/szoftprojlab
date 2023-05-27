import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class ActionButton extends JButton{
    private ArrayList<Integer> actions = new ArrayList<Integer>();
    private Element element;

    ActionButton(Element _element)
    {
        element=_element; 
        /** Ezen keresztul tudjuk meghivni a Control fuggvenyeit.*/
        Control instance = Control.getInstance();
        
        addActionListener(
        new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(e.getActionCommand()) {
                case "Move": 
                	/** Atadja a kivalasztott Elementet amire lepni szeretne.*/
                	//instance.Move(_element);
                	break;
                	/** Valasztott Elementen lyukasztas.*/
                case "Stab": 
                	instance.Stab();
                	break;
                	/** Valasztott Elementen pumpa elhelyezes.*/
                case "PlacePump": 
                	instance.PlacePump();
                	break;
                	/** Valasztott Elementen cso elhelyezes.*/
                case "PickupPipe": 
                	instance.PlacePipe();
                	break;
                	/** Valasztott cso vegenek felvetele.*/
                case "PickUpPipe": 
                	//instance.PickUpPipe(_element);
                	break;
                	/** Valasztott Element javitasa.*/
                case "Repair": 
                	instance.Repair();
                	break;
                	/** Valasztott Element ragadossa tetele.*/
                case "Stick": 
                	instance.Stick();
                	break;
                	/** Valasztott Element csuszossa tetele.*/
                case "Slime": 
                	instance.Slime();
                	break;
                	/** Pumpa ki es bemenetenek allitasa.*/
                case "Adjust": 
                	//ez meg kerdeses
                	break;
                	/** Adott karakter korenek vege.*/
                case "EndMove": 
                	instance.EndMove();
                	break;

                }

            }
        });
    }
}
