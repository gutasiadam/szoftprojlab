public class Control {

    //Az egyetlen Control objektum
    private static Control instance;

    private Repairman r;
    private Saboteur s;

    /**
     * Privát konstruktor, mivel Singleton
     */
    private Control(){}

    /**
     * Visszaadja a Singleton Control objektumot
     * @return Singleton Control objektum
     */
    public static Control getInstance(){
        if (instance==null){
            instance=new Control();
        }
        return instance;
    }

    /**
     * Ezen a függvényen keresztül tudjuk átmozgatni a játékost adott irányba.
     * @param dir Hanyadik szomszédjára lépjen át az Element-ről amin áll.
     */
    public void Move(int dir){
        Game.getInstance().getCurrentCharacter().move(dir);
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk kilyukasztani az Element-et amin állunk
     */
    public void Stab(){
        Game.getInstance().getCurrentCharacter().dealDamage();
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk lehelyezni az aktuális játékosnál lévű Pumpát
     */
    public void PlacePump(){
        //Elvileg ha biztosítjuk, hogy ezt csak a szerelők hívhatják meg akkor nem gyilkolnak meg a castolásért (meg az nyilván a többi fv-re is igaz alatta)
        //Ha viszont nem így akarjátok akkor minden fv kell a character osztályba, hogy meg lehessen azokat hívni
        r=(Repairman)Game.getInstance().getCurrentCharacter(); 
        r.PlacePump();
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk lehelyezni az aktuális játékosnál lévő Csövet
     */
    public void PlacePipe(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.PlacePipe();
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudunk Pumpát felvenni az aktuális játékossal.
     */
    public void PickupPump(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.LiftPump();
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudunk adott csövet felvenni az aktuális játékossal
     * @param dir Hanyadik szomszédját akarjuk felemelni az Element-nek amin áll.
     */
    public void PickUpPipe(int dir){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.LiftPipe(dir);
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk megjavítani az Element-et amin áll az aktuális játékos
     */
    public void Repair(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.RepairElement();
    }

    /**
     * Ezen a függvényen keresztül tudjuk ragadóssá tenni az Element-et áll az aktuális játékos
     */
    public void Stick(){
        s=(Saboteur)Game.getInstance().getCurrentCharacter();
        s.makeSticky();
        updateFrame();
    }

    /** 
    * Ezen a függvényen keresztül tudjuk csúszóssá tenni az Element-et amin áll az aktuális játékos
    */
    public void Slime(){
        s=(Saboteur)Game.getInstance().getCurrentCharacter();
        s.putSlime();
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk beállítani a Pumpa be és kimenetét az aktuális játékossal
     * @param src Bemenet száma (hanyadik szomszédja)
     * @param dest Kimenet száma (hanyadik szomszédja)
     */
    public void Adjust(int src, int dest){
        Game.getInstance().getCurrentCharacter().adjustPump(src, dest);
        updateFrame();
    }

    /**
     * Ezen a függvényen keresztül tudjuk befejezni az adott játékos körét, a következő játékos léphet.
     * Felébreszti a Game-et futtató szálat.
     */
    public void EndMove(){
        Game.getInstance().getCurrentCharacter().WakeUp();
    }

    /**
     * Frissiti a kepernyot
     * Hivhato nem UI szalrol is
     */
    public void updateFrame()
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Gui.getInstance().updateFrame();
			}
        });
    }

}
