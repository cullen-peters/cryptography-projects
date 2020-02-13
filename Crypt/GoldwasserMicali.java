import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class GoldwasserMicali
{
	Crypto cr = new Crypto();
	public ArrayList<BigInteger> Gen(int t)
	{
		SecureRandom rand = new SecureRandom();
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		
		BigInteger p = cr.randPrime(t);
		BigInteger q = cr.randPrime(t);
		BigInteger N = p.multiply(q);
		BigInteger jzp,jzq,z;
		do
		{
			z = new BigInteger(t,rand);
			z = z.mod(p.multiply(q));
			jzp = cr.legendre(z, p);
			jzq = cr.legendre(z, q);
		}
		while (!jzp.equals(cr.NONE)|!jzq.equals(cr.NONE));
		keys.add(z);
		keys.add(N);
		keys.add(p);
		keys.add(q);
		return keys;
	}
	public ArrayList<BigInteger> Enc(BigInteger z,BigInteger N, String m)
	{
		SecureRandom rand = new SecureRandom();
		
		int l = m.length();
		char[] mCharArray = m.toCharArray();
		ArrayList<BigInteger> messagelist = new ArrayList<BigInteger>();
		ArrayList<BigInteger> clist = new ArrayList<BigInteger>();
		for(int i=0;i<l;i++)
		{
			long mi = Character.getNumericValue(mCharArray[i]);
			messagelist.add(BigInteger.valueOf(mi));
		}
		int bits = N.bitLength();
		for(int i=0;i<l;i++)
		{
			BigInteger yi;
			do
			{
				yi = new BigInteger(bits,rand);
			}
			while(!yi.gcd(N).equals(cr.ONE));
			BigInteger c = cr.powerMod(yi,cr.TWO, N).multiply(cr.powerMod(z,messagelist.get(i),N)).mod(N);
			clist.add(c);
		}
		return clist;
	}
	public String Dec(BigInteger p, BigInteger q, String c)
	{
		int l = c.length();
		String[] cArray = c.split(",");
		String m = "-1";
		//System.out.println(cArray[0]);
		l = cArray.length;
		BigInteger c0 = new BigInteger(cArray[0]);
		BigInteger lc0p = cr.legendre(c0, p);
		BigInteger lc0q = cr.legendre(c0, q);
		if((lc0p.equals(cr.NONE))&&(lc0q.equals(cr.NONE)))
			m = "1";
		if((lc0p.equals(cr.ONE))&&(lc0q.equals(cr.ONE)))
			m = "0";
		for(int i=1;i<l;i++)
		{
			BigInteger ci = new BigInteger(cArray[i]);
			BigInteger lcp = cr.legendre(ci, p);
			BigInteger lcq = cr.legendre(ci, q);
			if((lcp.equals(cr.NONE))&&(lcq.equals(cr.NONE)))
				m = m+"1";
			if((lcp.equals(cr.ONE))&&(lcq.equals(cr.ONE)))
				m = m+"0";
			//System.out.println(ci);
		}
		return m;
	}
}
