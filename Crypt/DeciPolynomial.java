import java.math.*;
import java.util.*;

public class DeciPolynomial
{
	private final BigDecimal n;
	public ArrayList<BigDecimal> decicoeff = new ArrayList<BigDecimal>();
	public DeciPolynomial(BigDecimal n, ArrayList<BigDecimal> decicoeff)
	{
		this.n = n;
		this.decicoeff = decicoeff;
	}
	public DeciPolynomial(BigDecimal n)
	{
		this.n = n;
	}
	
	public DeciPolynomial deciAdd(DeciPolynomial q)
	{
		DeciPolynomial sum = new DeciPolynomial(n);
		int sizep = decicoeff.size();
		int sizeq = q.decicoeff.size();
		int max = max(sizep,sizeq);
		for(int i=0;i<max;i++)
		{
			if(i<min(sizep,sizeq))
			{
				BigDecimal input = (q.decicoeff.get(i)).add(decicoeff.get(i));
				sum.decicoeff.add(i, input);
			}
			else if(max==sizeq)
			{
				BigDecimal input = (q.decicoeff.get(i)).add(new BigDecimal("0"));
				sum.decicoeff.add(i, input);
			}
			else
			{
				BigDecimal input = (decicoeff.get(i).add(new BigDecimal("0")));
				sum.decicoeff.add(i, input);
			}
		}
		sum.deciReduce();
		return sum;
	}
	public DeciPolynomial deciSubtract(DeciPolynomial q)
	{
		DeciPolynomial dif = new DeciPolynomial(n);
		int sizep = decicoeff.size();
		int sizeq = q.decicoeff.size();
		int max = max(sizep,sizeq);
		for(int i=0;i<max;i++)
		{
			if(i<min(sizep,sizeq))
			{
				BigDecimal input = (decicoeff.get(i)).subtract(q.decicoeff.get(i));
				dif.decicoeff.add(i, input);
			}
			else if(max==sizeq)
			{
				BigDecimal input = (q.decicoeff.get(i)).negate();
				dif.decicoeff.add(i, input);
			}
			else
			{
				BigDecimal input = (decicoeff.get(i));
				dif.decicoeff.add(i, input);
			}
		}
		dif.deciReduce();
		return dif;
	}
	public DeciPolynomial deciMultiply(DeciPolynomial q)
	{
		DeciPolynomial prod = new DeciPolynomial(n);
		DeciPolynomial input = new DeciPolynomial(n);

		int sizep = decicoeff.size();
		int sizeq = q.decicoeff.size();
		for(int i=0;i<sizep;i++)
		{
			for(int j=0;j<sizeq;j++)
			{
				for(int k=0;k<(i+j);k++)
					input.decicoeff.add(new BigDecimal("0"));
				input.decicoeff.add((decicoeff.get(i)).multiply(q.decicoeff.get(j)));
				prod = prod.deciAdd(input);
				input.decicoeff.clear();
			}
		}
		prod.deciReduce();
		return prod;
	}
	public void deciReduce()
	{
		ArrayList<BigDecimal> coefficients = new ArrayList<BigDecimal>();
		BigDecimal input;
		int size = decicoeff.size();
		for(int i=0;i<size;i++)
		{
			input = decicoeff.get(i);
			while(input.compareTo(new BigDecimal("0"))<0||input.compareTo(n)>0)
					{
						if(input.compareTo(new BigDecimal("0"))<0)
							input = input.add(n);
						else
							input =input.subtract(n);
					}
			coefficients.add(i, input);
		}
		decicoeff.clear();
		decicoeff = coefficients;
		
		boolean marker = false;
		if(size>0)
		{
			if(decicoeff.get(size-1).equals(new BigDecimal("0")))
				marker=true;
		}
		for(int i=size-1;marker&&i>0;i--)
		{
			decicoeff.remove(i);
			size = decicoeff.size();
			if(decicoeff.get(size-1).equals(new BigDecimal("0")))
				marker=true;
			else
				marker=false;
		}
	}
	public ArrayList<DeciPolynomial> deciDivideWithRemainder(DeciPolynomial q)
	{
		ArrayList<DeciPolynomial> quotandrem = new ArrayList<DeciPolynomial>();
		DeciPolynomial p = this;
		DeciPolynomial quot = new DeciPolynomial(n);
		//Polynomial rem = new Polynomial(n);
		DeciPolynomial in1 = new DeciPolynomial(n);
		//Polynomial in2 = new Polynomial(n);
		p.deciReduce();
		q.deciReduce();
		int sizep = p.decicoeff.size();
		int sizeq = q.decicoeff.size();
		int max = max(sizep,sizeq);
		//int min = min(sizep,sizeq);
		int plast = sizep-1;
		int qlast = sizeq-1;
		
		for(int i=plast;i>=0;i--)
		{
			p.deciReduce();
			q.deciReduce();
			for(int k=0;k<max;k++)
			{
				quot.decicoeff.add(new BigDecimal("0"));
				in1.decicoeff.add(new BigDecimal("0"));
			}
			for(int k=(p.decicoeff.size()-1);k<=max;k++)
				p.decicoeff.add(new BigDecimal("0"));
			
			if(i>=qlast)
			{
				BigDecimal div = p.decicoeff.get(i).divide(q.decicoeff.get(qlast));
				//System.out.println("div "+div);
				in1.decicoeff.add((i-qlast),div);
				//System.out.print("in1 ");in1.deciPrint();
				DeciPolynomial sub = in1.deciMultiply(q);
				p = p.deciSubtract(sub);
				quot = quot.deciAdd(in1);
				//System.out.print("p ");p.deciPrint();
				//System.out.print("quot ");quot.deciPrint();
			}
			
			in1.decicoeff.clear();
			//in2.coeff.clear();
		}
		quot.deciReduce();
		p.deciReduce();
		quotandrem.add(quot);
		quotandrem.add(p);
		return quotandrem;
	}
	public void deciPrint()
	{
		this.deciReduce();
		int size = decicoeff.size();
		if(true)
		{
			if(size>1)
			{
				if(!decicoeff.get(0).equals(new BigDecimal("0")))
					System.out.print(decicoeff.get(0)+" + ");
			}
			if(size==1)
				System.out.print(decicoeff.get(0)+"\n");
		}
		if(size>2)
		{
			if(!decicoeff.get(1).equals(new BigDecimal("0")))
			{
				if(!decicoeff.get(1).equals(new BigDecimal("1")))
					System.out.print(decicoeff.get(1)+"x + ");
				else
					System.out.print("x + ");
			}
		}
		if(size==2)
		{
			if(!decicoeff.get(1).equals(new BigDecimal("0")))
			{
				if(!decicoeff.get(1).equals(new BigDecimal("1")))
					System.out.print(decicoeff.get(1)+"x\n");
				else
					System.out.print("x\n");
			}
		}
		for(int i=2;i<size;i++)
		{
			if(decicoeff.get(i).equals(new BigDecimal("0")))
				continue;
			else if(i==size-1)
			{
				if(decicoeff.get(i).equals(new BigDecimal("1")))
					System.out.print("x^"+i+"\n");
				else
					System.out.print(decicoeff.get(i)+"x^"+i+"\n");
			}
			else if(decicoeff.get(i).equals(new BigDecimal("1")))
				System.out.print("x^"+i+" + ");
			else
				System.out.print(decicoeff.get(i)+"x^"+i+" + ");
		}
	}
	public Polynomial toPolynomial()
	{
		Polynomial p = new Polynomial(n.toBigInteger());
		int size = decicoeff.size();
		for(int i=0;i<size;i++)
		{
			p.coeff.add(decicoeff.get(i).toBigInteger());
		}
		return p;
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
	public static void main(String[] args)
	{
		ArrayList<BigDecimal> coefficients = new ArrayList<BigDecimal>();
		coefficients.add(new BigDecimal("55"));
		coefficients.add(new BigDecimal("33"));
		coefficients.add(new BigDecimal("11"));
		DeciPolynomial p = new DeciPolynomial(new BigDecimal("7"),coefficients);
		ArrayList<BigDecimal> coeffic = new ArrayList<BigDecimal>();
		coeffic.add(new BigDecimal("1"));
		coeffic.add(new BigDecimal("1"));
		DeciPolynomial q = new DeciPolynomial(new BigDecimal("7"),coeffic);
		ArrayList<DeciPolynomial> n = p.deciDivideWithRemainder(q);
		p.deciReduce();
		q.deciReduce();
		p.deciPrint();
		q.deciPrint();
		n.get(0).deciPrint();
		n.get(1).deciPrint();
	}
}
