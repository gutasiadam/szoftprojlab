import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A csöveket reprezentáló osztály.
 */
public class Pipe extends Element implements SaboteurPointSource {
    private boolean holeOnPipe;
    private int leakedWaterAmount;
    private int breakable;
    private int sticky;
    private int slimey;
    private List<NonPipe> neighbors;

    /**
     * @author Szikszai Levente
     *         Inicializalja a Pipe-ot
     */
    Pipe() {
        neighbors = new ArrayList<NonPipe>();
        leakedWaterAmount = 0;
        holeOnPipe = false;
        setAvailableActions(true, true, false, false, true, false, true, true, true, false);
    }

    /**
     * Beállítja a betöltés során a Pipe változóit.
     * 
     * @param hole        holeOnPipe
     * @param leakedWater leakedWaterAmount
     * @param _slimey     _slimey
     * @param _sticky     sticky
     */
    Pipe(boolean hole, boolean hasWater, int leakedWater, int _slimey, int _sticky) {
        neighbors = new ArrayList<NonPipe>();
        leakedWaterAmount = leakedWater;
        holeOnPipe = hole;
        containingWater = hasWater;
        slimey = _slimey;
        sticky = _sticky;
        setAvailableActions(true, true, false, false, true, false, true, true, true, false);
    }

    /**
     * Ot kell meghivni ha ra szeretnek lepni a csore.
     * 
     * @param c - a karakterunk
     * @return boolean - a ralepes sikeressege
     */
    @Override
    public boolean accept(Character c) {
        boolean success;
        if (standingOn.size() == 1) {
            Control.getInstance().appendToLog("Failed to move to " + this.getName());
            // System.out.println("Failed to move to " + this.getName());
            return false;
        } else {
            if (slimey > 0) {
                success = false;
                if (neighbors.size() > 1) {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        success = getNeighbors().get(0).accept(c);
                        Control.getInstance().appendToLog("Slipped to " + getNeighbors().get(0).getName());
                        // System.out.println("Slipped to " + getNeighbors().get(0).getName());
                    } else {
                        success = getNeighbors().get(1).accept(c);
                        Control.getInstance().appendToLog("Slipped to " + getNeighbors().get(1).getName());
                        // System.out.println("Slipped to " + getNeighbors().get(1).getName());
                    }
                } else {
                    success = getNeighbors().get(0).accept(c);
                    Control.getInstance().appendToLog("Slipped to " + getNeighbors().get(0).getName());
                    // System.out.println("Slipped to " + getNeighbors().get(0).getName());
                }
                return success;
            } else {
                standingOn.add(c);
                Control.getInstance().appendToLog("Successfully moved to " + this.getName());
                // System.out.println("Successfully moved to " + this.getName());
                return true;
            }
        }
    }

    /**
     * Lelepes egy csorol
     * 
     * @param c a karakter
     * @return Sikeres volt-e a lelepes
     */
    public boolean remove(Character c) {
        if (sticky > 0) {
            Control.getInstance().appendToLog("Sticked to " + this.getName() + "can't move until: " + sticky);
            // System.out.println("Sticked to " + this.getName() + "can't move until: " + sticky);
            sticky--;
            return false;
        } else {
            if (sticky < 0)
                sticky = sticky * -1;
            standingOn.remove(c);
            return true;
        }
    }

    /**
     * Ha lyukas, kifolyatja magabol a vizet, es noveli a kifolyt viz mennyiseget.
     */
    @Override
    public void step() {
        if (holeOnPipe) {
            containingWater = false;
            leakedWaterAmount++;
        }
    }

    /**
     * Megfoltozza a csovet.
     */
    @Override
    public void repair() {
        if (holeOnPipe) {
            holeOnPipe = false;
            this.breakable = (int) (Math.random() * 4) + 2;
            Control.getInstance().appendToLog("Succesfully repaired " + this.getName());
            // System.out.println("Succesfully repaired " + this.getName());
        } else {
            Control.getInstance().appendToLog("Not broken");
            // System.out.println("Not broken");
        }
    }

    /**
     * Kilyukasztja a csovet.
     */
    @Override
    public void damage() {
        if (breakable == 0) {
            if (holeOnPipe) {
                Control.getInstance().appendToLog("Failed to stab " + this.getName() + " already has a hole");
                // System.out.println("Failed to stab " + this.getName() + " already has a hole");
            } else {
                holeOnPipe = true;
                step();
                Control.getInstance().appendToLog("Successfully stabbed " + this.getName());
                // System.out.println("Successfully stabbed " + this.getName());
            }
        } else {
            Control.getInstance().appendToLog("Failed to stab " + this.getName() + " unbreakable until: " + breakable);
            // System.out.println("Failed to stab " + this.getName() + " unbreakable until: " + breakable);
        }
    }

    /**
     * Letrehoz egy uj csovet, majd koze es a meglevo cso koze lehelyezi a pumpat.
     * 
     * @param holdingPump - a lehelyezni kivant pumpa
     * @return Pipe - az ujonnan letrehozott cso
     */
    @Override
    public Pipe placePump(Pump holdingPump) {
        NonPipe n;
        if (holdingPump == null) {
            Control.getInstance().appendToLog("No placable pump");
            // System.out.println("No placable pump");
        }
        n = (NonPipe) getNeighbors().get(0);
        if (n != null) {
            removeNeighbor(n);
            n.removeNeighbor(this);
            holdingPump.addNeighbor(this);
            addNeighbor(holdingPump);
            Pipe p = new Pipe();
            p.addNeighbor(n);
            n.addNeighbor(p);
            holdingPump.addNeighbor(p);
            p.addNeighbor(holdingPump);
            int rnd = (int) (Math.random() * 1000) + 100;
            p.setName("pi" + rnd);
            Control.getInstance().appendToLog("Successfully placed " + holdingPump.getName() + ", new pipe: " + p.getName());
            // System.out.println("Successfully placed " + holdingPump.getName() + ", new pipe: " + p.getName());
            return p;
        } else {
            Control.getInstance().appendToLog("Can't place " + holdingPump.getName() + " on " + this.getName());
            // System.out.println("Can't place " + holdingPump.getName() + " on " + this.getName());
            return null;
        }
    }

    /**
     * Olyan cso felemelesenel hasznaljuk, amelyiknek az egyik fele nincs sehova
     * bekotve.
     * 
     * @param dir - nem hasznaljuk ebben a megvalositsban
     * @return Pipe - a cso, amit felemelunk
     */
    @Override
    public Pipe lift(int dir) {
        try {
            if (neighbors.size() == 1 && neighbors.get(0).getClass().getName().equals("Cistern")) {
                return this;
            }
        } catch (IndexOutOfBoundsException e) {
            Control.getInstance().appendToLog("Invalid pipe to pick up.");
            // System.out.println("Invalid pipe to pick up.");
            return null;
        }
        Control.getInstance().appendToLog("Invalid pipe to pick up.");
        // System.out.println("Invalid pipe to pick up.");
        return null;

    }

    /**
     * Uj szomszed hozzacsatlakoztatasa a csohoz.
     * 
     * @param n - a csatlakoztatni kivant szomszed
     */
    public void addNeighbor(NonPipe n) {
        if (neighbors.size() < 2) {
            neighbors.add(n);
        }
    }

    /**
     * Ezen keresztul lehet rola szomszedot lecsatlakoztatni.
     * 
     * @param n - a lecsatlakoztatni kivant szomszed
     */
    public void removeNeighbor(NonPipe n) {
        neighbors.remove(n);
    }

    /**
     * Visszadja a kifolyott viz mennyiseget, majd nullara allitja
     * 
     * @return int - a kifolyott viz mennyisege
     */
    @Override
    public int measureAndResetLeakedWaterAmount() {
        int lkdwtramt = leakedWaterAmount;
        leakedWaterAmount = 0;
        return lkdwtramt;
    }

    /**
     * Kiszivja az adott csobol a vizet
     * 
     * @return boolean - volt-e benne viz
     */
    public boolean waterExtraction() {
        if (containingWater) {
            containingWater = false;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Vizet probal a csobe tenni
     * 
     * @return boolean - sikerult-e bele vizet tenni
     */
    public boolean giveWater() {
        boolean out = false;
        if (containingWater) {
            out = false;
        } else {
            containingWater = true;
            out = true;
        }
        return out;
    }

    /** Ragadossa teszi az adott poziciot. */
    public void stick() {
        sticky = -Game.sticky;
        Control.getInstance().appendToLog(this.getName() + " is now sticky");
        // System.out.println(this.getName() + " is now sticky");
    };

    /** Csuszossa tesz egy csovet. */
    public void slime() {
        slimey = Game.slimey;
        Control.getInstance().appendToLog(this.getName() + " is now slimey");
        // System.out.println(this.getName() + " is now slimey");
    };

    /**
     * Visszaadja a szomszedait
     * 
     * @return List<NonPipe> - a szomszedok
     */
    public List<NonPipe> getNeighbors() {
        return neighbors;
    }

    /**
	 * Az osztály fontosabb attribútumait összefűzve adja vissza egy String-gé
	 * @return String
	 */
    @Override
    public String toString() {
        return "Pi " + this.getName() + " " + holeOnPipe + " " + containingWater + " " + leakedWaterAmount + " "
                + slimey + " " + sticky;
    }

    /**
	 * Van-e lyuk a csövön
	 * @return bool
	 */
    public boolean getHoleOnPipe() {
        return holeOnPipe;
    }

    /**
	 * Van-e víz a csőben
	 * @return bool
	 */
    public boolean getContainingWater() {
        return containingWater;
    }

    /**
	 * hány körig csúszós
	 * @return bool
	 */
    public int getSlimey() {
        return slimey;
    }

    /**
	 * hány körig ragad
	 * @return bool
	 */
    public int getSticky() {
        return sticky;
    }
}
