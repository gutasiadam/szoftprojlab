public class WaterSource extends NonPipe {

    @Override
    public void step() {
        for(Pipe p : neighbors){
            p.giveWater();
        }
    }

    @Override
    public boolean placePipe(Pipe holdingPipe){
        NonPipe n = holdingPipe.getNeighbors().get(0);
        if(n != this){
            addNeighbor(holdingPipe);
            holdingPipe.addNeighbor(this);
            return true;
        }else{
            return false;
        }
    }
}
