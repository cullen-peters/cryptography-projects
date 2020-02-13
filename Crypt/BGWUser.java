import java.math.*;
import java.util.*;

public class BGWUser
{
	BigInteger m;
	BigInteger n;
	BigInteger t;
	BGWProtocol bgw;
	int i;
	public Polynomial polynomial;
	public BigInteger xVal;
	public ArrayList<BigInteger> shares = new ArrayList<BigInteger>();
	ArrayList<BigInteger> hshares = new ArrayList<BigInteger>();
	BigInteger h;
	BigInteger vote;
	public BGWUser(int i, BGWProtocol bgw)
	{
		this.polynomial = bgw.polynomials.get(i-1);
		this.xVal = bgw.xVals.get(i-1);
		this.shares = bgw.vectors.get(i-1);
		this.n = bgw.n;
		this.t = bgw.t;
		this.m = bgw.m;
		this.bgw = bgw;
		this.i= i;
	}
	
	public void setX(BigInteger x)
	{
		polynomial.coeff.remove(0);
		polynomial.coeff.add(0,x);
	}
	public void setH(BigInteger x)
	{
		h = x.mod(m);
	}
	public void setVote(BigInteger x)
	{
		vote = x.mod(m);
	}
	public BigInteger gateAdd(int x, int y)
	{
		return shares.get(x-1).add(shares.get(y-1)).mod(m);
	}
	public BigInteger gateAdd(int x)
	{
		return shares.get(x-1).add(h).mod(m);
	}
	public ArrayList<BigInteger> gateMultiply(int x, int y)
	{
		Polynomial h = new Polynomial(m, t.add(BigInteger.ONE));
		BigInteger h0 = shares.get(x-1).multiply(shares.get(y-1)).mod(m);
		h.coeff.remove(0);
		h.coeff.add(0,h0);
		hshares.clear();
		for(BigInteger i=BigInteger.ONE; i.compareTo(n)<=0; i=i.add(BigInteger.ONE))
		{
			hshares.add(h.evaluate(i));
		}
		return hshares;
	}
	public ArrayList<BigInteger> gateMultiply(int x)
	{
		Polynomial p = new Polynomial(m, t.add(BigInteger.ONE));
		BigInteger h0 = shares.get(x-1).multiply(h).mod(m);
		p.coeff.remove(0);
		p.coeff.add(0,h0);
		hshares.clear();
		for(BigInteger i=BigInteger.ONE; i.compareTo(n)<=0; i=i.add(BigInteger.ONE))
		{
			hshares.add(p.evaluate(i));
		}
		return hshares;
	}
	public BigInteger gateConstantMultiply(int x, BigInteger b)
	{
		return shares.get(x-1).multiply(b).mod(m);
	}
	public void updatePolynomial(Polynomial p)
	{
		this.polynomial = p;
	}
}
