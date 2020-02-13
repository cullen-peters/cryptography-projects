import java.math.*;
import java.util.*;

public class ModularSquareRoots {
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("ENTER THE NUMBER YOU WOULD LIKE TO SQUARE ROOT OF: ");
		BigInteger a = scan.nextBigInteger();
		System.out.println("ENTER THE PRIME MOD: ");
		BigInteger p = scan.nextBigInteger();
		if(!cr.isItQR(a,p))
			System.out.println("THAT NUMBER IS NOT A QUADRATIC RESIDUE");
		else
		{
			BigInteger sqrt1 = cr.modSQRoot(a,p);
			BigInteger sqrt2 = p.subtract(sqrt1);
			System.out.println("THE SQUARE ROOTS OF "+a+" MOD "+p+" ARE: \n"+sqrt1+" "+sqrt2);
		}
		scan.close();
	}

}
