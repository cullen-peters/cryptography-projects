import java.math.*;
import java.util.*;

public class GoldwasserMicaliDec
{
	public static void main(String[] args)
	{
		GoldwasserMicali gm = new GoldwasserMicali();
		Scanner sc = new Scanner(System.in);
		System.out.println("INPUT THE PRIVATE KEY: ");
		BigInteger p = sc.nextBigInteger();
		BigInteger q = sc.nextBigInteger();
		System.out.println("INPUT THE CIPHERTEXT SEPARATED BY COMMAS WITHOUT SPACES: ");
		String c = sc.next();
		sc.close();
		System.out.println("THE MESSAGE IS: ");
		System.out.println(gm.Dec(p, q, c));
	}
}
