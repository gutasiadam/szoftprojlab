public class Control {

    private static Control instance;

    private Repairman r;
    private Saboteur s;

    private Control(){}

    public static Control getInstance(){
        if (instance==null){
            instance=new Control();
        }
        return instance;
    }

    public void Move(int dir){
        //Le kéne tudni kérdezni hogy éppen hol áll a karakter hogy onnnan lehessen removolni
    }

    public void Stab(){
        Game.getInstance().getCurrentCharacter().dealDamage();
    }

    public void PlacePump(){
        //Elvileg ha biztosítjuk, hogy ezt csak a szerelők hívhatják meg akkor nem gyilkolnak meg a castolásért (meg az nyilván a többi fv-re is igaz alatta)
        //Ha viszont nem így akarjátok akkor minden fv kell a character osztályba, hogy meg lehessen azokat hívni
        r=(Repairman)Game.getInstance().getCurrentCharacter(); 
        r.PlacePump();
    }

    public void PlacePipe(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.PlacePipe();
    }

    public void PickupPump(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.LiftPump();
    }

    public void PickUpPipe(int dir){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.LiftPipe(dir);
    }

    public void Repair(){
        r=(Repairman)Game.getInstance().getCurrentCharacter();
        r.RepairElement();
    }

    public void Stick(){
        s=(Saboteur)Game.getInstance().getCurrentCharacter();
        s.makeSticky();
    }

    public void Slime(){
        s=(Saboteur)Game.getInstance().getCurrentCharacter();
        s.putSlime();
    }

    public void Adjust(int src, int dest){
        Game.getInstance().getCurrentCharacter().adjustPump(src, dest);
    }

    public void EndMove(){
        //TODO
    }
}
