public interface RepairmanPlace extends Place {
    public void repair();
    public Pipe placePump(Pump p);
    public Pipe lift(int dir);
    public Pump givePump();
    public boolean placePipe(Pipe p);
}
