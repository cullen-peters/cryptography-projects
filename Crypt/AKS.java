import java.math.*;
import java.util.*;
 /*
 * 1. Check if n is a perfect power: if n = ab for integers a > 1 and b > 1, output composite.
 * 2. Find the smallest r such that ordr(n) > (log n)^2. (if r and n are not coprime, then skip this r)
 * 3. For all 2 ≤ a ≤ min(r, n−1), check that a does not divide n: If a|n for some 2 ≤ a ≤ min(r, n−1), output composite.
 * 4. If n ≤ r, output prime.
 * 5. For a = 1 to {\displaystyle \left\lfloor \scriptstyle {{\sqrt {\varphi (r)}}\log _{2}(n)}\right\rfloor } {\displaystyle \left\lfloor \scriptstyle {{\sqrt {\varphi (r)}}\log _{2}(n)}\right\rfloor } do
 * 		if (X+a)^n ≠ Xn+a (mod X^r − 1,n), output composite;
 * 6. Output prime.
 */
public class AKS
{
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("INPUT THE NUMBER YOU WOULD LIKE TO TEST:");
		BigInteger n = scan.nextBigInteger();
		boolean isItPrime = cr.isPrimeAKS(n);
		System.out.println("It is "+isItPrime+" that the number is prime.");
		scan.close();
	}
}