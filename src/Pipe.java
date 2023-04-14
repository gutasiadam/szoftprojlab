import java.util.List;

public class Pipe extends Element implements SaboteurPointSource{
    private boolean holeOnPipe;
    private int leakedWaterAmount;
    private List<NonPipe> neighbors;
    
    @Override
    public boolean accept(Character c){
        if(standingOn.size()<1){
            standingOn.add(c);
            return true;
        }
        return false;
    }
    @Override
    public void step() {
        if(holeOnPipe){
            containingWater = false;
            leakedWaterAmount++;
        }
    }
    @Override
    public void repair(){
        boolean userInput = false; //TODO: stdinrol
        if(userInput){
            holeOnPipe = false;
        }
    }
    @Override
    public void damage(){
        boolean userInput = false; //TODO: stdinrol
        if(userInput) holeOnPipe = true;
    }
    @Override
    public Pipe placePump(Pump holdingPump) {
        NonPipe n = (NonPipe)getNeighbors().get(0);
        if(n != null){
            removeNeighbor(n);
            n.removeNeighbor(this);
            holdingPump.addNeighbor(this);
            addNeighbor(holdingPump);
            Pipe p = new Pipe();
            p.addNeighbor(n);
            n.addNeighbor(p);
            holdingPump.addNeighbor(p);
            p.addNeighbor(holdingPump);
            return p;
        }else{
            return null;
        }
    }
    @Override
    public Pipe lift(int dir){
        NonPipe n1 = neighbors.get(0);
        NonPipe n2 = neighbors.get(1);
        if(n1 == null || n2 == null){
            return this;
        }
        return null;
    }
    public void addNeighbor(NonPipe n){
        if(neighbors.size()<2){
            neighbors.add(n);
        }
    }
    public void removeNeighbor(NonPipe n) {
        neighbors.remove(n);
    }
    @Override
    public int measureAndResetLeakedWaterAmount(){
        int lkdwtramt = leakedWaterAmount;
        leakedWaterAmount = 0;
        return lkdwtramt;
    }
    public boolean waterExtraction(){
        if(containingWater){
            containingWater = false;
            return true;
        }
        return false;
    }
    public boolean giveWater(){
        if(containingWater) return false;
        containingWater = true;
        return true;
    }
    public List<NonPipe> getNeighbors(){
        return neighbors;
    }
}
