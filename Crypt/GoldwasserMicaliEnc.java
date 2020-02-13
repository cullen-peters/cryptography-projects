import java.math.*;
import java.util.*;

public class GoldwasserMicaliEnc
{
	public static void main(String[] args)
	{
		GoldwasserMicali gm = new GoldwasserMicali();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("INPUT THE PUBLIC KEY YOU WISH TO SEND YOUR MESSAGE TO: ");
		BigInteger z = sc.nextBigInteger();
		BigInteger N = sc.nextBigInteger();
		System.out.println("INPUT YOUR MESSAGE");
		String m = sc.next();
		sc.close();
		System.out.println("THE ENCRYPTION IS: ");
		System.out.println(gm.Enc(z, N, m));
	}
}
