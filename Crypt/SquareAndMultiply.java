import java.math.*;
import java.util.*;

public class SquareAndMultiply
{
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("INPUT THE NUMBER YOU WANT TO EXPONENTIATE:");
		BigInteger a = scan.nextBigInteger();
		System.out.println("INPUT THE EXPONENT YOU WISH TO USE:");
		BigInteger b = scan.nextBigInteger();
		System.out.println("INPUT THE MOD:");
		BigInteger n = scan.nextBigInteger();
		BigInteger x = cr.powerMod(a, b, n);
		System.out.println("YOUR ANSWER IS: " + x);
		scan.close();
	}
}