import java.math.*;
import java.util.*;

public class SquareRootModN {
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner sc = new Scanner(System.in);
		System.out.println("INPUT THE NUMBER YOU WISH TO FIND THE SQUARE ROOTS OF AND THE FACTORS OF N");
		BigInteger a = sc.nextBigInteger();
		BigInteger p = sc.nextBigInteger();
		BigInteger q = sc.nextBigInteger();
		if(cr.isItQR(a,p)&&cr.isItQR(a, q))
			System.out.println(cr.modSQRoot(a,p,q));
		else
			System.out.println("THAT NUMBER IS NOT A QUADRATIC RESIDUE");
		sc.close();
	}
}
