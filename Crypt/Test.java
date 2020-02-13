import java.math.*;
import java.util.*;

public class Test
{
	public static void main(String[] args)throws Exception
	{
		Crypto cr = new Crypto();
		BigInteger a =  new BigInteger("11");
		BigInteger b = new BigInteger("2018");
		BigInteger n = new BigInteger("100");
		
		Scanner sc = new Scanner(System.in);
		/*BigInteger a =  new BigInteger("3");
		BigInteger x = new BigInteger("71");
		int i = 1;
		while(i == 1)
		{
			BigInteger b = x.multiply(a).add(BigInteger.ONE);
			if(cr.isPerfectPower(b))
			{
				System.out.println(a);
				i = sc.nextInt();
			}
			a = a.add(new BigInteger("2"));
		*/
		cr.powerMod(a, b, n);
		System.out.println(cr.powerMod(a, b, n));
		sc.close();
	}
}
