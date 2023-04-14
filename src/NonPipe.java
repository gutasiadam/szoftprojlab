import java.util.List;

public abstract class NonPipe extends Element {
    protected List<Pipe> neighbors;

    public void addNeighbor(Pipe n) {
        neighbors.add(n);
    }

    public void removeNeighbor(Pipe n) {
        neighbors.remove(n);
    }
    
    @Override
    public Pipe lift(int dir) {
        Pipe n = neighbors.get(dir);
        if( n != null){
            removeNeighbor(n);
            n.removeNeighbor(this);
            return n;
        }
        return null;
    }

    @Override
    public boolean placePipe(Pipe holdingPipe) {
        if(holdingPipe != null){
            addNeighbor(holdingPipe);
            holdingPipe.addNeighbor(this);
            return true;
        }
        return false;
    }
}
