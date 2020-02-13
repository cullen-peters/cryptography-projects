import java.math.*;

public class counting
{
	public static void main(String args[])
	{
		BigInteger a = new BigInteger("2018");
		int m = 0;
		int n = 0;
		BigInteger c = BigInteger.ZERO;
		BigInteger two = new BigInteger("2");
		for(m = 0;m<=11;m++)
		{
			for(n = 0; n <= 11;n++)
			{
				if(m<=n)
				{
					BigInteger x = two.pow(n).add(two.pow(m));
					System.out.println(x);
					if(x.compareTo(a)<=0)
					{
						c = c.add(BigInteger.ONE);
					}
				}
			}
		}
		System.out.println("c: "+c);
	}
}
