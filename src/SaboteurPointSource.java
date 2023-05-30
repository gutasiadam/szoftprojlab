/**
 * A szabotőrök számára pontszámot generáló objektumok interfésze.
 */
public interface SaboteurPointSource {
    public int measureAndResetLeakedWaterAmount(); /** Visszadja a kifolyott viz mennyiseget, majd nullara allitja */

	public String getName();
}
