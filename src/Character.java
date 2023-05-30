/** @author Bodnar Mark*/
public abstract class Character {
    private String Name;
	private int remainingSteps = 3;
    String getName() {return Name;}
    void setName(String name) {this.Name=name;}
	public void move(int dir) {}/** Csorendszeren mozgas.*/
	public void adjustPump(int src, int dest) {}/** Pumpak ki- es bemenetenek valtoztatasa*/
	void dealDamage() {}/** Lyukasztas*/

	public int resetRemainingSteps() {
		remainingSteps=3;
		return remainingSteps;
	}
	public int getRemainingSteps() {
		return remainingSteps;
	}
	public int decreaseRemainingSteps() {
		Control.getInstance().appendToLog("Remaining steps: "+remainingSteps);
		remainingSteps--;
		return remainingSteps;
	}
	public synchronized void step() {
		resetRemainingSteps();
		try{
			wait();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void makeSticky(){}/** Ragadossa teszi az adott csovet amin all*/
	
	public synchronized void WakeUp() {
		this.notify();
	}
}
