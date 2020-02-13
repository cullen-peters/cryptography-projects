import java.math.*;
import java.security.*;
import java.util.*;

public class Crypto
{
	public final BigInteger NONE = BigInteger.valueOf(1L).negate();
	public final BigInteger ZERO = BigInteger.valueOf(0L);
	public final BigInteger ONE = BigInteger.valueOf(1L);
	public final BigInteger TWO = BigInteger.valueOf(2L);
	public final BigInteger THREE = BigInteger.valueOf(3L);
	public final BigInteger FOUR = BigInteger.valueOf(4L);
	public int COMPOSITEARRAYSIZE = 100000000;
	public boolean compositeArray[];
	public final ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
	public BigInteger powerMod(BigInteger a, BigInteger b, BigInteger n)
	{
		String sb = b.toString(2);
		char[] cab = sb.toCharArray();
		int ln = cab.length;
		ArrayList<BigInteger> powers = new ArrayList<BigInteger>();
		a = a.mod(n);
		powers.add(a);
		for (int i = 0; i < ln - 1; i++)
		{
			BigInteger p = powers.get(i);
			p = p.multiply(p);
			p = p.mod(n);
			powers.add(p);
		}
		//System.out.println(powers);
		//System.out.println(cab);
		// System.out.println(ln);
		BigInteger m = ONE;
		for (int i = ln - 1; i >= 0; i--) {
			if (cab[i] == '1') {
				m = m.multiply(powers.get(ln - i - 1));
				m = m.mod(n);
			}
		}
		return m;
	}
	public BigInteger power(BigInteger a, BigInteger b)
	{
		String sb = b.toString(2);
		char[] cab = sb.toCharArray();
		int ln = cab.length;
		ArrayList<BigInteger> powers = new ArrayList<BigInteger>();
		powers.add(a);
		for (int i = 0; i < ln - 1; i++) {
			BigInteger p = powers.get(i);
			p = p.multiply(p);
			powers.add(p);
		}
		//System.out.println(powers);
		//System.out.println(cab);
		// System.out.println(ln);
		BigInteger m = ONE;
		for (int i = ln - 1; i >= 0; i--) {
			if (cab[i] == '1') {
				m = m.multiply(powers.get(ln - i - 1));
			}
		}
		return m;
	}
	public BigDecimal power(BigDecimal a, BigInteger b)
	{
		String sb = b.toString(2);
		char[] cab = sb.toCharArray();
		int ln = cab.length;
		ArrayList<BigDecimal> powers = new ArrayList<BigDecimal>();
		powers.add(a);
		for (int i = 0; i < ln - 1; i++) {
			BigDecimal p = powers.get(i);
			p = p.multiply(p);
			powers.add(p);
		}
		//System.out.println(powers);
		//System.out.println(cab);
		// System.out.println(ln);
		BigDecimal m = new BigDecimal("1");
		for (int i = ln - 1; i >= 0; i--) {
			if (cab[i] == '1') {
				m = m.multiply(powers.get(ln - i - 1));
			}
		}
		return m;
	}
	public boolean isPrime (BigInteger N, int t)
	{	
		if (N.equals(ZERO) || N.equals(ONE))
			return false;
		if (N.equals(TWO))
			return true;
		if((N.mod(TWO)).equals(ZERO))
			return false;
		
		BigInteger nMinOne = N.subtract(ONE);
		BigInteger u = nMinOne;
		int r = 1;
		BigInteger div = TWO;
		
		while(u.mod(TWO).equals(ZERO))
		{
			div = TWO.pow(r);
			u = nMinOne.divide(div);
			r++;
		}
		r--;
	//System.out.println("r: "+r);
	//System.out.println("u: "+u);
		//System.out.println("Pre Random");
		//SecureRandom random = new SecureRandom();
		Random random = new Random();
		BigInteger a;
		//System.out.println("Post Random");
		for(int i=0; i<t; i++)
		{
			//long a = (long)(Math.random()*(N.intValue()-3)+2);   //This is troubling since N is big but int values are not
			
			a = new BigInteger(N.bitLength(), random);
			a = a.mod(N);
			if(a.equals(ZERO))
				a=a.add(TWO);
			if(a.equals(ONE))
				a= a.add(ONE);
			
			BigInteger aToU = powerMod(a,u,N);
			BigInteger aToUPowers = aToU;
			//System.out.println("a: "+a);
			//System.out.println("aToU: "+aToU);
			if(aToU.equals(ONE)||(aToU.equals(nMinOne)))
				continue;
			boolean contflag=false;
			for(int j=1;j<=r;j++)
			{
				aToUPowers = powerMod(aToUPowers,TWO,N);
			//System.out.println("aToUPowers: "+aToUPowers);
				if(aToUPowers.equals(nMinOne))
				{	
					contflag=true;
					break;
				}
			}
			if(contflag==true)
				continue;
			return false;
		}
		return true;
	}
	public BigInteger pollardsRho(BigInteger N)
	{
		if(isPrime(N, 1000))
			return N;
		SecureRandom random = new SecureRandom();
		
		BigInteger x0  = new BigInteger(N.bitLength(), random);
		BigInteger X = x0.mod(N);
		//System.out.println("X: "+X);
		BigInteger XPRIME = X;
		BigInteger P = ONE;
		int max = 2^(N.bitLength()/4)+1;
		
		do {
			BigInteger a  = new BigInteger(N.bitLength(), random).mod(N);
			BigInteger b  = new BigInteger(N.bitLength(), random).mod(N);
			BigInteger c  = new BigInteger(N.bitLength(), random).mod(N);
		
			
			for(int i=0;i<=max;i++)
			{
				X = (powerMod(X,TWO,N).multiply(a).add(c).add(X.multiply(b))).mod(N);
				//System.out.println("X: "+X);
				XPRIME = (powerMod(XPRIME,TWO,N).multiply(a).add(c).add(XPRIME.multiply(b))).mod(N);
				XPRIME = (powerMod(XPRIME,TWO,N).multiply(a).add(c).add(XPRIME.multiply(b))).mod(N);
				//System.out.println("X': " +XPRIME);
				P = (X.subtract(XPRIME)).mod(N).gcd(N);
				//System.out.println("P: " +P);
				if(!P.equals(ONE)&&!P.equals(N))
					return P;
			}
		}
		while(P.equals(ONE)||P.equals(N));
		return P;
	}
	public void factor(BigInteger N)
	{
		if(N.equals(ONE))
			return;
		if(isPrime(N,100))
		{
			primes.add(N);
			return;
		}
		BigInteger p = pollardsRho(N);
		factor(p);
		factor(N.divide(p));
	}
	public boolean isItQR (BigInteger a, BigInteger p)
	{
		BigInteger exponent = (p.subtract(ONE)).divide(TWO);
		BigInteger legendreSymbol = powerMod(a,exponent,p);
		if(legendreSymbol.equals(ONE))
			return true;
		return false;
	}
	public BigInteger modSQRoot (BigInteger a, BigInteger p)
	{
		BigInteger theCase = p.mod(FOUR);
		BigInteger power = (p.subtract(ONE)).divide(TWO);
		BigInteger NEGONE = p.subtract(ONE);
		
		if(theCase.equals(THREE))
		{
			BigInteger exponent = (p.add(ONE)).divide(FOUR);
			BigInteger sqroot = powerMod(a,exponent,p);
			return sqroot;
		}
		
		BigInteger b = ZERO;
		
		for (int i = 2; i < p.intValue(); i++)
		{
			long li = (long)i;
			b = BigInteger.valueOf(li);
			boolean qr = isItQR(b,p);
			if(!qr)
				break;
		}
		
		int l = 1;
		BigInteger div = TWO;
		BigInteger m = power;
		while((m.mod(TWO)).equals(ZERO))
		{
			div = TWO.pow(l);
			m = m.divide(div);
			l++;
		}
		l=l-1;
		BigInteger r = power;
	
		BigInteger rPrime = ZERO;
			
		for(int i = l; i>=1;i--)
		{
			//maintain the invariant (a^r)(b^r')=1 (mod p)
			r = r.divide(TWO);
			rPrime = rPrime.divide(TWO);
			BigInteger product = (powerMod(a,r,p)).multiply(powerMod(b,rPrime,p));
			if(product.equals(NEGONE))
			{
				rPrime = rPrime.add(power);
			}
		}
		//now r=m, r' is even, and (a^r)(b^r')=1 (mod p)
		BigInteger exp1 = (r.add(ONE)).divide(TWO);
		BigInteger exp2 = rPrime.divide(TWO);
		BigInteger sqroot = ((powerMod(a,exp1,p)).multiply(powerMod(b,exp2,p))).mod(p);
		return sqroot;
	}
	public BigInteger randPrime(int t)
	{
		SecureRandom random = new SecureRandom();
		BigInteger n;
		do 
		{
			n  = new BigInteger(t, random);
		}
		while(!isPrime(n,100));
		return n;
	}
	public BigInteger legendre(BigInteger a, BigInteger p)
	{
		BigInteger power = p.subtract(ONE).divide(TWO);
		BigInteger lgndr = powerMod(a,power,p);
		if(!lgndr.equals(ONE))
			lgndr = lgndr.subtract(p);
		return lgndr;
	}
	public BigInteger jacobiSymbol(BigInteger a, BigInteger b)
	{
		int sign = a.signum();
		BigInteger jac = ONE;
		
		if(b.equals(ZERO))
		{
			jac = ZERO;
			return jac;
		}
		if(sign == (-1))
			a = b.subtract(a.abs().mod(b));
		a = a.mod(b);
		if(a.equals(ZERO))
		{
			jac = ZERO;
			return jac;
		}
		if(a.equals(ONE))
		{
			jac = ONE;
			return jac;
		}
		
		if(a.equals(b.subtract(ONE)))
	    {
	    	if((b.mod(FOUR)).equals(ONE))
	    	{
	    		jac = ONE;
	    		return jac;
	    	}
	    	if(b.mod(FOUR).equals(THREE))
	    	{
	    		jac = NONE;
	    		return jac;
	    	}
	    }
	    if(a.mod(TWO).equals(ZERO))
	    {
	    	a = a.divide(TWO);
	    	if(b.mod(BigInteger.valueOf(8L)).equals(ONE)||b.mod(BigInteger.valueOf(8L)).equals(BigInteger.valueOf(7L)))
	    	{
	    		jac = jacobiSymbol(a,b);
	    		return jac;
	    	}
	    	if(b.mod(BigInteger.valueOf(8L)).equals(THREE)||b.mod(BigInteger.valueOf(8L)).equals(BigInteger.valueOf(5L)))
	    	{
	    		jac = jacobiSymbol(a,b).negate();
	    		return jac;
	    	}
	    }
	    if((b.mod(FOUR)).equals(THREE)&&(a.mod(FOUR)).equals(THREE))
	    {
	    	jac = jacobiSymbol(b,a).negate();
	    	return jac;
	    }
	    if((b.mod(FOUR)).equals(ONE)||(a.mod(FOUR)).equals(ONE))
	    {
	    	jac = jacobiSymbol(b,a);
	    	return jac;
	    }
	    return jac;

		/*factor(b);
		int length = primes.size();
		BigInteger jacobi = ONE;
		BigInteger x = a;
		for(int i=0;i<length;i++)
		{
			x=a;
			x.mod(primes.get(i));
			jacobi = jacobi.multiply(legendre(x,primes.get(i)));
		}
		return jacobi;*/
	}
    public ArrayList<BigInteger> extEuclid(BigInteger a, BigInteger b)
    {
        BigInteger x = ZERO, y = ONE, lastx = ONE, lasty = ZERO, temp;
        ArrayList<BigInteger> last = new ArrayList<BigInteger>();
        while (!b.equals(ZERO))
        {
            BigInteger q = a.divide(b);
            BigInteger r = a.mod(b);
 
            a = b;
            b = r;
 
            temp = x;
            x = lastx.subtract(q.multiply(x));
            lastx = temp;
            temp = y;
            y = lasty.subtract(q.multiply(y));
            lasty = temp;
        }
        last.add(lastx);
        last.add(lasty);
        return last;
    }
	public ArrayList<BigInteger> chineseRemThm(BigInteger a, BigInteger p, BigInteger q)
	{
		ArrayList<BigInteger> pair = new ArrayList<BigInteger>();
		pair.add(a.mod(p));
		pair.add(a.mod(q));
		return pair;
	}
	public BigInteger chineseRemThm(BigInteger ap, BigInteger aq, BigInteger p, BigInteger q)
	{
		ArrayList<BigInteger> xy = extEuclid(p,q);
		BigInteger onep = q.multiply(xy.get(1)).mod(p.multiply(q));
		BigInteger oneq = p.multiply(xy.get(0)).mod(p.multiply(q));
		BigInteger a = ap.multiply(onep).add(aq.multiply(oneq));
		a = a.mod(q.multiply(p));
		return a;
	}
	public ArrayList<BigInteger> modSQRoot(BigInteger a, BigInteger p, BigInteger q)
	{
		BigInteger ap = a.mod(p);
		BigInteger aq = a.mod(q);
		BigInteger sqrtap1 = modSQRoot(ap,p);
		BigInteger sqrtap2 = p.subtract(sqrtap1);
		BigInteger sqrtaq1 = modSQRoot(aq,q);
		BigInteger sqrtaq2 = q.subtract(sqrtaq1);
		
		BigInteger sqrt1 = chineseRemThm(sqrtap1,sqrtaq1,p,q);
		BigInteger sqrt2 = chineseRemThm(sqrtap1,sqrtaq2,p,q);
		BigInteger sqrt3 = chineseRemThm(sqrtap2,sqrtaq1,p,q);
		BigInteger sqrt4 = chineseRemThm(sqrtap2,sqrtaq2,p,q);
		
		ArrayList<BigInteger> sqrts = new ArrayList<BigInteger>();
		sqrts.add(sqrt1);sqrts.add(sqrt2);sqrts.add(sqrt3);sqrts.add(sqrt4);
		return sqrts;
	}
	public BigInteger nthRoot(BigInteger a, int n)
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
			//System.out.println(nextx);
			nextx = deltax.add(nextx);
		}
		//System.out.println("last "+nextx);
		BigInteger x = nextx.toBigInteger();
		return x;
	}
	public boolean isPerfectPower(BigInteger n)
	{
		long log = n.bitLength();
		//BigInteger LOG = BigInteger.valueOf(log);
		for(int i = 2; i<=log;i++)
		{
			BigInteger a = nthRoot(n,i);
			BigInteger exp = BigInteger.valueOf((long)i);
			BigInteger possn = powerMod(a,exp,n.add(ONE));
			if(possn.equals(n))
				return true;
		}
		return false;
	}
	public BigInteger min(BigInteger n, BigInteger m)
	{
		BigInteger sub = n.subtract(m);
		if(sub.compareTo(ZERO)<0)
			return n;
		return m;
	}
	public boolean isPrimeAKS(BigInteger n)
	{
		// 1.)
		if(isPerfectPower(n))
			return false;		
		// 2.)
		int r = smallestOrder(n);
		System.out.println("r "+r);
		// 3.)
		for(int i=2;i<=r;i++)
		{
			BigInteger gcd = n.gcd(BigInteger.valueOf((long)i));
			if(!gcd.equals(ONE)&&gcd.compareTo(n)<0)
				return false;
		}
		// 4.)
		if(n.compareTo(BigInteger.valueOf((long)r))<=0)
			return true;
		// 5.)
		int sqrtrlog = (int)Math.sqrt((double)r-1)*n.bitLength();
		System.out.println("sqrtrlog "+sqrtrlog);
		BigInteger nmodrBI = n.mod(BigInteger.valueOf((long)r)); int nmodr = nmodrBI.intValue();
		Polynomial xtothen = new Polynomial(n);
		Polynomial zerop = new Polynomial(n);
		zerop.coeff.add(ZERO);
		for(int i=2;i<nmodr;i++)
			xtothen.coeff.add(ZERO);
		xtothen.coeff.add(ONE);
		for(int a=1;a<=sqrtrlog;a++)
		{
			Polynomial xplusa = new Polynomial(n); xplusa.coeff.clear();
			xplusa.coeff.add(BigInteger.valueOf((long)a)); xplusa.coeff.add(ONE);
			Polynomial xtothenplusa = xtothen; xtothenplusa = xtothenplusa.polyAdd(zerop);
			xtothenplusa.coeff.add(0, BigInteger.valueOf((long)a));
			Polynomial xandatothen = xplusa.AKSpowerMod(n, r);
			if(xandatothen.coeff.equals(xtothenplusa.coeff))
				return false;
		}
		return true;
	}
	public int max(int p, int q)
	{
		int dif = p-q;
		if(dif>0)
			return p;
		else
			return q;
	}
	public int min(int p, int q)
	{
		int dif = p-q;
		if(dif>0)
			return q;
		else
			return p;
	}
	public int smallestOrder(BigInteger n)
	{
		int maxj = n.bitLength()*n.bitLength();
		//System.out.println(maxj);
		boolean marker = true;
		int r;
		for(r=2;marker;r++)
		{
			//System.out.println(r);
			marker = false;
			for(int j=1;(!marker)&&j<=maxj;j++)
			{
				//System.out.println(marker);
				marker = (powerMod(n,BigInteger.valueOf((long)j),BigInteger.valueOf((long)r)).equals(ONE))||(powerMod(n,BigInteger.valueOf((long)j),BigInteger.valueOf((long)r)).equals(ZERO));
				//System.out.print(j+" ");
				//System.out.println(marker);
			}
			//System.out.println(marker);
		}
		r--;
		return r;
	}
	public String binaryHash(String s)throws Exception
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.update(s.getBytes());
		byte[] byteStuff = digest.digest();
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteStuff.length; i++)
        {
         sb.append(Integer.toString((byteStuff[i] & 0xff) + 0x100, 16).substring(1));
        }
        String encodedHash = sb.toString();
        String binary = new BigInteger(encodedHash,16).toString(2);
		return binary;
	}
	public ArrayList<String> OTSender(String m0, String m1)throws Exception
	{
		BigInteger g = TWO;
		BigInteger p = new BigInteger("2312838245153358657841020929173777138757586553879423722891578318583232019507371667267663915132882405691832245246661258139");
		OneTimePadPlus otpp = new OneTimePadPlus();
		Scanner sc = new Scanner(System.in);
		
		BigInteger a = randPrime(p.bitLength()).mod(p);
		BigInteger A = powerMod(g, a, p);
		System.out.println("A is "+A);
		System.out.println("Enter B");
		BigInteger B = sc.nextBigInteger();
		BigInteger Btothea = powerMod(B, a, p);
		BigInteger BoverA = B.multiply(A.modInverse(p));
		BigInteger BoverAtothea = powerMod(BoverA, a, p);
		String k0 = binaryHash(Btothea.toString());
		String k1 = binaryHash(BoverAtothea.toString());
		String e0 = otpp.enc(m0, k0);
		String e1 = otpp.enc(m1, k1);
		ArrayList<String> output = new ArrayList<String>();
		output.add(e0);
		output.add(e1);
		sc.close();
		return output;
	}
	public String OTReceiver(int c)throws Exception
	{
		if(c!=1&&c!=0)
			return "\u22A5";
		BigInteger g = TWO;
		BigInteger p = new BigInteger("2312838245153358657841020929173777138757586553879423722891578318583232019507371667267663915132882405691832245246661258139");
		OneTimePadPlus otpp = new OneTimePadPlus();
		Scanner sc = new Scanner(System.in);
		
		BigInteger b = randPrime(p.bitLength()).mod(p);
		System.out.println("Enter A:");
		BigInteger A = sc.nextBigInteger();
		BigInteger B = ZERO;
		B = powerMod(g,b,p);
		if(c==1)
		{
			B = A.multiply(B);
		}
		System.out.println("B is "+B);
		BigInteger Atotheb = powerMod(A,b,p);
		String k = binaryHash(Atotheb.toString());
		System.out.println("Enter e0:");
		String e0 = sc.next();
		System.out.println("Enter e1:");
		String e1 = sc.next();
		sc.close();
		if(c==0)
			return otpp.dec(e0, k);
		if(c==1)
			return otpp.dec(e1, k);
		return "\u22A5";
	}
	
	public static void main(String[] args)
	{
		Crypto cr = new Crypto();
		//System.out.println(cr.nthRoot(new BigInteger("64"),3));
		//System.out.println(cr.isPerfectPower(new BigInteger("256")));
		System.out.println(cr.smallestOrder(new BigInteger("8675309")));
	}
}