/**
 * A skeleton osztálybeli parancssoros diagramok megjelenítéséhez használt osztály.
 */
public class Tabulator {

	private static int tabNum = 0;
	
	public static void printTab()
	{
		for(int i = 0;i<tabNum;i++)
			System.out.print("\t");
	}
	public static void increaseTab()
	{
		tabNum++;
	}
	public static void decreaseTab()
	{
		tabNum--;
	}
	
	public static void reset()
	{
		tabNum=0;
	}
}
