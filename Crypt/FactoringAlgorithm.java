import java.math.*;
import java.util.*;

public class FactoringAlgorithm
{
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("ENTER THE NUMBER YOU WISH TO FACTOR: ");
		BigInteger n = scan.nextBigInteger();
		cr.factor(n);
		System.out.println(cr.primes);
		scan.close();
	}
}
