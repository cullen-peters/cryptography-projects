import java.math.*;
import java.util.*;

public class JacobiSymbolCalculator
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		Crypto cr = new Crypto();
		System.out.println("INPUT THE NUMBERS FOR THE JACOBI SYMBOL YOU WANT TO EVALUATE");
		BigInteger a = sc.nextBigInteger();
		BigInteger b = sc.nextBigInteger();
		System.out.println(cr.jacobiSymbol(a,b));
		/*for(long i = 1L; i<36L;i++)
		{
			//if(!(i%5L==0L)&&!(i%7L==0L))
			System.out.println(cr.jacobiSymbol(BigInteger.valueOf(i),b));
		}*/
		sc.close();
	}
}
