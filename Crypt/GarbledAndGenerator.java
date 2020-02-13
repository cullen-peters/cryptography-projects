import java.util.*;

public class GarbledAndGenerator
{
	public static void main(String[] args)throws Exception
	{
		GarbledAnd gand = new GarbledAnd();
		Scanner scan = new Scanner(System.in);
		Crypto cr = new Crypto();
		
		System.out.println("Enter your bit, 0 or 1");
		String c = scan.next();
		ArrayList<String> g = gand.garble();
		if(c.equals("0"))
		{
			System.out.println("a0: "+g.get(4));
		}
		if(c.equals("1"))
		{
			System.out.println("a1: "+g.get(5));
		}
		System.out.println("e1: "+g.get(0));
		System.out.println("e2: "+g.get(1));
		System.out.println("e3: "+g.get(2));
		System.out.println("e4: "+g.get(3));
		System.out.println("o0: "+g.get(8));
		System.out.println("o1: "+g.get(9));
		System.out.println(cr.OTSender(g.get(6), g.get(7)));
		scan.close();
	}
}
