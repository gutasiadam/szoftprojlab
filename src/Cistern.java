import java.util.List;

public class Cistern extends NonPipe {
    private int waterFlown;

    @Override
    public void step() {
        for (Pipe p : neighbors) {
            p.step();
            if(p.waterExtraction()) waterFlown++;
            List<NonPipe> pipeNeighbors = p.getNeighbors();
            for(NonPipe np : pipeNeighbors){
                np.step();
            }
        }
    }

    public int measureAndResetWaterFlown(){
        int wf = waterFlown;
        waterFlown = 0;
        return wf;
    }

    public Pipe newPipe(){
        Pipe createdPipe = new Pipe();
        createdPipe.addNeighbor(this);
        return createdPipe; //TODO: kell returnolni?
    }

    @Override
    public Pump givePump(){
        return new Pump();
    }
}
