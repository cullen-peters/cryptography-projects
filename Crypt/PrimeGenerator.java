import java.math.*;
import java.util.*;

public class PrimeGenerator {
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("ENTER THE NUMBER OF BITS: ");
		int t = sc.nextInt();
		BigInteger p = cr.randPrime(t);
		System.out.println(p);
		BigInteger q = cr.randPrime(t);

		BigInteger prod = p.multiply(q);
		System.out.println(q);System.out.println("THE PRODUCT IS: "+prod);
		sc.close();
	}
}