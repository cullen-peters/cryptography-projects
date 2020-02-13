import java.math.*;
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
	public static void main(String[] args) {
		boolean isItPrime = isPrimeAKS(new BigInteger(args[0]));
		if (isItPrime)
			System.out.println("The number is PRIME");
		else
			System.out.println("The number is NOT PRIME");
	}

	public static BigInteger nthRoot(BigInteger a, int n)
	{
		BigDecimal aa = new BigDecimal(a);
		BigDecimal nn = new BigDecimal(n);

		int nmin1 = n-1;
		BigDecimal nextx = new BigDecimal("2");
		BigDecimal deltax = new BigDecimal("2");
		while((deltax.abs().compareTo(new BigDecimal("0.01"))>0))
		{
			deltax = aa.divide(nextx.pow(nmin1), RoundingMode.HALF_EVEN);
			deltax = deltax.subtract(nextx).divide(nn, RoundingMode.HALF_EVEN);
			nextx = deltax.add(nextx);
		}
		BigInteger x = nextx.toBigInteger();
		return x;
	}
	public static boolean isPerfectPower(BigInteger n)
	{
		long log = n.bitLength();
		for(int i = 2; i<=log;i++)
		{
			BigInteger a = nthRoot(n,i);
			BigInteger exp = BigInteger.valueOf((long)i);
			BigInteger possn = a.modPow(exp,n.add(BigInteger.ONE));
			if(possn.equals(n))
				return true;
		}
		return false;
	}
	public static int smallestOrder(BigInteger n)
	{
		int maxj = n.bitLength()*n.bitLength();
		boolean marker = true;
		int r;
		for(r=2;marker;r++)
		{
			marker = false;
			for(int j=1;(!marker)&&j<=maxj;j++)
			{
				marker = (n.modPow(BigInteger.valueOf((long)j),BigInteger.valueOf((long)r)).equals(BigInteger.ONE))||(n.modPow(BigInteger.valueOf((long)j),BigInteger.valueOf((long)r)).equals(BigInteger.ZERO));
			}
		}
		r--;
		return r;
	}
	public static boolean isPrimeAKS(BigInteger n) {
		// 1.)
		if(isPerfectPower(n))
			return false;		
		// 2.)
		int r = smallestOrder(n);
		// 3.)
		for(int i=2;i<=r;i++)
		{
			BigInteger gcd = n.gcd(BigInteger.valueOf((long)i));
			if(!gcd.equals(BigInteger.ONE)&&gcd.compareTo(n)<0)
				return false;
		}
		// 4.)
		if(n.compareTo(BigInteger.valueOf((long)r))<=0)
			return true;
		// 5.)
		int sqrtrlog = (int)Math.sqrt((double)r-1)*n.bitLength();
		BigInteger nmodrBI = n.mod(BigInteger.valueOf((long)r)); int nmodr = nmodrBI.intValue();
		Polynomial.BigInt xtothen = new Polynomial.BigInt(n);
		Polynomial.BigInt zerop = new Polynomial.BigInt(n);
		zerop.coeff.add(BigInteger.ZERO);
		for(int i=2;i<nmodr;i++)
			xtothen.coeff.add(BigInteger.ZERO);
		xtothen.coeff.add(BigInteger.ONE);
		for(int a=1;a<=sqrtrlog;a++)
		{
			Polynomial.BigInt xplusa = new Polynomial.BigInt(n); xplusa.coeff.clear();
			xplusa.coeff.add(BigInteger.valueOf((long)a)); xplusa.coeff.add(BigInteger.ONE);
			Polynomial.BigInt xtothenplusa = xtothen; xtothenplusa = xtothenplusa.polyAdd(zerop);
			xtothenplusa.coeff.add(0, BigInteger.valueOf((long)a));
			Polynomial.BigInt xandatothen = xplusa.AKSpowerMod(n, r);
			if(xandatothen.coeff.equals(xtothenplusa.coeff))
				return false;
		}
		return true;
	}
}