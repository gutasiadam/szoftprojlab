public interface Place {
    public void adjust(int src, int dest);
    public boolean accept(Character c);
    public void remove(Character c);
    //public Element[] getNeighbors(); //vegyük ki mert a return type problematikus
}
