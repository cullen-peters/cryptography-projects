import java.util.*;
import java.math.*;
import java.security.*;

public class DiffieHellmanKeyExchange
{
	public static void main(String[] args)
	{
		SecureRandom rand = new SecureRandom();
		Scanner sc = new Scanner(System.in);
		Crypto cr = new Crypto();
		int decision = 0;
		System.out.println("Enter 0 if you are the Sender or enter 1 if you are the Reciever");	
		decision = sc.nextInt();
		if(decision==0)
		{
			System.out.println("Enter the number of bits of security");
			int t = sc.nextInt();
			BigInteger p = cr.randPrime(t);
			BigInteger a = (new BigInteger(p.bitLength(),rand)).abs();
			a = a.mod(p);
			BigInteger g = (new BigInteger(p.bitLength(),rand)).abs();
			g = g.mod(p);
			BigInteger A = cr.powerMod(g,a,p);
			System.out.println("g: "+g+" p: "+p+" A: "+A);
			System.out.println("Enter B");
			BigInteger B = sc.nextBigInteger();
			BigInteger k = cr.powerMod(B,A,p);
			System.out.println("The key is "+k);
		}
		if(decision!=1)
		{
			System.out.println("Enter g, p, and A");
			BigInteger g = sc.nextBigInteger();
			BigInteger p = sc.nextBigInteger();
			BigInteger A = sc.nextBigInteger();
			BigInteger b = (new BigInteger(p.bitLength(),rand)).abs();
			b = b.mod(p);
			BigInteger B = cr.powerMod(g,b,p);
			System.out.println("B: "+B);
			BigInteger k = cr.powerMod(A,B,p);
			System.out.println("The key is "+k);
		}
		sc.close();
	}
}
