import java.math.*;
import java.util.*;

public class MillerRabinTest {
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("INPUT THE NUMBER YOU WOULD LIKE TO TEST:");
		BigInteger n = scan.nextBigInteger();
		System.out.println("INPUT THE PARAMETER T:");
		int t = scan.nextInt();
		boolean isItPrime = cr.isPrime(n,t);
		System.out.println("It is "+isItPrime+" that the number is prime.");
		//for(int i =0; i<1000;i++)
			//System.out.println(cr.isPrime(n, t));
		scan.close();
	}
}