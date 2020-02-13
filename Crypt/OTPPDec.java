import java.util.*;

public class OTPPDec
{
	public static void main(String[] args)
	{
		OneTimePadPlus otpp = new OneTimePadPlus();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Ciphertext:");
		String c = sc.nextLine();
		System.out.println("Enter the Key:");
		String key = sc.nextLine();
		String M = otpp.dec(c, key);
		System.out.println("The message was: "+M);
		sc.close();
	}
}
