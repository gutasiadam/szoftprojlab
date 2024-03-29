import java.util.List;

/**
 * Olyan helyeket reprezental, ahova a karakterek lephetnek.
 */
public interface Place {
    public void adjust(int src, int dest); /** Ezen keresztul lehet beallitani a pumpalasi iranyt. */
    public boolean accept(Character c); /** Ot kell meghivni ha ra szeretnek lepni a helyre. */
    public boolean remove(Character c); /** Ot kell meghivni ha le szeretnenk lepni a mezorol. */
    public String getName(); /**Teszteleshez nev kiiratasa */
    public List<? extends Element> getNeighbors();/**Szomszedok elerese. @author Bodnar Mark */
    public void stick();/** Ragadossa teszi az adott poziciot. */
}
