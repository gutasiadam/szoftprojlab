import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class ActionButton extends JButton{
    private ArrayList<Integer> actions;
    //private Element element;

    ActionButton(ArrayList<Integer> acts/*Element _element*/)
    {
		actions = acts;

		if(acts==null)
			actions = new ArrayList<Integer>();

		//Hozzaad 2 db parametert ami nem lesz hasznalva, csak ha nem kap eleg parametert a konstuktor
		actions.add(0);
		actions.add(0);

        //element=_element; 
        /** Ezen keresztul tudjuk meghivni a Control fuggvenyeit.*/
        Control instance = Control.getInstance();
        
        addActionListener(
        new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
				Game.getInstance().getCurrentCharacter().decreaseRemainingSteps();
                switch(e.getActionCommand()) {
                case "Move": 
                	/** Atadja a kivalasztott Elementet amire lepni szeretne.*/
                	instance.Move(actions.get(0));
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
                case "PlacePipe": 
                	instance.PlacePipe();
                	break;
					/** Valasztott cso vegenek felvetele.*/
				case "PickupPump": 
                	instance.PickupPump();
                	break;
                	/** Valasztott cso vegenek felvetele.*/
                case "PickUpPipe": 
                	instance.PickUpPipe(actions.get(0));
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
					instance.Adjust(actions.get(0), actions.get(1));
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
