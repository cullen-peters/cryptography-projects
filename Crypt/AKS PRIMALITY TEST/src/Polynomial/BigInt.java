package Polynomial;

import java.math.*;
import java.util.*;
import java.security.*;

public class BigInt {
	SecureRandom rand = new SecureRandom();
	private final BigInteger n;
	public BigInteger siz;
	public ArrayList<BigInteger> coeff = new ArrayList<BigInteger>();
	
	public BigInt(BigInteger n, ArrayList<BigInteger> coeff) {
		this.n = n;
		this.coeff = coeff;
	}
	public BigInt(BigInteger n) {
		this.n = n;
	}
	public BigInt(BigInteger n,BigInteger siz) {
		this.n = n;
		for(BigInteger i=BigInteger.ZERO; i.compareTo(siz) < 0; i=i.add(BigInteger.ONE)) {
			this.coeff.add((new BigInteger(this.n.bitLength()+1,rand)).mod(n));
		}
	}

	public BigDec toDeciPolynomial()
	{
		BigDec p = new BigDec(new BigDecimal(n));
		int size = coeff.size();
		for(int i=0;i<size;i++)
		{
			p.decicoeff.add(new BigDecimal(coeff.get(i)));
		}
		return p;
	}
	public BigInteger evaluate(BigInteger x)
	{
		int size = this.coeff.size();
		BigInteger result = BigInteger.ZERO;
		for(int i=0; i<size; i++)
		{
			result = result.add((x.pow(i)).multiply(this.coeff.get(i)));
		}
		return result.mod(this.n);
	}
	public BigInt polyAdd(BigInt q)
	{
		BigInt sum = new BigInt(n);
		int sizep = coeff.size();
		int sizeq = q.coeff.size();
		int max = max(sizep,sizeq);
		for(int i=0;i<max;i++)
		{
			if(i<min(sizep,sizeq))
			{
				BigInteger input = (q.coeff.get(i)).add(coeff.get(i));
				sum.coeff.add(i, input);
			}
			else if(max==sizeq)
			{
				BigInteger input = (q.coeff.get(i)).add(BigInteger.ZERO);
				sum.coeff.add(i, input);
			}
			else
			{
				BigInteger input = (coeff.get(i).add(BigInteger.ZERO));
				sum.coeff.add(i, input);
			}
		}
		//sum.polyReduce();
		return sum;
	}
	public BigInt AKSmod(int r)
	{
		//takes a polynomial, returns that polynomial mod (x^r - 1),n
		BigInt j = this;
		if(j.coeff.size()>=r)
		{
			BigInt input = new BigInt(j.n);
			for(int i=0;i<r;i++)
			{
				input.coeff.add(BigInteger.ZERO);
			}
			j = j.polyAdd(input);
			for(int i=j.coeff.size()-1;i>=r;i--)
			{
				int pos = i%r;
				BigInteger ati = j.coeff.get(i);
				input.coeff.add(pos,ati);
				j.coeff.remove(i);
				j = j.polyAdd(input);
				input = input.polySubtract(input);
			}
		}
		return j;
	}
	public BigInt AKSpowerMod(BigInteger pow,int r)
	{
		BigInt zerop = new BigInt(n);
		zerop.coeff.add(BigInteger.ZERO);
		BigInt p = this;
		p = p.polyAdd(zerop);
		String pow2 = pow.toString(2);
		char[] cab = pow2.toCharArray();
		int len = cab.length;
		ArrayList<BigInt> powers = new ArrayList<BigInt>();
		powers.add(p);
		for (int i = 0; i < len-1; i++)
		{
			BigInt q = powers.get(i);
			q = q.polyAdd(zerop);
			q = q.polyMultiply(q,r);
			q = q.AKSmod(r);
			powers.add(q);
		}
		BigInt m = new BigInt(n);
		m.coeff.add(BigInteger.ONE);
		m = m.polyAdd(zerop);
		for (int i = len - 1; i >= 0; i--)
		{
			if (cab[i] == '1')
			{
				m = m.polyMultiply(powers.get(len - i - 1),r);
				m = m.AKSmod(r);
			}
		}
		m.polyReduce();
		return m;
	}
	public BigInt polySubtract(BigInt q)
	{
		BigInt dif = new BigInt(n);
		int sizep = coeff.size();
		int sizeq = q.coeff.size();
		int max = max(sizep,sizeq);
		for(int i=0;i<max;i++)
		{
			if(i<min(sizep,sizeq))
			{
				BigInteger input = (coeff.get(i)).subtract(q.coeff.get(i));
				dif.coeff.add(i, input);
			}
			else if(max==sizeq)
			{
				BigInteger input = (q.coeff.get(i)).negate();
				dif.coeff.add(i, input);
			}
			else
			{
				BigInteger input = (coeff.get(i));
				dif.coeff.add(i, input);
			}
		}
		return dif;
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
	public BigInt polyScalarMultiply(BigInteger k, BigInt p)
	{
		BigInt kp = new BigInt(this.n);
		kp.coeff.add(k);
		return p.polyMultiply(kp);
	}
	public BigInt polyMultiply(BigInt q)
	{
		BigInt prod = new BigInt(n);
		BigInt input = new BigInt(n);

		int sizep = coeff.size();
		int sizeq = q.coeff.size();
		input.coeff.add(BigInteger.ZERO);
		for(int i=0;i<sizep;i++)
		{
			for(int j=0;j<sizeq;j++)
			{
				input.coeff.add((i+j),((coeff.get(i)).multiply(q.coeff.get(j))).mod(n));
				prod = prod.polyAdd(input);
				input = input.polySubtract(input);
			}
		}
		prod.polyReduce();
		return prod;
	}
	public BigInt polyMultiply(BigInt q,int r)
	{
		BigInt prod = new BigInt(n);
		for(int i=0;i<2*r;i++)
			prod.coeff.add(BigInteger.ZERO);
		BigInt input = new BigInt(n);
		
		int sizep = this.coeff.size();
		int sizeq = q.coeff.size();
		input.coeff.add(BigInteger.ZERO);
		
		//System.out.println("p size "+sizep);
		//System.out.println("q size "+sizeq);
		for(int i=0;i<sizep;i++)
		{
			//System.out.print(i);
			for(int j=0;j<sizeq;j++)
			{
				//System.out.println(j);
				//input.coeff.add((i+j),((coeff.get(i)).multiply(q.coeff.get(j))).mod(n));
				//System.out.println("multiply done");
				BigInteger product = (coeff.get(i)).multiply(q.coeff.get(j)).mod(n);
				int spot = i+j;
				product = (prod.coeff.get(spot)).add(product);
				prod.coeff.remove(spot);
				prod.coeff.add(spot,product);
				//prod = prod.polyAdd(input);
				//input = input.polySubtract(input);
			}
		}
		prod = prod.AKSmod(r);
		prod.polyReduce();
		return prod;
	}
	public void polyReduce()
	{
		ArrayList<BigInteger> coefficients = new ArrayList<BigInteger>();
		int size = coeff.size();
		for(int i=0;i<size;i++)
		{
			coefficients.add(i, (coeff.get(i).mod(n)));
		}
		coeff.clear();
		coeff = coefficients;
		
		boolean marker = false;
		if(size>0)
		{
			if(coeff.get(size-1).equals(BigInteger.ZERO))
				marker=true;
		}
		for(int i=size-1;marker&&i>0;i--)
		{
			coeff.remove(i);
			size = coeff.size();
			if(coeff.get(size-1).equals(BigInteger.ZERO))
				marker=true;
			else
				marker=false;
		}
	}

	public BigInt polyMod(BigInt mod)
	{
		BigDec p = this.toDeciPolynomial();
		BigDec m = mod.toDeciPolynomial();
		ArrayList<BigDec> quotient = p.deciDivideWithRemainder(m);
		BigInt rem = quotient.get(1).toPolynomial();
		rem.polyReduce();
		return rem;
	}

	public BigInt polyPowerMod(BigInteger pow, BigInt mod)
	{
		BigInt p = this;
		String pow2 = pow.toString(2);
		char[] cab = pow2.toCharArray();
		int len = cab.length;
		ArrayList<BigInt> powers = new ArrayList<BigInt>();
		p=p.polyMod(mod);
		powers.add(p);
		for (int i = 0; i < len-1; i++)
		{
			BigInt q = powers.get(i);
			q = q.polyMultiply(p);
			q = q.polyMod(mod);
			powers.add(q);
		}
		BigInt m = new BigInt(n);
		m.coeff.add(BigInteger.ONE);
		for (int i = len - 1; i >= 0; i--)
		{
			if (cab[i] == '1')
			{
				m = m.polyMultiply(powers.get(len - i - 1));
				m = m.polyMod(mod);
			}
		}
		m.polyReduce();
		return m;
	}
	public void polyPrint()
	{
		this.polyReduce();
		int size = coeff.size();
		if(true)
		{
			if(size>1)
			{
				if(!coeff.get(0).equals(BigInteger.ZERO))
					System.out.print(coeff.get(0)+" + ");
			}
			if(size==1)
				System.out.print(coeff.get(0)+"\n");
		}
		if(size>2)
		{
			if(!coeff.get(1).equals(BigInteger.ZERO))
			{
				if(!coeff.get(1).equals(BigInteger.ONE))
					System.out.print(coeff.get(1)+"x + ");
				else
					System.out.print("x + ");
			}
		}
		if(size==2)
		{
			if(!coeff.get(1).equals(BigInteger.ZERO))
			{
				if(!coeff.get(1).equals(BigInteger.ONE))
					System.out.print(coeff.get(1)+"x\n");
				else
					System.out.print("x\n");
			}
		}
		for(int i=2;i<size;i++)
		{
			if(coeff.get(i).equals(BigInteger.ZERO))
				continue;
			else if(i==size-1)
			{
				if(coeff.get(i).equals(BigInteger.ONE))
					System.out.print("x^"+i+"\n");
				else
					System.out.print(coeff.get(i)+"x^"+i+"\n");
			}
			else if(coeff.get(i).equals(BigInteger.ONE))
				System.out.print("x^"+i+" + ");
			else
				System.out.print(coeff.get(i)+"x^"+i+" + ");
		}
	}
	public static void main(String[] args)
	{
		ArrayList<BigInteger> coefficients = new ArrayList<BigInteger>();
		coefficients.add(BigInteger.ONE);
		coefficients.add(BigInteger.ONE);
		coefficients.add(BigInteger.ONE);
		coefficients.add(BigInteger.ONE);
		//coefficients.add(BigInteger.ONE);
		//coefficients.add(BigInteger.ONE);
		BigInt p = new BigInt(new BigInteger("67"),coefficients);
		//System.out.print("p ");p.polyPrint();
		ArrayList<BigInteger> coeffs = new ArrayList<BigInteger>();
		coeffs.add(new BigInteger("3"));
		coeffs.add(BigInteger.ZERO);
		coeffs.add(new BigInteger("3"));
		coeffs.add(BigInteger.ONE);
		coeffs.add(BigInteger.ZERO);
		coeffs.add(new BigInteger("5"));
		//BigInt q = new BigInt(new BigInteger("8675309"),coeffs);
		//int r=24;
		//BigInteger power = new BigInteger("7");
		System.out.print("p = ");p.polyPrint();
		System.out.println("p(5) = "+p.evaluate(new BigInteger("2")));
		//System.out.println("r = "+r);
		//BigInt n = p.AKSpowerMod(power,r);
		//System.out.print("p ");p.polyPrint();
		//System.out.print("p ");p.polyPrint();
		//System.out.print("p ");p.polyPrint();
		//q.polyPrint();
		//System.out.print("p^r = ");n.polyPrint();
	}
}