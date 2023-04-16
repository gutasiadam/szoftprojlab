/** @authorBodnar Mark*/
public abstract class Character {
    private String Name;

    String getName() {return Name;}
    void setName(String name) {this.Name=name;}
	public void move(int dir) {}/** Csorendszeren mozgas.*/
	public void adjustPump(int src, int dest) {}/** CPumpak ki- es bemenetenek valtoztatasa*/
	public void step() {}
}
