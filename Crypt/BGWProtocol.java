import java.math.*;
import java.util.*;

public class BGWProtocol
{
	public static BigInteger ONE = new BigInteger("1");
	public static BigInteger TWO = new BigInteger("2");
	public static BigInteger THREE = new BigInteger("3");
	public static BigInteger FOUR = new BigInteger("4");
	public static BigInteger FIVE = new BigInteger("5");
	public static BigInteger SIX = new BigInteger("6");
	public static BigInteger ZERO = new BigInteger("0");
	
	BigInteger m = new BigInteger("7");
	BigInteger n;
	BigInteger t;
	ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
	ArrayList<BigInteger> xVals = new ArrayList<BigInteger>();
	ArrayList<ArrayList<BigInteger>> vectors = new ArrayList<ArrayList<BigInteger>>();
	ArrayList<BGWUser> users = new ArrayList<BGWUser>();
	
	public BGWProtocol(int n)
	{
		this.n = BigInteger.valueOf((long)n);
		this.t = BigInteger.valueOf((long)((n-1)/2));
		for(int i=0; i<n; i++)
		{
			this.polynomials.add(new Polynomial(m,this.t.add(BigInteger.ONE)));
			this.xVals.add(this.polynomials.get(i).evaluate(BigInteger.ZERO));
		}
		for(int i=0; i<n; i++)
		{
			this.vectors.add(getShares(i));
		}
		users.add(new BGWUser(1,this));
		users.add(new BGWUser(2,this));
		users.add(new BGWUser(3,this));
		users.add(new BGWUser(4,this));
		users.add(new BGWUser(5,this));
	}
	public BGWProtocol(BigInteger x1, BigInteger x2, BigInteger x3, BigInteger x4, BigInteger x5)
	{
		this.n = BigInteger.valueOf((long)5);
		this.t = BigInteger.valueOf((long)((5-1)/2));
		for(int i=0; i<5; i++)
		{
			this.polynomials.add(new Polynomial(m,this.t.add(BigInteger.ONE)));
			if(i==0)
			{
				polynomials.get(i).coeff.remove(0);
				polynomials.get(i).coeff.add(0,x1);
			}
			if(i==1)
			{
				polynomials.get(i).coeff.remove(0);
				polynomials.get(i).coeff.add(0,x2);
			}
			if(i==2)
			{
				polynomials.get(i).coeff.remove(0);
				polynomials.get(i).coeff.add(0,x3);
			}
			if(i==3)
			{
				polynomials.get(i).coeff.remove(0);
				polynomials.get(i).coeff.add(0,x4);
			}
			if(i==4)
			{
				polynomials.get(i).coeff.remove(0);
				polynomials.get(i).coeff.add(0,x5);
			}
			this.xVals.add(this.polynomials.get(i).evaluate(BigInteger.ZERO));
		}
		for(int i=0; i<5; i++)
		{
			this.vectors.add(getShares(i));
		}
		users.add(new BGWUser(1,this));
		users.add(new BGWUser(2,this));
		users.add(new BGWUser(3,this));
		users.add(new BGWUser(4,this));
		users.add(new BGWUser(5,this));
	}
	
	public ArrayList<BigInteger> getShares(int i)
	{
		ArrayList<BigInteger> shares = new ArrayList<BigInteger>();
		for(int j=0; j<n.intValue(); j++)
		{
			shares.add(polynomials.get(j).evaluate(BigInteger.valueOf(i+1)));
		}
		return shares;
	}
	public BigInteger getX(int i)
	{
		return polynomials.get(i-1).evaluate(BigInteger.ZERO);
	}
	public void setX(int i, BigInteger x)
	{
		polynomials.get(i-1).coeff.remove(0);
		polynomials.get(i-1).coeff.add(0,x);
	}
	public Polynomial interpolate3(BigInteger s1, BigInteger s2, BigInteger s3)
	{
		BigInteger h0 = s1.multiply(new BigInteger("3")).add(s2.multiply(new BigInteger("4"))).add(s3.multiply(new BigInteger("1")));
		BigInteger h1 = s1.multiply(new BigInteger("1")).add(s2.multiply(new BigInteger("4"))).add(s3.multiply(new BigInteger("2")));
		BigInteger h2 = s1.multiply(new BigInteger("4")).add(s2.multiply(new BigInteger("6"))).add(s3.multiply(new BigInteger("4")));
		ArrayList<BigInteger> coeff = new ArrayList<BigInteger>();
		coeff.add(h0);
		coeff.add(h1);
		coeff.add(h2);
		Polynomial h = new Polynomial(m,coeff);
		return h;
	}
	public void multiply5(int x, int y)
	{
		BGWUser user1 = this.users.get(0);
		BGWUser user2 = this.users.get(1);
		BGWUser user3 = this.users.get(2);
		BGWUser user4 = this.users.get(3);
		BGWUser user5 = this.users.get(4);
		ArrayList<BigInteger> s1 = user1.gateMultiply(x, y);
		ArrayList<BigInteger> s2 = user2.gateMultiply(x, y);
		ArrayList<BigInteger> s3 = user3.gateMultiply(x, y);
		ArrayList<BigInteger> s4 = user4.gateMultiply(x, y);
		ArrayList<BigInteger> s5 = user5.gateMultiply(x, y);
		
		for(int i=0;i<s1.size();i++)
		{
			BigInteger prod = s1.get(i).multiply(FIVE).mod(m);
			s1.remove(i);
			s1.add(i,prod);
		}
		for(int i=0;i<s2.size();i++)
		{
			BigInteger prod = s2.get(i).multiply(FOUR).mod(m);
			s2.remove(i);
			s2.add(i,prod);
		}
		for(int i=0;i<s3.size();i++)
		{
			BigInteger prod = s3.get(i).multiply(THREE).mod(m);
			s3.remove(i);
			s3.add(i,prod);
		}
		for(int i=0;i<s4.size();i++)
		{
			BigInteger prod = s4.get(i).multiply(TWO).mod(m);
			s4.remove(i);
			s4.add(i,prod);
		}
		for(int i=0;i<s5.size();i++)
		{
			BigInteger prod = s5.get(i).multiply(ONE).mod(m);
			s5.remove(i);
			s5.add(i,prod);
		}
		BigInteger h1 = s1.get(0).add(s2.get(0)).add(s3.get(0)).add(s4.get(0)).add(s5.get(0)).mod(m);
		BigInteger h2 = s1.get(1).add(s2.get(1)).add(s3.get(1)).add(s4.get(1)).add(s5.get(1)).mod(m);
		BigInteger h3 = s1.get(2).add(s2.get(2)).add(s3.get(2)).add(s4.get(2)).add(s5.get(2)).mod(m);
		BigInteger h4 = s1.get(3).add(s2.get(3)).add(s3.get(3)).add(s4.get(3)).add(s5.get(3)).mod(m);
		BigInteger h5 = s1.get(4).add(s2.get(4)).add(s3.get(4)).add(s4.get(4)).add(s5.get(4)).mod(m);
		
		Polynomial h = interpolate3(h1,h2,h3);
		BigInteger y4 = h.evaluate(new BigInteger("4"));
		BigInteger y5 = h.evaluate(new BigInteger("5"));
		if(y4.equals(h4)&&y5.equals(h5))
		{
			user1.setH(h1);
			user2.setH(h2);
			user3.setH(h3);
			user4.setH(h4);
			user5.setH(h5);
		}
		else
			System.out.println("Polynomial incorrect");
	}
	public void multiply5(int x)
	{
		BGWUser user1 = this.users.get(0);
		BGWUser user2 = this.users.get(1);
		BGWUser user3 = this.users.get(2);
		BGWUser user4 = this.users.get(3);
		BGWUser user5 = this.users.get(4);
		ArrayList<BigInteger> s1 = user1.gateMultiply(x);
		ArrayList<BigInteger> s2 = user2.gateMultiply(x);
		ArrayList<BigInteger> s3 = user3.gateMultiply(x);
		ArrayList<BigInteger> s4 = user4.gateMultiply(x);
		ArrayList<BigInteger> s5 = user5.gateMultiply(x);
		
		for(int i=0;i<s1.size();i++)
		{
			BigInteger prod = s1.get(i).multiply(FIVE).mod(m);
			s1.remove(i);
			s1.add(i,prod);
		}
		for(int i=0;i<s2.size();i++)
		{
			BigInteger prod = s2.get(i).multiply(FOUR).mod(m);
			s2.remove(i);
			s2.add(i,prod);
		}
		for(int i=0;i<s3.size();i++)
		{
			BigInteger prod = s3.get(i).multiply(THREE).mod(m);
			s3.remove(i);
			s3.add(i,prod);
		}
		for(int i=0;i<s4.size();i++)
		{
			BigInteger prod = s4.get(i).multiply(TWO).mod(m);
			s4.remove(i);
			s4.add(i,prod);
		}
		for(int i=0;i<s5.size();i++)
		{
			BigInteger prod = s5.get(i).multiply(ONE).mod(m);
			s5.remove(i);
			s5.add(i,prod);
		}
		BigInteger h1 = s1.get(0).add(s2.get(0)).add(s3.get(0)).add(s4.get(0)).add(s5.get(0)).mod(m);
		BigInteger h2 = s1.get(1).add(s2.get(1)).add(s3.get(1)).add(s4.get(1)).add(s5.get(1)).mod(m);
		BigInteger h3 = s1.get(2).add(s2.get(2)).add(s3.get(2)).add(s4.get(2)).add(s5.get(2)).mod(m);
		BigInteger h4 = s1.get(3).add(s2.get(3)).add(s3.get(3)).add(s4.get(3)).add(s5.get(3)).mod(m);
		BigInteger h5 = s1.get(4).add(s2.get(4)).add(s3.get(4)).add(s4.get(4)).add(s5.get(4)).mod(m);
		
		Polynomial h = interpolate3(h1,h2,h3);
		BigInteger y4 = h.evaluate(new BigInteger("4"));
		BigInteger y5 = h.evaluate(new BigInteger("5"));
		if(y4.equals(h4)&&y5.equals(h5))
		{
			user1.setH(h1);
			user2.setH(h2);
			user3.setH(h3);
			user4.setH(h4);
			user5.setH(h5);
		}
		else
			System.out.println("Polynomial incorrect");
	}
	public void add5(int x, int y)
	{
		BGWUser user1 = this.users.get(0);
		BGWUser user2 = this.users.get(1);
		BGWUser user3 = this.users.get(2);
		BGWUser user4 = this.users.get(3);
		BGWUser user5 = this.users.get(4);
		BigInteger h1 = user1.gateAdd(x, y);
		BigInteger h2 = user2.gateAdd(x, y);
		BigInteger h3 = user3.gateAdd(x, y);
		BigInteger h4 = user4.gateAdd(x, y);
		BigInteger h5 = user5.gateAdd(x, y);
		
		Polynomial h = interpolate3(h1,h2,h3);
		BigInteger y4 = h.evaluate(new BigInteger("4"));
		BigInteger y5 = h.evaluate(new BigInteger("5"));
		if(y4.equals(h4)&&y5.equals(h5))
		{
			user1.h = h1;
			user2.h = h2;
			user3.h = h3;
			user4.h = h4;
			user5.h = h5;
		}
		else
			System.out.println("Polynomial incorrect");
	}
	public void add5(int x)
	{
		BGWUser user1 = this.users.get(0);
		BGWUser user2 = this.users.get(1);
		BGWUser user3 = this.users.get(2);
		BGWUser user4 = this.users.get(3);
		BGWUser user5 = this.users.get(4);
		BigInteger h1 = user1.gateAdd(x);
		BigInteger h2 = user2.gateAdd(x);
		BigInteger h3 = user3.gateAdd(x);
		BigInteger h4 = user4.gateAdd(x);
		BigInteger h5 = user5.gateAdd(x);
		
		Polynomial h = interpolate3(h1,h2,h3);
		BigInteger y4 = h.evaluate(new BigInteger("4"));
		BigInteger y5 = h.evaluate(new BigInteger("5"));
		if(y4.equals(h4)&&y5.equals(h5))
		{
			user1.h = h1;
			user2.h = h2;
			user3.h = h3;
			user4.h = h4;
			user5.h = h5;
		}
		else
			System.out.println("Polynomial incorrect");
	}
	public BigInteger getAnswer()
	{
		BGWUser user1 = this.users.get(0);
		BGWUser user2 = this.users.get(1);
		BGWUser user3 = this.users.get(2);
		BGWUser user4 = this.users.get(3);
		BGWUser user5 = this.users.get(4);
		
		Polynomial h = interpolate3(user1.h, user2.h, user3.h);
		BigInteger y4 = h.evaluate(new BigInteger("4"));
		BigInteger y5 = h.evaluate(new BigInteger("5"));
		if(y4.equals(user4.h)&&y5.equals(user5.h))
		{
			return h.evaluate(ZERO);
		}
		System.out.println("Polynomial incorrect");
		return ZERO;
		
		/*Polynomial p1 = user1.polynomial;
		Polynomial p2 = user2.polynomial;
		Polynomial p3 = user3.polynomial;
		Polynomial p4 = user4.polynomial;
		Polynomial p5 = user5.polynomial;
		ArrayList<BigInteger> shares1 = new ArrayList<BigInteger>();
		ArrayList<BigInteger> shares2 = new ArrayList<BigInteger>();
		ArrayList<BigInteger> shares3 = new ArrayList<BigInteger>();
		ArrayList<BigInteger> shares4 = new ArrayList<BigInteger>();
		ArrayList<BigInteger> shares5 = new ArrayList<BigInteger>();
		shares1.add(p1.evaluate(ONE));
		shares1.add(p2.evaluate(ONE));
		shares1.add(p3.evaluate(ONE));
		shares1.add(p4.evaluate(ONE));
		shares1.add(p5.evaluate(ONE));
		user1.shares = shares1;
		shares2.add(p1.evaluate(TWO));
		shares2.add(p2.evaluate(TWO));
		shares2.add(p3.evaluate(TWO));
		shares2.add(p4.evaluate(TWO));
		shares2.add(p5.evaluate(TWO));
		user2.shares = shares2;
		shares3.add(p1.evaluate(THREE));
		shares3.add(p2.evaluate(THREE));
		shares3.add(p3.evaluate(THREE));
		shares3.add(p4.evaluate(THREE));
		shares3.add(p5.evaluate(THREE));
		user3.shares = shares3;
		shares4.add(p1.evaluate(FOUR));
		shares4.add(p2.evaluate(FOUR));
		shares4.add(p3.evaluate(FOUR));
		shares4.add(p4.evaluate(FOUR));
		shares4.add(p5.evaluate(FOUR));
		user4.shares = shares4;
		shares5.add(p1.evaluate(FIVE));
		shares5.add(p2.evaluate(FIVE));
		shares5.add(p3.evaluate(FIVE));
		shares5.add(p4.evaluate(FIVE));
		shares5.add(p5.evaluate(FIVE));
		user5.shares = shares5;
		*/
	}
	
	public static void main(String[] args)
	{
		int x = 1;
		int y = 5;
		BGWProtocol bgw1 = new BGWProtocol(5);
		//BGWProtocol bgw1 = new BGWProtocol(ZERO,ONE,ZERO,ONE,ONE);
		System.out.println(bgw1.xVals);
		System.out.println();
		
		BGWUser user1 = bgw1.users.get(0);
		BGWUser user2 = bgw1.users.get(1);
		BGWUser user3 = bgw1.users.get(2);
		BGWUser user4 = bgw1.users.get(3);
		BGWUser user5 = bgw1.users.get(4);
		user1.polynomial.polyPrint();
		user2.polynomial.polyPrint();
		user3.polynomial.polyPrint();
		user4.polynomial.polyPrint();
		user5.polynomial.polyPrint();
		System.out.println();
		
		System.out.println("The product of users "+x+" and "+y+" is ");
		bgw1.multiply5(x,y);
		System.out.println(bgw1.getAnswer());
		System.out.println("The sum of users "+x+" and "+y+" is ");
		bgw1.add5(x,y);
		System.out.println(bgw1.getAnswer());
	}
}
