import java.util.List;

public abstract class Element implements RepairmanPlace, SaboteurPlace{
    protected boolean containingWater;
    protected List<Character> standingOn;

    public abstract void step();

    @Override
    public void adjust(int src, int dest) {
        //akiben kell, felul van definialva
    }

    @Override
    public boolean accept(Character c) {
        standingOn.add(c);
        return true;
    }

    @Override
    public void remove(Character c) {
        standingOn.remove(c);
    }

    @Override
    public void damage() {
        //intentionally nothing happens
    }

    @Override
    public void repair() {
        //akiben kell, felul van definialva
    }

    @Override
    public Pump givePump() {
        return null;
    }

    @Override
    public boolean placePipe(Pipe p) {
        return false;
    }

    @Override
    public Pipe placePump(Pump p) {
        return null;
    }
}
