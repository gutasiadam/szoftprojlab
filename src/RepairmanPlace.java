public interface RepairmanPlace extends Place {
    public void repair(); /** Megjavitja a palyaelemet. */
    public Pipe placePump(Pump p); /** Elhelyezi a pumpat a csovon kettevagva azt. */
    public Pipe lift(int dir); /** Adott azonositoju cso lecsatlakoztatasa, es felemelese. */
    public Pump givePump(); /** Letrehoz egy uj pumpat, es odaadja a jatekosnak. */
    public boolean placePipe(Pipe p); /** Bekoti a paramterul kapott csovet az interfeszt megvalosito elembe. */
}
