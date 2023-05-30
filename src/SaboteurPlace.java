/**
 * Interfész, ami a szabotőrök által látható metódusokat tartalmazza a Place
 * osztályból.
 */
public interface SaboteurPlace extends Place {
    public void damage(); /** Megrongalja az ot megvalosito elemet. */
    public void slime(); /** Csuszossa tesz egy csovet. */
}
