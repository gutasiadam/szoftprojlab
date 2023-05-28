/** @author Bodnar Mark*/
public abstract class Character {
    private String Name;

    String getName() {return Name;}
    void setName(String name) {this.Name=name;}
	public void move(int dir) {}/** Csorendszeren mozgas.*/
	public void adjustPump(int src, int dest) {}/** Pumpak ki- es bemenetenek valtoztatasa*/
	void dealDamage() {}/** Lyukasztas*/
	public synchronized void step() {
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
