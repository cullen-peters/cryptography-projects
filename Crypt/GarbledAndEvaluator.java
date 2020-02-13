import java.util.*;

public class GarbledAndEvaluator
{
	public static void main(String[] args)throws Exception
	{
		GarbledAnd gand = new GarbledAnd();
		Scanner scan = new Scanner(System.in);
		Crypto cr = new Crypto();
		
		System.out.println("Enter your bit, 0 or 1");
		int c = scan.nextInt();
		System.out.println("Enter the random string associated with the generator's choice");
		String sa = scan.next();
		System.out.println("Enter e1, e2, e3, e4, o0, and o1 separated by spaces");
		String e1 = scan.next();
		String e2 = scan.next();
		String e3 = scan.next();
		String e4 = scan.next();
		String o0 = scan.next();
		String o1 = scan.next();
		
		Wire b = new Wire(cr.OTReceiver(c));
		Wire a = new Wire(sa);
		
		System.out.println(gand.eval(e1, e2, e3, e4, o0, o1, a, b));
		scan.close();
	}
}
