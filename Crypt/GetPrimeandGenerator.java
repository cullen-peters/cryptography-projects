import java.util.*;
import java.math.*;

public class GetPrimeandGenerator
{
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner sc = new Scanner(System.in);
		System.out.println("Input the maximum number of bits for the prime you want to generate:");
		int t = sc.nextInt();
		int bits=t-1;
		BigInteger p = cr.ZERO;
		BigInteger q = cr.FOUR;
		boolean isitprime = false;
		while(!isitprime)
		{
			q = cr.randPrime(bits);
			p = q.multiply(cr.TWO).add(cr.ONE);
			isitprime = cr.isPrime(p, 50);
		}
		BigInteger gsquared = cr.ONE;
		BigInteger gtotheq = cr.ONE;
		BigInteger g = cr.ONE;
		for(BigInteger i=cr.TWO;gsquared.compareTo(cr.ONE)==0||gtotheq.compareTo(cr.ONE)==0;i=i.add(cr.ONE))
		{
			g = i;
			gsquared = cr.powerMod(g, cr.TWO, p);
			gtotheq = cr.powerMod(g, q, p);
		}
		System.out.println("p: "+p);
		//System.out.println(p.bitLength());
		System.out.println("g: "+g);
		sc.close();
	}
}
